/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Symbol;

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
    private KraClass self;

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
        if (method.isStatic()) {
            pw.print("_static_" + method.getKc().getCname() + "_");
        } else if (method.getQualifier().equals(Symbol.PRIVATE)) {
            pw.print("_" + method.getKc().getCname() + "_");
        } else if (ismessagesendtoself) {
            KraClass kc = method.getKc();
            ArrayList<Method> methodlist = new ArrayList<>();
            gerarListaMetodos(kc, kc, methodlist);
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
            pw.print(")) " + "this" + "->vt[" + pos + "] ) (");
            if (!kc.equals(self)) {
                pw.print("(_class_" + kc.getName() + " *) ");
            }
            pw.print("this");
        } else if (!messagesendtoclass.equals("")) {
            KraClass kc = method.getKc();
            ArrayList<Method> methodlist = new ArrayList<>();
            gerarListaMetodos(kc, kc, methodlist);
            int pos = 0;
            for (Method m : methodlist) {
                if (m.equals(method)) {
                    pos = methodlist.indexOf(m);
                    break;
                }
            }

            pw.print("( (");
            if (method.getType() instanceof KraClass) {
                pw.print("_class_");
            }
            pw.print(method.getType().getCname() + " ");
            if (method.getType() instanceof KraClass) {
                pw.print("*");
            }
            pw.print("(*)(_class_" + kc.getName() + " *");
            if (exprlist != null) {
                ArrayList el = exprlist.getExprList();
                for (Object e : el) {
                    Type t = ((Expr) e).getType();
                    pw.print(", ");
                    if (t instanceof KraClass) {
                        pw.print("_class_");
                    }
                    pw.print(t.getCname());
                    if (t instanceof KraClass) {
                        pw.print(" *");
                    }

                }
            }
            pw.print(")) _" + messagesendtoclass + "->vt[" + pos + "] ) (");
            if (!kc.equals(self)) {
                pw.print("(_class_" + kc.getName() + " *) ");
            }
            pw.print("_" + messagesendtoclass);
        } else {
            pw.print(method.getName() + "(");
        }
        if (method.isStatic()) {
            pw.print(method.getName() + "(");
            pw.print("(_class_" + method.getKc().getName() + " *) this");
        } else if (method.getQualifier().equals(Symbol.PRIVATE)) {
            pw.print(method.getName() + "(");
            pw.print("(_class_" + method.getKc().getName() + " *) this");
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

    public void gerarListaMetodos(KraClass currentclass, KraClass kc, ArrayList<Method> methodlist) {
        boolean adicionar = true;
        if (kc.getSuperclass() != null) {
            gerarListaMetodos(currentclass, kc.getSuperclass(), methodlist);
        }
        ArrayList<Method> ml = kc.getMethodList().getMethodList();
        if (!currentclass.equals(kc)) {
            for (Method m : ml) {
                if (currentclass.getMethod(m.getName()) == null) {
                    adicionar = true;
                    for (Method m2 : methodlist) {
                        if (m2.getName().equals(m.getName())) {
                            adicionar = false;
                        }
                    }
                    if (adicionar && m.getQualifier() != Symbol.PRIVATE) {
                        if (!m.isStatic()) {
                            methodlist.add(m);
                        }
                    }
                } else {
                    Method method = currentclass.getMethod(m.getName());

                    adicionar = true;
                    for (Method m2 : methodlist) {
                        if (m2.getName().equals(method.getName())) {
                            adicionar = false;
                        }
                    }

                    if (adicionar && !method.isStatic()) {
                        methodlist.add(method);
                    }
                }
            }
        } else {
            for (Method m : ml) {
                adicionar = true;
                for (Method m2 : methodlist) {
                    if (m.getName().equals(m2.getName())) {
                        adicionar = false;
                    }
                }
                if (adicionar && m.getQualifier() != Symbol.PRIVATE) {
                    if (!m.isStatic()) {
                        methodlist.add(m);
                    }
                }
            }
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

    public void setSelf(KraClass self) {
        this.self = self;
    }

}
