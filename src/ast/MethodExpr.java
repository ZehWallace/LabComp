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
public class MethodExpr extends Expr {

    private Method method;
    private boolean ismessagesendtoself;
    private String messagesendtoclass;
    private boolean ismessagesendtosuper;
    private ExprList exprlist;

    public MethodExpr(Method method, ExprList exprlist, boolean ismessagesendtoself, boolean ismessagesendtosuper, String messagesendtoclass) {
        this.method = method;
        this.ismessagesendtoself = ismessagesendtoself;
        this.ismessagesendtosuper = ismessagesendtosuper;
        this.messagesendtoclass = messagesendtoclass;
        this.exprlist = exprlist;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        KraClass skc = null;
        if (ismessagesendtosuper) {
            skc = method.getKc();
            pw.print("_" + skc.getName() + "_");
        }
        if (ismessagesendtoself) {
            KraClass kc = method.getKc();
            ArrayList<Method> methodlist = new ArrayList<>();
            gerarListaMetodos(kc, methodlist);
            int pos = 0;
            for (Method m : methodlist) {
                if (m.equals(method)) {
                    pos = methodlist.indexOf(m);
                    break;
                }
            }

            pw.print("( (" + method.getType().getCname() + " (*)(_class_" + kc.getName() + " *");
            if (exprlist != null) {
                ArrayList el = exprlist.getExprList();
                for (Object e : el) {
                    pw.print(", " + ((Expr) e).getType().getCname());
                }
            }
            pw.print(")) " + "this" + "->[" + pos + "] ) (" + "this");
        } else if (!messagesendtoclass.equals("")) {
            KraClass kc = method.getKc();
            ArrayList<Method> methodlist = new ArrayList<>();
            gerarListaMetodos(kc, methodlist);
            int pos = 0;
            for (Method m : methodlist) {
                if (m.equals(method)) {
                    pos = methodlist.indexOf(m);
                    break;
                }
            }

            pw.print("( (" + method.getType().getCname() + " (*)(_class_" + kc.getName() + " *");
            if (exprlist != null) {
                ArrayList el = exprlist.getExprList();
                for (Object e : el) {
                    pw.print(", " + ((Expr) e).getType().getCname());
                }
            }
            pw.print(")) _" + messagesendtoclass + "->[" + pos + "] ) (_" + messagesendtoclass);
        } else {
            pw.print(method.getName() + "(");
        }
        if (ismessagesendtosuper) {
            pw.print("(_class_" + skc.getName() + " *) this");

        }
        if (exprlist != null) {
            pw.print(", ");
            exprlist.genC(pw);
        }
        pw.print(")");
    }

    public void gerarListaMetodos(KraClass kc, ArrayList<Method> methodlist) {
        if (kc.getSuperclass() != null) {
            gerarListaMetodos(kc.getSuperclass(), methodlist);
        }
        ArrayList<Method> ml = kc.getMethodList().getMethodList();
        for (Method m : ml) {
            methodlist.add(m);
        }
    }

    @Override
    public Type getType() {
        return method.getType();
    }

    public String getName() {
        return method.getName();
    }

    public Method getMethod() {
        return method;
    }

    @Override
    void genKra(PW pw) {
        if (ismessagesendtosuper) {
            pw.print("super.");
        }
        if (ismessagesendtoself) {
            pw.print("this.");
        }
        if (!messagesendtoclass.equals("")) {
            pw.print(messagesendtoclass + ".");
        }
        pw.print(method.getName() + "(");
        if (exprlist != null) {
            exprlist.genKra(pw);
        }
        pw.print(")");
    }
}
