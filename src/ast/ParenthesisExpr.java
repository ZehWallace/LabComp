package ast;

public class ParenthesisExpr extends Expr {
    
    public ParenthesisExpr( Expr expr ) {
        this.expr = expr;
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
        pw.print("(");
        expr.genC(pw, false);
        pw.print(")");
    }
    
    public Type getType() {
        return expr.getType();
    }
    
    private Expr expr;

    @Override
    void genKra(PW pw) {
        pw.print("( ");
        expr.genKra(pw);
        pw.print(")");
    }
    
}