/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Bruno
 */
public class NewStatement extends Statement{
    KraClass kc;
    ExprList exprlist;
    
    public NewStatement(KraClass kc){
        this.kc = kc;
    }
    
    @Override
    public void genC(PW pw) {
    pw.printIdent("");
    }

    @Override
    void genKra(PW pw) {
        pw.printlnIdent(" new " + kc.getName() + " ();");
    }
    
}
