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
public class CompositeStatement extends Statement{
    ArrayList<Statement> statementList;
    
    public CompositeStatement(ArrayList<Statement> statementList){
        this.statementList = statementList;
    }
    @Override
    public void genC(PW pw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void genKra(PW pw) {
        pw.print("**compositestatement**");
    }
    
}
