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
public class WhileStatement extends Statement {

    public WhileStatement(Expr expr, Statement stat){
        this.expr = expr;
        this.stat = stat;
    }
    
    @Override
    public void genC(PW pw) {
    
    }
    
    private Expr expr;
    private Statement stat;

    @Override
    void genKra(PW pw) {
        pw.printIdent("while (");
        expr.genKra(pw);
        pw.println(") {");
        pw.add();
        stat.genKra(pw);
        pw.sub();
        pw.printlnIdent("}");
    }
}
