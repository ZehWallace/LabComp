package ast;

public class VariableExpr extends Expr {

    public VariableExpr(Variable v, boolean ismessagetoself, String messagetoclass) {
        this.v = v;
        this.ismessagetoself = ismessagetoself;
        this.messagetoclass = messagetoclass;
    }

    public void genC(PW pw, boolean putParenthesis) {
        pw.print(v.getName());
    }

    public void genKra(PW pw) {
        if(ismessagetoself){
            pw.print("this.");
        }
        if(!messagetoclass.equals("")){
            pw.print(messagetoclass + ".");
        }
        pw.print(v.getName());
    }

    public Type getType() {
        return v.getType();
    }

    private Variable v;
    private boolean ismessagetoself;
    private String messagetoclass;
}
