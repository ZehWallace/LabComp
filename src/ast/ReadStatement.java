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
public class ReadStatement extends Statement {

    public ReadStatement(ArrayList<Variable> variableList) {
        this.variableList = variableList;
    }

    @Override
    public void genC(PW pw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void genKra(PW pw) {
        int cont = 0;
        pw.printIdent("read (");
        for (Variable v : variableList) {
            if(cont > 0){
                pw.print(", ");
            }
            pw.print(v.getName());
            cont++;
        }
        pw.println(");");
    }

    private ArrayList<Variable> variableList;
}
