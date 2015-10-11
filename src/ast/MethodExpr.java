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

    public MethodExpr(Method method) {
        this.method = method;
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

    Method method;

    @Override
    void genKra(PW pw) {
        pw.print(method.getName() + "()batata pode acontecer");
    }
}
