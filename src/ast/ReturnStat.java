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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
