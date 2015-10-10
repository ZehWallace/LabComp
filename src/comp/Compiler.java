package comp;

import ast.*;
import lexer.*;
import java.io.*;
import java.util.*;

public class Compiler {

    // compile must receive an input with an character less than
    // p_input.lenght
    public Program compile(char[] input, PrintWriter outError) {
        nested_whiles = 0;
        ArrayList<CompilationError> compilationErrorList = new ArrayList<>();
        kraClassList = new ArrayList<>();
        signalError = new SignalError(outError, compilationErrorList);
        symbolTable = new SymbolTable();
        lexer = new Lexer(input, signalError);
        signalError.setLexer(lexer);

        Program program = null;
        lexer.nextToken();
        program = program(compilationErrorList);
        return program;
    }

    private Program program(ArrayList<CompilationError> compilationErrorList) {
        // Program ::= KraClass { KraClass }
        ArrayList<MetaobjectCall> metaobjectCallList = new ArrayList<>();
        ArrayList<KraClass> kraClassList = new ArrayList<>();
        Program program = new Program(kraClassList, metaobjectCallList, compilationErrorList);
        try {
            while (lexer.token == Symbol.MOCall) {
                metaobjectCallList.add(metaobjectCall());
            }
            classDec();
            while (lexer.token == Symbol.CLASS) {
                classDec();
            }
            if (lexer.token != Symbol.EOF) {
                signalError.show("End of file expected");
            }
        } catch (RuntimeException e) {
            // if there was an exception, there is a compilation signalError
        }
        return program;
    }

    /**
     * parses a metaobject call as <code>{@literal @}ce(...)</code> in <br>
     * <code>
     *
     * @ce(5, "'class' expected") <br>
     * clas Program <br>
     * public void run() { } <br>
     * end <br>
     * </code>
     *
     *
     */
    @SuppressWarnings("incomplete-switch")
    private MetaobjectCall metaobjectCall() {
        String name = lexer.getMetaobjectName();
        lexer.nextToken();
        ArrayList<Object> metaobjectParamList = new ArrayList<>();
        if (lexer.token == Symbol.LEFTPAR) {
            // metaobject call with parameters
            lexer.nextToken();
            while (lexer.token == Symbol.LITERALINT || lexer.token == Symbol.LITERALSTRING
                    || lexer.token == Symbol.IDENT) {
                switch (lexer.token) {
                    case LITERALINT:
                        metaobjectParamList.add(lexer.getNumberValue());
                        break;
                    case LITERALSTRING:
                        metaobjectParamList.add(lexer.getLiteralStringValue());
                        break;
                    case IDENT:
                        metaobjectParamList.add(lexer.getStringValue());
                }
                lexer.nextToken();
                if (lexer.token == Symbol.COMMA) {
                    lexer.nextToken();
                } else {
                    break;
                }
            }
            if (lexer.token != Symbol.RIGHTPAR) {
                signalError.show("')' expected after metaobject call with parameters");
            } else {
                lexer.nextToken();
            }
        }
        if (name.equals("nce")) {
            if (metaobjectParamList.size() != 0) {
                signalError.show("Metaobject 'nce' does not take parameters");
            }
        } else if (name.equals("ce")) {
            if (metaobjectParamList.size() != 3 && metaobjectParamList.size() != 4) {
                signalError.show("Metaobject 'ce' take three or four parameters");
            }
            if (!(metaobjectParamList.get(0) instanceof Integer)) {
                signalError.show("The first parameter of metaobject 'ce' should be an integer number");
            }
            if (!(metaobjectParamList.get(1) instanceof String) || !(metaobjectParamList.get(2) instanceof String)) {
                signalError.show("The second and third parameters of metaobject 'ce' should be literal strings");
            }
            if (metaobjectParamList.size() >= 4 && !(metaobjectParamList.get(3) instanceof String)) {
                signalError.show("The fourth parameter of metaobject 'ce' should be a literal string");
            }

        }

        return new MetaobjectCall(name, metaobjectParamList);
    }

