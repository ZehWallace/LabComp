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
public class IfStat extends Statement{

    public IfStat(Expr expr, Statement stat){
        this.expr = expr;
        this.stat = stat;
        this.stat2 = null;
    }
    
    public IfStat(Expr expr, Statement stat, Statement stat2){
        this.expr = expr;
        this.stat = stat;
        this.stat2 = stat2;
    }
    
    @Override
    public void genC(PW pw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    private Expr expr;
    private Statement stat;
    private Statement stat2;
    
}
