package ast;

public class VariableExpr extends Expr {

    public VariableExpr(Variable v, boolean ismessagetoself, String messagetoclass, boolean isNew) {
        this.v = v;
        this.ismessagetoself = ismessagetoself;
        this.messagetoclass = messagetoclass;
        this.isNew = isNew;
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
        if(isNew){
            pw.print("new ");
        }
        pw.print(v.getName());
        if(isNew){
            pw.print("()");
        }
    }

    public Type getType() {
        return v.getType();
    }

    private Variable v;
    private boolean ismessagetoself;
    private String messagetoclass;
    private boolean isNew;
}
