package ast;

public class InstanceVariable extends Variable {

    public InstanceVariable( String name, Type type, boolean isStatic ) {
        super(name, type);
        this.isStatic = isStatic;
    }

    @Override
    public void genKra(PW pw) {
        pw.printIdent("");
        if(isStatic){
            pw.print("static ");
        }
        pw.print("private " + super.getType().getName() + " ");
        super.genKra(pw);
        pw.println(";");
    }
    void genC(PW pw) {
        if(!isStatic){
            pw.printIdent(super.getType().getCname() + " " + super.getName());
            pw.println(";");
        }
    }
    
    public void setisStatic(boolean isStatic){
        this.isStatic = isStatic;
    }
    public boolean isStatic(){
        return isStatic;
    }
    
    private boolean isStatic;


}