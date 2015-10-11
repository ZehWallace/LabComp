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
public class WhileStat extends Statement {

    public WhileStat(Expr expr, Statement stat){
        this.expr = expr;
        this.stat = stat;
    }
    
    @Override
    public void genC(PW pw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Expr expr;
    private Statement stat;
}
