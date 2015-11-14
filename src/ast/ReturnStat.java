/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Guilherme
 */
public class ReturnStat extends Statement {
    Expr expr;
    
    public ReturnStat(Expr expr){
        this.expr = expr;
    }
    @Override
    public void genC(PW pw) {
        pw.printIdent("return ");
        expr.genC(pw,false);
        pw.println(";");
    }

    @Override
    void genKra(PW pw) {
        pw.printIdent("return ");
        expr.genKra(pw);
        pw.println(";");
    }
}
