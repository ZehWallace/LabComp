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
        for (Variable v : variableList) {
            pw.printlnIdent("{");
            pw.add();
            pw.printlnIdent("char __s[512];");
            pw.printlnIdent("gets(__s)");
            pw.printIdent("sscanf(__s, \"");
            switch (v.getType().getCname()) {
                case "int":
                    pw.print("%d\", &");
                    pw.print(v.getCname());
                    pw.println(");");
                    break;
                case "String":
                    pw.print("%s\", ");
                    pw.print(v.getCname());
                    pw.println(");");
                    break;
                case "char":
                    pw.print("%c\", &");
                    pw.print(v.getCname());
                    pw.println(");");
                    break;
                case "float":
                    pw.print("%f\", &");
                    pw.print(v.getCname());
                    pw.println(");");
                    break;
                default:
                    pw.print("nao contavam com a minha astucia ");
            }
            pw.sub();
            pw.printlnIdent("}");
        }
    }

    @Override
    void genKra(PW pw) {
        int cont = 0;
        pw.printIdent("read (");
        for (Variable v : variableList) {
            if (cont > 0) {
                pw.print(", ");
            }
            pw.print(v.getName());
            cont++;
        }
        pw.println(");");
    }

    private ArrayList<Variable> variableList;
}