    private void classDec() {
        // Note que os m�todos desta classe n�o correspondem exatamente �s
        // regras
        // da gram�tica. Este m�todo classDec, por exemplo, implementa
        // a produ��o KraClass (veja abaixo) e partes de outras produ��es.

        /*
         * KraClass ::= ``class'' Id [ ``extends'' Id ] "{" MemberList "}"
         * MemberList ::= { Qualifier Member } 
         * Member ::= InstVarDec | MethodDec
         * InstVarDec ::= Type IdList ";" 
         * MethodDec ::= Qualifier Type Id "("[ FormalParamDec ] ")" "{" StatementList "}" 
         * Qualifier ::= [ "static" ]  ( "private" | "public" )
         */
        if (lexer.token != Symbol.CLASS) {
            signalError.show("'class' expected");
        }
        lexer.nextToken();
        if (lexer.token != Symbol.IDENT) {
            signalError.show(SignalError.ident_expected);
        }
        String className = lexer.getStringValue();
        if (symbolTable.getInGlobal(className) != null) {
            signalError.show("Class '" + className + "' is being redeclared");
        }
        KraClass kc = new KraClass(className);
        currentclass = kc;
        symbolTable.putInGlobal(className, new KraClass(className));
        lexer.nextToken();
        if (lexer.token == Symbol.EXTENDS) {
            lexer.nextToken();
            if (lexer.token != Symbol.IDENT) {
                signalError.show(SignalError.ident_expected);
            }
            String superclassName = lexer.getStringValue();

            //ERRO 27
            if (className.equals(superclassName)) {
                signalError.show("Class '" + className + "' is inheriting from itself");
            }
            if (symbolTable.getInGlobal(superclassName) == null) {
                signalError.show("Class '" + superclassName + "' does not exist");
            }

            for (KraClass k : kraClassList) {
                if (superclassName.equals(k.getCname())) {
                    kc.setSuperclass(k);
                }
            }

            lexer.nextToken();
        }
        if (lexer.token != Symbol.LEFTCURBRACKET) {
            signalError.show("{ expected", true);
        }
        lexer.nextToken();

        while (lexer.token == Symbol.PRIVATE || lexer.token == Symbol.PUBLIC) {

            Symbol qualifier;
            switch (lexer.token) {
                case PRIVATE:
                    lexer.nextToken();
                    qualifier = Symbol.PRIVATE;
                    break;
                case PUBLIC:
                    lexer.nextToken();
                    qualifier = Symbol.PUBLIC;
                    break;
                default:
                    signalError.show("private, or public expected");
                    qualifier = Symbol.PUBLIC;
            }
            Type t = type();
            if (lexer.token != Symbol.IDENT) {
                signalError.show("Identifier expected");
            }
            String name = lexer.getStringValue();
            lexer.nextToken();
            if (lexer.token == Symbol.LEFTPAR) {
                if (kc.getInstanceVariable(name) != null) {
                    signalError.show("Method '" + name + "' has name equal to an instance variable");
                }
                if (kc.getMethod(name) != null) {
                    signalError.show("Method '" + name + "' is being redeclared");
                }
                //ERRO 29 ERRO 30 AQUI
                KraClass skc = kc.getSuperclass();
                Method method = methodDec(t, name, qualifier, kc, skc);
                kc.addMethod(method);

            } else if (qualifier != Symbol.PRIVATE) {
                signalError.show("Attempt to declare a public instance variable");
            } else {
                //ERRO RANDOM
                if (kc.getMethod(name) != null) {
                    signalError.show("Variable '" + name + "' has name equal to a method");
                }
                if (kc.getInstanceVariable(name) != null) {
                    signalError.show("Variable '" + name + "' is being redeclared");
                }
                kc.addInstanceVariable(new InstanceVariable(name, t));
                instanceVarDec(t, name);
            }
        }
        if (lexer.token != Symbol.RIGHTCURBRACKET) {
            signalError.show("public/private or \"}\" expected");
        }
        lexer.nextToken();

        kraClassList.add(kc);

    }

    private void instanceVarDec(Type type, String name) {
        // InstVarDec ::= [ "static" ] "private" Type IdList ";"

        while (lexer.token == Symbol.COMMA) {
            lexer.nextToken();
            if (lexer.token != Symbol.IDENT) {
                signalError.show("Identifier expected");
            }
            String variableName = lexer.getStringValue();
            lexer.nextToken();
        }
        if (lexer.token != Symbol.SEMICOLON) {
            signalError.show(SignalError.semicolon_expected);
        }
        lexer.nextToken();
    }

    private Method methodDec(Type type, String name, Symbol qualifier, KraClass kc, KraClass skc) {
        /*
         * MethodDec ::= Qualifier Return Id "("[ FormalParamDec ] ")" "{"
         *                StatementList "}"
         */
        Method method = new Method(name, type);
        currentMethod = method;
        lexer.nextToken();
        if (lexer.token != Symbol.RIGHTPAR) {
            method.setParamList(formalParamDec());
        }
        while (skc != null) {
            Method skcmethod = skc.getMethod(name);
            if (skcmethod != null) {
                if (!method.getParamList().getTypeNames().equals(skcmethod.getParamList().getTypeNames())) {
                    signalError.show("Method '" + name + "' of the subclass '" + kc.getName() + "' has a signature different from the same method of superclass '" + skc.getName() + "'");
                }
                if (!method.getType().equals(skcmethod.getType())) {
                    signalError.show("Method '" + name + "' of subclass '" + kc.getName() + "' has a signature different from method inherited from superclass '" + skc.getName() + "'");
                }
            }
            skc = skc.getSuperclass();
        }
        if (lexer.token != Symbol.RIGHTPAR) {
            signalError.show(") expected");
        }

        lexer.nextToken();
        if (lexer.token != Symbol.LEFTCURBRACKET) {
            signalError.show("{ expected");
        }

        lexer.nextToken();
        ArrayList<Statement> statementList = statementList();
        method.setStatementList(statementList);
        boolean isvoid = true;
        for (Statement s : statementList) {
            if (s.getClass().equals(ReturnStat.class)) {
                isvoid = false;
            }
        }
        if (type == Type.voidType) {
            if (!isvoid) {
                signalError.show("Illegal 'return' statement. Method returns 'void'");
            }
        } else {
            if (isvoid) {
                signalError.show("Missing 'return' statement in method '" + name + "'");
            }
        }

        if (lexer.token != Symbol.RIGHTCURBRACKET) {
            signalError.show("} expected");
        }

        lexer.nextToken();
        return method;
    }

