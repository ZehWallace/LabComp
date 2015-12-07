package ast;

public class Variable {

    private String name;
    private Type type;
    private KraClass kc;
    private boolean isStatic;
    
    public Variable(String name, Type type) {
        this.name = name;
        this.type = type;
        kc = null;
    }

    public String getName() {
        return name;
    }

    public String getCname() {
        return "_" + name;
    }

    public Type getType() {
        return type;
    }

    public void genKra(PW pw) {
        pw.print(name);
    }

    public void setKc(KraClass kc) {
        this.kc = kc;
    }

    public KraClass getKc() {
        return kc;
    }
//
//    public boolean isIsStatic() {
//        return isStatic;
//    }
//
//    public void setIsStatic(boolean isStatic) {
//        this.isStatic = isStatic;
//    }
    
    
}
