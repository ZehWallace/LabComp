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
public class WriteStatement extends Statement {

    public WriteStatement(ExprList exprlist){
        this.exprlist = exprlist;
    }
    
    @Override
    public void genC(PW pw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private ExprList exprlist;

    @Override
    void genKra(PW pw) {
        pw.printIdent("write (");
        exprlist.genKra(pw);
        pw.println(");");
    }
}
