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
public class NullStatement extends Statement{

    @Override
    public void genC(PW pw) {
        pw.printlnIdent("");
    }

    @Override
    void genKra(PW pw) {
        pw.printlnIdent("");
    }
    
}
