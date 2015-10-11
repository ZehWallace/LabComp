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

    public MethodExpr(Method method, boolean ismessagesendtoself, boolean ismessagesendtosuper, String messagesendtoclass) {
        this.method = method;
        this.ismessagesendtoself = ismessagesendtoself;
        this.ismessagesendtosuper = ismessagesendtosuper;
        this.messagesendtoclass = messagesendtoclass;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {

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

    private Method method;
    private boolean ismessagesendtoself;
    private String messagesendtoclass;
    private boolean ismessagesendtosuper;

    @Override
    void genKra(PW pw) {
        if(ismessagesendtosuper){
            pw.print("super.");
        }
        if(ismessagesendtoself){
            pw.print("this.");
        }
        if(!messagesendtoclass.equals("")){
            pw.print(messagesendtoclass + ".");
        }
        pw.print(method.getName() + "()batata pode acontecer");
    }
}
