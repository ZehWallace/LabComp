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
    
    }

    @Override
    void genKra(PW pw) {
        for(Statement statement : statementList){
            statement.genKra(pw);
        }
    }
    
}