    private void localDec() {
        // LocalDec ::= Type IdList ";"
        Type type = type();
        if (lexer.token != Symbol.IDENT) {
            signalError.show("Identifier expected");
        }
        if (symbolTable.getInLocal(lexer.getStringValue()) != null) {
            signalError.show("Variable '" + lexer.getStringValue() + "' is being redeclared");
        }
        Variable v = new Variable(lexer.getStringValue(), type); //VARDECLIST

        symbolTable.putInLocal(lexer.getStringValue(), v);
        lexer.nextToken();
        while (lexer.token == Symbol.COMMA) {
            lexer.nextToken();
            if (lexer.token != Symbol.IDENT) {
                signalError.show("Identifier expected");
            }
            if (symbolTable.getInLocal(lexer.getStringValue()) != null) {
                signalError.show("Variable " + lexer.getStringValue() + " is being redeclared");
            }
            v = new Variable(lexer.getStringValue(), type);
            symbolTable.putInLocal(lexer.getStringValue(), v);
            lexer.nextToken();
        }
    }

    private ParamList formalParamDec() {
        // FormalParamDec ::= ParamDec { "," ParamDec }
        ParamList paramList = new ParamList();
        paramList.addElement(paramDec());
        while (lexer.token == Symbol.COMMA) {
            lexer.nextToken();
            paramList.addElement(paramDec());
        }
        return paramList;
    }

    private Variable paramDec() {
        // ParamDec ::= Type Id
        //AQUI
        Type type = type();
        if (lexer.token != Symbol.IDENT) {
            signalError.show("Identifier expected");
        }
        String name = lexer.getStringValue();
        lexer.nextToken();
        return new Variable(name, type);
    }

    private Type type() {
        // Type ::= BasicType | Id
        Type result = null;

        switch (lexer.token) {
            case VOID:
                result = Type.voidType;
                break;
            case INT:
                result = Type.intType;
                break;
            case BOOLEAN:
                result = Type.booleanType;
                break;
            case STRING:
                result = Type.stringType;
                break;
            case IDENT:
                // # corrija: fa�a uma busca na TS para buscar a classe
                // IDENT deve ser uma classe.
                for (KraClass kc : kraClassList) {
                    if (kc.getName().equals(lexer.getStringValue())) {
                        result = kc;
                    }
                }
                if (result == null) {
                    signalError.show("Class '" + lexer.getStringValue() + "' is not declared");
                }
                break;
            default:
                signalError.show("Type expected");
                result = Type.undefinedType;
        }
        lexer.nextToken();
        return result;
    }

    private void compositeStatement() {
        lexer.nextToken();
        statementList();
        if (lexer.token != Symbol.RIGHTCURBRACKET) {
            signalError.show("} expected");
        } else {
            lexer.nextToken();
        }
    }

    private ArrayList statementList() {
        ArrayList<Statement> statementList = new ArrayList<>();
        // CompStatement ::= "{" { Statement } "}"
        Symbol tk;
        // statements always begin with an identifier, if, read, write, ...
        while ((tk = lexer.token) != Symbol.RIGHTCURBRACKET
                && tk != Symbol.ELSE) {
            Statement statement = statement();
            if (statement != null) {
                statementList.add(statement);
            }
        }
        return statementList;
    }

    private Statement statement() {
        /*
         * Statement ::= Assignment ``;'' | IfStat |WhileStat | MessageSend
         *                ``;'' | ReturnStat ``;'' | ReadStat ``;'' | WriteStat ``;'' |
         *               ``break'' ``;'' | ``;'' | CompStatement | LocalDec
         */

        switch (lexer.token) {
            case THIS:
            case IDENT:
            case SUPER:
            case INT:
            case BOOLEAN:
            case STRING:
                assignExprLocalDec();
                break;
            case RETURN:
                return returnStatement();
            case READ:
                readStatement();
                break;
            case WRITE:
                writeStatement();
                break;
            case WRITELN:
                writelnStatement();
                break;
            case IF:
                ifStatement();
                break;
            case BREAK:
                //ERRO 26
                if (nested_whiles <= 0) {
                    signalError.show("break statement found outside a while statement");
                }
                breakStatement();
                break;
            case WHILE:
                nested_whiles += 1;
                whileStatement();
                nested_whiles -= 1;
                break;
            case SEMICOLON:
                nullStatement();
                break;
            case LEFTCURBRACKET:
                compositeStatement();
                break;
            default:
                signalError.show("'operator expected' or 'variable expected at the left-hand side of a assignment'");
        }
        return null;
    }

