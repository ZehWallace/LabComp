package ast;

import java.util.*;

public class ExprList {

    public ExprList() {
        exprList = new ArrayList<Expr>();
    }

    public ExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    public void addElement(Expr expr) {
        exprList.add(expr);
    }

    public void genC(PW pw) {

        int size = exprList.size();
        for (Expr e : exprList) {
            e.genC(pw, false);
            if (--size > 0) {
                pw.print(", ");
            }
        }
    }

    private ArrayList<Expr> exprList;

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    void genKra(PW pw) {
        int cont = 0;
        if (exprList != null) {
            for (Expr expr : exprList) {
                if (cont > 0) {
                    pw.print(", ");
                }
                if (expr != null) {
                    expr.genKra(pw);
                }
                cont++;
            }
        }
    }

}
