/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;

/**
 *
 * @author guilherme
 */
public class WriteStatement extends Statement {

    private ExprList exprlist;

    public WriteStatement(ExprList exprlist) {
        this.exprlist = exprlist;
    }

    @Override
    public void genC(PW pw) {

        ArrayList<Expr> exprlist2 = exprlist.getExprList();
        for (Expr e : exprlist2) {
            Type t = e.getType();
            switch(t.getName()){
                case "int":
                    pw.printIdent("printf(\"%d \", ");
                    e.genC(pw, false);
                    pw.println(");");
                    break;
                case "String":
                    pw.printIdent("puts(");
                    e.genC(pw, false);
                    pw.println(");");
                    break;
                case "char":
                    pw.printIdent("printf(\"%c \", ");
                    e.genC(pw, false);
                    pw.println(");");
                    break;
                case "float":
                    pw.printIdent("printf(\"%f \", ");
                    e.genC(pw, false);
                    pw.println(");");
                    break;
                default:
                    pw.print("nao contavam com a minha astucia ");
            }
        }
    }

    @Override
    void genKra(PW pw) {
        pw.printIdent("write (");
        exprlist.genKra(pw);
        pw.println(");");
    }
}
