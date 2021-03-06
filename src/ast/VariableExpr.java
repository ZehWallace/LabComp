package ast;

public class VariableExpr extends Expr {

    private Variable v;
    private boolean ismessagetoself;
    private String messagetoclass;
    private boolean isNew;

    public VariableExpr(Variable v, boolean ismessagetoself, String messagetoclass, boolean isNew) {
        this.v = v;
        this.ismessagetoself = ismessagetoself;
        this.messagetoclass = messagetoclass;
        this.isNew = isNew;
    }

    public void genC(PW pw, boolean putParenthesis) {
        if (ismessagetoself) {
            pw.print("this->");
        }
        if (isNew) {
            pw.print("new");
        }
        if (putParenthesis) {
            pw.print("(");
        }
        if (v instanceof InstanceVariable) {
            if (((InstanceVariable) v).isStatic()) {
                pw.print("_static");
            }
        }
        if (v.getKc() != null) {
            pw.print("_" + v.getKc().getName());
        }

        pw.print("_" + v.getName());
        if (putParenthesis) {
            pw.print(")");
        }
        if (isNew) {
            pw.print("()");
        }
    }

    public void genKra(PW pw) {
        if (ismessagetoself) {
            pw.print("this.");
        }
        if (!messagetoclass.equals("")) {
            pw.print(messagetoclass + ".");
        }
        if (isNew) {
            pw.print("new ");
        }
        pw.print(v.getName());
        if (isNew) {
            pw.print("()");
        }
    }

    public Type getType() {
        return v.getType();
    }
}