    /*
     * retorne true se 'name' � uma classe declarada anteriormente. � necess�rio
     * fazer uma busca na tabela de s�mbolos para isto.
     */
    private boolean isType(String name) {
        return this.symbolTable.getInGlobal(name) != null;
    }

    /*
     * AssignExprLocalDec ::= Expression [ ``$=$'' Expression ] | LocalDec
     */
    private Expr assignExprLocalDec() {
        if (lexer.token == Symbol.INT || lexer.token == Symbol.BOOLEAN
                || lexer.token == Symbol.STRING
                ||// token � uma classe declarada textualmente antes desta
                // instru��o
                (lexer.token == Symbol.IDENT && isType(lexer.getStringValue()))) {
            /*
             * uma declara��o de vari�vel. 'lexer.token' � o tipo da vari�vel
             * 
             * AssignExprLocalDec ::= Expression [ ``$=$'' Expression ] | LocalDec 
             * LocalDec ::= Type IdList ``;''
             */
            localDec();
        } else {
            /*
             * AssignExprLocalDec ::= Expression [ ``$=$'' Expression ]
             */
            String obj = lexer.getStringValue();
            Expr exprl = expr();
            if (lexer.token == Symbol.ASSIGN) {
                lexer.nextToken();
                //erro 18
                if (exprl == null) {
                    signalError.show("Variable '" + obj + "' was not declared");
                } else if (exprl.getClass() != VariableExpr.class) {
                    signalError.show("Variable '" + obj + "' was not declared");
                }
                Expr exprr = expr();
                //AQUI MODIFICAR TIPO EXPR PARA VARIAVEL
                if (exprr.getType() == Type.voidType) {
                    signalError.show("Expression expected in the right-hand side of assignment");
                }
                if (exprl.getType() != exprr.getType() && exprr.getType()!= Type.undefinedType) {
                    //ERRO 38
                    if (exprl.getType().getClass().equals(KraClass.class) && exprr.getType().getClass().equals(KraClass.class)) {
                        KraClass skc = ((KraClass) exprr.getType()).getSuperclass();
                        boolean isSClass = false;
                        while (skc != null && !isSClass) {
                            if (exprl.getType().getName().equals(skc.getName())) {
                                isSClass = true;
                            } else {
                                skc = skc.getSuperclass();
                            }
                        }
                        if (!isSClass) {
                            signalError.show("Type error: type of the right-hand side of the assignment is not a subclass of the left-hand side");
                        }
                    } else {
                        signalError.show("Type error: value of the right-hand side is not subtype of the variable of the left-hand side.");
                    }
                }
                
                if(exprl.getType().getClass() != KraClass.class && exprr.getType() == Type.undefinedType){
                    signalError.show("Type error: 'null' cannot be assigned to a variable of a basic type");
                }
                
                if (lexer.token != Symbol.SEMICOLON) {
                    signalError.show("';' expected", true);
                } else {
                    lexer.nextToken();
                }
            } else if (lexer.token == Symbol.IDENT) {
                signalError.show("Type '" + obj + "' was not found");
            } else if (lexer.token == Symbol.SEMICOLON) {
                if (exprl.getType() != Type.voidType) {
                    signalError.show("Message send '" + obj + "." + ((MethodExpr) exprl).getName() + "()' returns a value that is not used");
                }
            } else {
                //ARRUMAR
                signalError.show("expected ';'");
            }
        }
        return null;
    }

    private ExprList realParameters() {
        ExprList anExprList = null;

        if (lexer.token != Symbol.LEFTPAR) {
            signalError.show("( expected");
        }
        lexer.nextToken();
        if (startExpr(lexer.token)) {
            anExprList = exprList();
        }
        if (lexer.token != Symbol.RIGHTPAR) {
            signalError.show(") expected");
        }
        lexer.nextToken();
        return anExprList;
    }

    private void whileStatement() {

        lexer.nextToken();
        if (lexer.token != Symbol.LEFTPAR) {
            signalError.show("( expected");
        }
        lexer.nextToken();
        Expr expr = expr();
        //ERRO 11
        if (expr.getType() != Type.booleanType) {
            signalError.show("non-boolean expression in  'while' command");
        }
        if (lexer.token != Symbol.RIGHTPAR) {
            signalError.show(") expected");
        }
        lexer.nextToken();
        statement();
    }

