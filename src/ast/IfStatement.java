/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author guilherme
 */
public class IfStatement extends Statement {

    public IfStatement(Expr expr, Statement stat, Statement stat2) {
        this.expr = expr;
        this.stat = stat;
        this.stat2 = stat2;
    }

    @Override
    public void genC(PW pw) {
        pw.printIdent("if (");
        expr.genC(pw,false);
        pw.println("){");
        pw.add();
        stat.genC(pw);
        pw.sub();
        pw.printlnIdent("}");
        if (stat2 != null) {
            pw.add();
            pw.println(" else {");
            stat2.genC(pw);
            pw.sub();
            pw.printlnIdent("}");
        }
    }

    private Expr expr;
    private Statement stat;
    private Statement stat2;

    @Override
    void genKra(PW pw) {
        pw.printIdent("if (");
        expr.genKra(pw);
        pw.println("){");
        pw.add();
        stat.genKra(pw);
        pw.sub();
        pw.printlnIdent("}");
        if (stat2 != null) {
            pw.add();
            pw.println(" else {");
            stat2.genKra(pw);
            pw.sub();
            pw.printlnIdent("}");
        }
    }

}
