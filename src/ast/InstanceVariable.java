package ast;

public class InstanceVariable extends Variable {

    public InstanceVariable( String name, Type type, boolean isStatic ) {
        super(name, type);
        this.setisStatic(isStatic);
    }

    public void genKra(PW pw) {
        super.genKra(pw);
    }
    public void setisStatic(boolean isStatic){
        this.isStatic = isStatic;
    }
    public boolean isStatic(){
        return isStatic;
    }
    
    private boolean isStatic;
}