    private void ifStatement() {

        lexer.nextToken();
        if (lexer.token != Symbol.LEFTPAR) {
            signalError.show("( expected");
        }
        lexer.nextToken();
        expr();
        if (lexer.token != Symbol.RIGHTPAR) {
            signalError.show(") expected");
        }
        lexer.nextToken();
        statement();
        if (lexer.token == Symbol.ELSE) {
            lexer.nextToken();
            statement();
        }
    }

    private ReturnStat returnStatement() {

        lexer.nextToken();
        Expr expr = expr();
        //ERRO 39
        if (currentMethod.getType().getName() != expr.getType().getName()) {
            signalError.show("Type error: type of the expression returned is not subclass of the method return type");
        }
        //ERRO 35
        if (currentMethod.getType() == Type.voidType) {
            signalError.show("Illegal 'return' statement. Method returns 'void'");
        }
        ReturnStat returnStat = new ReturnStat(expr);
        if (lexer.token != Symbol.SEMICOLON) {
            signalError.show(SignalError.semicolon_expected);
        }
        lexer.nextToken();
        return returnStat;
    }

    private void readStatement() {
        lexer.nextToken();
        if (lexer.token != Symbol.LEFTPAR) {
            signalError.show("( expected");
        }
        lexer.nextToken();
        while (true) {
            if (lexer.token == Symbol.THIS) {
                lexer.nextToken();
                if (lexer.token != Symbol.DOT) {
                    signalError.show(". expected");
                }
                lexer.nextToken();
            }
            if (lexer.token != Symbol.IDENT) {
                signalError.show(SignalError.ident_expected);
            }
            //ERRO 13
            String name = lexer.getStringValue();
            Variable v = symbolTable.getInLocal(name);
            if (v == null) {
                signalError.show("Variable " + name + " was not declared");
            }
            if (v.getType() != Type.intType && v.getType() != Type.stringType) {
                signalError.show("'int' or 'String' expression expected");
            }
            lexer.nextToken();
            if (lexer.token == Symbol.COMMA) {
                lexer.nextToken();
            } else {
                break;
            }
        }

        if (lexer.token != Symbol.RIGHTPAR) {
            signalError.show(") expected");
        }
        lexer.nextToken();
        if (lexer.token != Symbol.SEMICOLON) {
            signalError.show(SignalError.semicolon_expected);
        }
        lexer.nextToken();
    }

    private void writeStatement() {

        lexer.nextToken();
        if (lexer.token != Symbol.LEFTPAR) {
            signalError.show("( expected");
        }
        lexer.nextToken();
        //ERRO 14
        ArrayList<Expr> exprlist = exprList().getExprList();
        for (Expr e : exprlist) {
            Type t = e.getType();
            if (t == Type.booleanType || t == Type.undefinedType || t == Type.voidType) {
                signalError.show("Command 'write' does not accept '" + t.getName() + "' expressions");
            }else if(t.getClass() == KraClass.class){
                signalError.show("Command 'write' does not accept objects");
            }
        }
        if (lexer.token != Symbol.RIGHTPAR) {
            signalError.show(") expected");
        }
        lexer.nextToken();
        if (lexer.token != Symbol.SEMICOLON) {
            signalError.show(SignalError.semicolon_expected);
        }
        lexer.nextToken();
    }

    private void writelnStatement() {

        lexer.nextToken();
        if (lexer.token != Symbol.LEFTPAR) {
            signalError.show("( expected");
        }
        lexer.nextToken();
        exprList();
        if (lexer.token != Symbol.RIGHTPAR) {
            signalError.show(") expected");
        }
        lexer.nextToken();
        if (lexer.token != Symbol.SEMICOLON) {
            signalError.show(SignalError.semicolon_expected);
        }
        lexer.nextToken();
    }

    private void breakStatement() {
        lexer.nextToken();
        if (lexer.token != Symbol.SEMICOLON) {
            signalError.show(SignalError.semicolon_expected);
        }
        lexer.nextToken();
    }

    private void nullStatement() {
        lexer.nextToken();
    }

    private ExprList exprList() {
        // ExpressionList ::= Expression { "," Expression }

        ExprList anExprList = new ExprList();
        anExprList.addElement(expr());
        while (lexer.token == Symbol.COMMA) {
            lexer.nextToken();
            anExprList.addElement(expr());
        }
        return anExprList;
    }

