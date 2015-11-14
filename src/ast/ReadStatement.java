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
        pw.printIdent("scanf(");
        for (Variable v : variableList) {
            switch(v.getType().getName()){
                case "int":
                    pw.print("%d");
                    break;
                case "String":
                    pw.print("%s");
                    break;
                case "char":
                    pw.print("%c");
                    break;
                case "float":
                    pw.print("%f");
                    break;
                default:
                    pw.print("nao contavam com a minha astucia ");
            }
        }
        for (Variable v : variableList) {
            pw.print(", ");
            pw.print(v.getName());
        }
        
        pw.println(");");
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
