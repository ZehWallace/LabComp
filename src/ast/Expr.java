package ast;

abstract public class Expr {
    abstract public void genC( PW pw, boolean putParenthesis );
      // new method: the type of the expression
    abstract public Type getType();

    abstract void genKra(PW pw);
}