    private Expr expr() {

        Expr left = simpleExpr();
        Symbol op = lexer.token;
        if (op == Symbol.EQ || op == Symbol.NEQ || op == Symbol.LE
                || op == Symbol.LT || op == Symbol.GE || op == Symbol.GT) {
            lexer.nextToken();
            Expr right = simpleExpr();
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    private Expr simpleExpr() {
        Symbol op;

        Expr left = term();
        while ((op = lexer.token) == Symbol.MINUS || op == Symbol.PLUS
                || op == Symbol.OR) {
            //ERRO 8
            lexer.nextToken();
            Expr right = term();

            if (op == Symbol.MINUS || op == Symbol.PLUS) {
                if (left.getType() != Type.intType) {
                    signalError.show("type '" + left.getType().getName() + "' does not support operation '" + op + "'");
                } else if (right.getType() != Type.intType) {
                    signalError.show("type '" + right.getType().getName() + "' does not support operation '" + op + "'");
                }
            }
            if (op == Symbol.OR) {
                if (left.getType() != Type.booleanType) {
                    signalError.show("type '" + left.getType().getName() + "' does not support operation '" + op + "'");
                } else if (right.getType() != Type.booleanType) {
                    signalError.show("type '" + right.getType().getName() + "' does not support operation '" + op + "'");
                }
            }
            left = new CompositeExpr(left, op, right);

        }
        return left;
    }

    private Expr term() {
        Symbol op;

        Expr left = signalFactor();
        while ((op = lexer.token) == Symbol.DIV || op == Symbol.MULT
                || op == Symbol.AND) {
            lexer.nextToken();
            Expr right = signalFactor();

            //ERRO 9
            if (op == Symbol.DIV || op == Symbol.MULT) {
                if (left.getType() != Type.intType) {
                    signalError.show("type '" + left.getType().getName() + "' does not support operation '" + op + "'");
                } else if (right.getType() != Type.intType) {
                    signalError.show("type '" + right.getType().getName() + "' does not support operation '" + op + "'");
                }
            }
            if (op == Symbol.AND) {
                if (left.getType() != Type.booleanType) {
                    signalError.show("type '" + left.getType().getName() + "' does not support operation '" + op + "'");
                } else if (right.getType() != Type.booleanType) {
                    signalError.show("type '" + right.getType().getName() + "' does not support operation '" + op + "'");
                }
            }
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    private Expr signalFactor() {
        Symbol op;
        if ((op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS) {
            lexer.nextToken();
            Expr expr = factor();
            if (expr.getType() == Type.booleanType || expr.getType() == Type.stringType || expr.getType() == Type.voidType || expr.getType() == Type.undefinedType) {
                signalError.show("Operator '" + op + "' does not accepts '" + expr.getType().getName() + "' expressions");
            }
            return new SignalExpr(op, expr);
        } else {
            return factor();
        }
    }

    /*
     * Factor ::= BasicValue | "(" Expression ")" | "!" Factor | "null" |
     *      ObjectCreation | PrimaryExpr
     * 
     * BasicValue ::= IntValue | BooleanValue | StringValue 
     * BooleanValue ::=  "true" | "false" 
     * ObjectCreation ::= "new" Id "(" ")" 
     * PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"  | 
     *                 Id  |
     *                 Id "." Id | 
     *                 Id "." Id "(" [ ExpressionList ] ")" |
     *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
     *                 "this" | 
     *                 "this" "." Id | 
     *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
     *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
     */
    private Expr factor() {
        KraClass skc;
        Method m;
        Expr e;
        ExprList exprList;
        String messageName, ident;
        Variable v = null;
        switch (lexer.token) {
            // IntValue
            case LITERALINT:
                return literalInt();
            // BooleanValue
            case FALSE:
                lexer.nextToken();
                return LiteralBoolean.False;
            // BooleanValue
            case TRUE:
                lexer.nextToken();
                return LiteralBoolean.True;
            // StringValue
            case LITERALSTRING:
                String literalString = lexer.getLiteralStringValue();
                lexer.nextToken();
                return new LiteralString(literalString);
            // "(" Expression ")" |
            case LEFTPAR:
                lexer.nextToken();
                e = expr();
                if (lexer.token != Symbol.RIGHTPAR) {
                    signalError.show(") expected");
                }
                lexer.nextToken();
                return new ParenthesisExpr(e);

            // "null"
            case NULL:
                lexer.nextToken();
                return new NullExpr();
            // "!" Factor
            case NOT:
                lexer.nextToken();
                e = expr();
                //ERRO 15
                if (e.getType() != Type.booleanType) {
                    signalError.show("Operator '!' does not accepts '" + e.getType().getName() + "' values");
                }
                return new UnaryExpr(e, Symbol.NOT);
            // ObjectCreation ::= "new" Id "(" ")"
            case NEW:
                lexer.nextToken();
                if (lexer.token != Symbol.IDENT) {
                    signalError.show("Identifier expected");
                }

                String className = lexer.getStringValue();
                if (!isType(className)) {
                    signalError.show("Class '" + className + "' is not declared");
                }

                for (KraClass kc : kraClassList) {
                    if (kc.getName().equals(className)) {
                        v = new Variable(className, kc);
                    }
                }
                if (v == null) {
                    signalError.show("Class '" + className + "' is not declared");
                }
                /*
                 * // encontre a classe className in symbol table KraClass 
                 *      aClass = symbolTable.getInGlobal(className); 
                 *      if ( aClass == null ) ...
                 */

                lexer.nextToken();
                if (lexer.token != Symbol.LEFTPAR) {
                    signalError.show("( expected");
                }
                lexer.nextToken();
                if (lexer.token != Symbol.RIGHTPAR) {
                    signalError.show(") expected");
                }
                lexer.nextToken();
                /*
                 * return an object representing the creation of an object
                 */
                return new VariableExpr(v);
            /*
             * PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"  | 
             *                 Id  |
             *                 Id "." Id | 
             *                 Id "." Id "(" [ ExpressionList ] ")" |
             *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
             *                 "this" | 
             *                 "this" "." Id | 
             *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
             *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
             */
            case SUPER:
                // "super" "." Id "(" [ ExpressionList ] ")"
                lexer.nextToken();
                if (lexer.token != Symbol.DOT) {
                    signalError.show("'.' expected");
                } else {
                    lexer.nextToken();
                }
                if (lexer.token != Symbol.IDENT) {
                    signalError.show("Identifier expected");
                }
                messageName = lexer.getStringValue();
                /*
                 * para fazer as confer�ncias sem�nticas, procure por 'messageName'
                 * na superclasse/superclasse da superclasse etc
                 */
                lexer.nextToken();
                exprList = realParameters();
                skc = currentclass.getSuperclass();
                if (skc == null) {
                    signalError.show("Current class does not have superclass");
                }
                m = skc.getMethod(messageName);
                while (m == null && (skc = skc.getSuperclass()) != null) {
                    m = skc.getMethod(messageName);
                }
                if (m == null) {
                    signalError.show("Superclass of current class does not have method '" + messageName + "'");
                }
                return new MethodExpr(m);
            case IDENT:
                /*
                 * PrimaryExpr ::=  
                 *                 Id  |
                 *                 Id "." Id | 
                 *                 Id "." Id "(" [ ExpressionList ] ")" |
                 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
                 */
                String firstId = lexer.getStringValue();
                lexer.nextToken();
                if (lexer.token != Symbol.DOT) {
                    // Id
                    // retorne um objeto da ASA que representa um identificador
                    v = symbolTable.getInLocal(firstId);
                    if (v == null) {
                        v = currentMethod.getParam(firstId);
                    }
                    if (v == null) {
                        Object o = symbolTable.getInGlobal(lexer.getStringValue());
                        if (o == null) {
                            //erro 18
                            return null;
                        } else {
                            return new VariableExpr(new Variable(lexer.getStringValue(), Type.undefinedType));
                        }
                    }
                    //ARRUMAR K NÃO FOI DECLARADO COMO VARIÁVEL NEM CLASSE COMOFAZ
                    return new VariableExpr(v);
                } else { // Id "."
                    v = symbolTable.getInLocal(lexer.getStringValue());
                    if (v == null) {
                        v = currentMethod.getParam(lexer.getStringValue());
                    }
                    if (v.getType().getClass() != KraClass.class) {
                        if (symbolTable.getInGlobal(v.getType().getName()) == null) {
                            signalError.show("Message send to a non-object receiver");
                        }
                    }
                    lexer.nextToken(); // coma o "."

                    if (lexer.token != Symbol.IDENT) {
                        signalError.show("Identifier expected");
                    } else {
                        // Id "." Id
                        lexer.nextToken();
                        ident = lexer.getStringValue();
                        if (lexer.token == Symbol.DOT) {
                            // Id "." Id "." Id "(" [ ExpressionList ] ")"
						/*
                             * se o compilador permite vari�veis est�ticas, � poss�vel
                             * ter esta op��o, como
                             *     Clock.currentDay.setDay(12);
                             * Contudo, se vari�veis est�ticas n�o estiver nas especifica��es,
                             * sinalize um erro neste ponto.
                             */
                            lexer.nextToken();
                            if (lexer.token != Symbol.IDENT) {
                                signalError.show("Identifier expected");
                            }
                            messageName = lexer.getStringValue();
                            lexer.nextToken();
                            exprList = this.realParameters();

                        } else if (lexer.token == Symbol.LEFTPAR) {
                            v = symbolTable.getInLocal(firstId);
                            if (v == null) {
                                v = currentMethod.getParam(firstId);
                            }
                            if (v == null) {
                                signalError.show("Variable '" + firstId + "' is not declared");
                            }
                            //PROCURA SE A CLASSE OBJCLASS EXISTE E POSSUI O MÉTODO
                            String objclass = v.getType().getName();
                            m = null;
                            for (KraClass kc : kraClassList) {
                                if (kc.getName().equals(objclass)) {
                                    m = kc.getMethod(ident);
                                    if (m == null) {
                                        skc = kc.getSuperclass();
                                        while (skc != null) {
                                            m = skc.getMethod(ident);
                                            if (m == null) {
                                                skc = skc.getSuperclass();
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (m == null) {
                                signalError.show("Method '" + ident + "' was not found in class '" + objclass + "' or its superclasses");
                            }

                            // Id "." Id "(" [ ExpressionList ] ")"
                            exprList = this.realParameters();
                            /*
                             * para fazer as confer�ncias sem�nticas, procure por
                             * m�todo 'ident' na classe de 'firstId'
                             */
                            if (exprList != null) {
                                boolean isSubClass = false;
                                Iterator<Variable> var = m.getParamList().elements();
                                for (Expr expr : exprList.getExprList()) {
                                    v = var.next();
                                    if (expr.getType() != v.getType()) {
                                        if (expr.getType().getClass() == KraClass.class && v.getType().getClass() == KraClass.class) {
                                            while ((skc = ((KraClass) expr.getType()).getSuperclass()) != null) {
                                                if (skc == v.getType()) {
                                                    isSubClass = true;
                                                }
                                            }
                                        }
                                        if (!isSubClass) {
                                            signalError.show("Type error: the type of the real parameter is not subclass of the type of the formal parameter");
                                        }
                                    }
                                }
                            }

                            return new MethodExpr(m);
                        } else {
                            // retorne o objeto da ASA que representa Id "." Id
                        }
                    }
                }
                break;
            case THIS:
                /*
                 * Este 'case THIS:' trata os seguintes casos: 
                 * PrimaryExpr ::= 
                 *                 "this" | 
                 *                 "this" "." Id | 
                 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
                 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
                 */
                lexer.nextToken();
                if (lexer.token != Symbol.DOT) {
                    // only 'this'
                    // retorne um objeto da ASA que representa 'this'
                    // confira se n�o estamos em um m�todo est�tico
                    return null;
                } else {
                    lexer.nextToken();
                    if (lexer.token != Symbol.IDENT) {
                        signalError.show("Identifier expected");
                    }
                    ident = lexer.getStringValue();
                    lexer.nextToken();
                    // j� analisou "this" "." Id
                    if (lexer.token == Symbol.LEFTPAR) {
                        // "this" "." Id "(" [ ExpressionList ] ")"
					/*
                         * Confira se a classe corrente possui um m�todo cujo nome �
                         * 'ident' e que pode tomar os par�metros de ExpressionList
                         */
                        exprList = this.realParameters();
                        m = currentclass.getMethod(ident);

                        if (exprList != null) {
                            boolean isSubClass = false;
                            Iterator<Variable> var = m.getParamList().elements();
                            for (Expr expr : exprList.getExprList()) {
                                v = var.next();
                                if (expr.getType() != v.getType()) {
                                    if (expr.getType().getClass() == KraClass.class && v.getType().getClass() == KraClass.class) {
                                        while ((skc = ((KraClass) expr.getType()).getSuperclass()) != null) {
                                            if (skc == v.getType()) {
                                                isSubClass = true;
                                            }
                                        }
                                    }
                                    if (!isSubClass) {
                                        signalError.show("Type error: the type of the real parameter is not subclass of the type of the formal parameter");
                                    }
                                }
                            }
                        }

                        return new MethodExpr(m);
                    } else if (lexer.token == Symbol.DOT) {
                        // "this" "." Id "." Id "(" [ ExpressionList ] ")"
                        lexer.nextToken();
                        if (lexer.token != Symbol.IDENT) {
                            signalError.show("Identifier expected");
                        }
                        lexer.nextToken();
                        exprList = this.realParameters();
                    } else {
                        // retorne o objeto da ASA que representa "this" "." Id
					/*
                         * confira se a classe corrente realmente possui uma
                         * vari�vel de inst�ncia 'ident'
                         */
                        InstanceVariable var = currentclass.getInstanceVariable(ident);
                        if (var == null) {
                            signalError.show("Class '" + currentclass.getName() + "' does not have attribute '" + ident + "'");
                        }
                        return new VariableExpr(var);
                    }
                }
                break;
            default:
                signalError.show("Expression expected");
        }
        return null;
    }

    private LiteralInt literalInt() {

        LiteralInt e = null;

        // the number value is stored in lexer.getToken().value as an object of
        // Integer.
        // Method intValue returns that value as an value of type int.
        int value = lexer.getNumberValue();
        lexer.nextToken();
        return new LiteralInt(value);
    }

    private static boolean startExpr(Symbol token) {

        return token == Symbol.FALSE || token == Symbol.TRUE
                || token == Symbol.NOT || token == Symbol.THIS
                || token == Symbol.LITERALINT || token == Symbol.SUPER
                || token == Symbol.LEFTPAR || token == Symbol.NULL
                || token == Symbol.IDENT || token == Symbol.LITERALSTRING;

    }

    private SymbolTable symbolTable;
    private Lexer lexer;
    private SignalError signalError;
    private int nested_whiles;
    private ArrayList<KraClass> kraClassList;
    private Method currentMethod;
    private KraClass currentclass;
}
