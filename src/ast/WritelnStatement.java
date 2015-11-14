/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;

/**
 *
 * @author Bruno
 */
public class WritelnStatement extends Statement{

    public WritelnStatement(ExprList exprlist){
        this.exprlist = exprlist;
    }
    
    @Override
    public void genC(PW pw) {
        pw.printIdent("printf (\"");
        ArrayList<Expr> exprlist2 = exprlist.getExprList();
        for (Expr e : exprlist2) {
            Type t = e.getType();
            switch(t.getName()){
                case "int":
                    pw.print("%d");
                    break;
                case "String":
                    pw.print("%s");
                    break;
                case "char":
                    pw.print("%c");
                    break;
                case "float":
                    pw.print("%f");
                    break;
                default:
                    pw.print("nao contavam com a minha astucia ");
            }
        }
        pw.print("\\n\", ");
        exprlist.genC(pw);
        pw.println(");");
    }

    @Override
    void genKra(PW pw) {
        pw.printIdent("writeln (");
        exprlist.genKra(pw);
        pw.println(");");
    }
    
    private ExprList exprlist;
    
}
