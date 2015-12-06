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
            pw.printIdent(skc.getName() + "_");
        }
        if (ismessagesendtoself) {
            pw.print("this.");
        }
        if (!messagesendtoclass.equals("")) {
            pw.print(messagesendtoclass + ".");
        }
        pw.print(method.getName() + "(");
        if(ismessagesendtosuper){
            pw.print("(_class_" + skc.getName() + " *) this");
            if(!exprlist.isEmpty()){
                pw.print(", ");
            }
        }
        if (exprlist != null) {
            exprlist.genKra(pw);
        }
        pw.print(")");
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
