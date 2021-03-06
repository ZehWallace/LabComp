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
public class AssignExprLocalDecStatement extends Statement {

    ArrayList<VariableExpr> variableexprlist;
    Expr exprl, exprr;

    //caso declaração
    public AssignExprLocalDecStatement(ArrayList<VariableExpr> variableexprlist) {
        this.variableexprlist = variableexprlist;
        this.exprl = null;
        this.exprr = null;
    }

    //caso assignment
    public AssignExprLocalDecStatement(Expr exprl, Expr exprr) {
        this.exprl = exprl; //se exprr e exprl != null -> assignment
        this.exprr = exprr; //se exprr == null -> método
        this.variableexprlist = null;
    }

    @Override
    public void genC(PW pw) {
        if (variableexprlist != null) {
            for (VariableExpr ve : variableexprlist) {
                pw.printIdent("");
                if (ve.getType() instanceof KraClass) {
                    pw.print("_class_");
                }
                pw.print(ve.getType().getCname());
                if (ve.getType() != Type.stringType) {
                    pw.print(" ");
                }
                if(ve.getType() instanceof KraClass){
                    pw.print("*");
                }
                ve.genC(pw, false);
                pw.println(";");
            }
        } else if (exprl != null && exprr != null) {
            pw.printIdent("");
            exprl.genC(pw, false);
            pw.print(" = ");
            exprr.genC(pw, false);
            pw.println(";");
        } else if (exprl != null) {
            pw.printIdent("");
            exprl.genC(pw, false);
            pw.println(";");
        }
    }

    @Override
    void genKra(PW pw) {
        if (variableexprlist != null) {
            for (VariableExpr ve : variableexprlist) {
                pw.printIdent(ve.getType().getName() + " ");
                ve.genKra(pw);
                pw.println(";");
            }
        } else if (exprl != null && exprr != null) {
            pw.printIdent("");
            exprl.genKra(pw);
            pw.print(" = ");
            exprr.genKra(pw);
            pw.println(";");
        } else if (exprl != null) {
            pw.printIdent("");
            exprl.genKra(pw);
            pw.println(";");
        }
    }

}
