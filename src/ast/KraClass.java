package ast;
/*
 * Krakatoa Class
 */

public class KraClass extends Type {
    
    public KraClass(String name) {
        super(name);
        this.name = name;       
        instanceVariableList = new InstanceVariableList();
        methodList = new MethodList();
        superclass = null;
    }

    public void genKra(PW pw) {
        if (isFInal) {
            pw.print("final ");
        }
        pw.printlnIdent("class " + name + " {");
        pw.add();
        instanceVariableList.genKra(pw);
        methodList.genKra(pw);
        //chamar outro genKra
        pw.sub();
        pw.printlnIdent("}");
    }
    
    @Override
    public String getCname() {
        return getName();
    }
    
    public void addInstanceVariable(InstanceVariable v) {
        instanceVariableList.addElement(v);
    }
    
    public void addMethod(Method m) {
        methodList.addElement(m);
    }
    
    public InstanceVariable getInstanceVariable(String name) {
        InstanceVariable v = instanceVariableList.getInstanceVariable(name);
        return v;
    }
    
    public Method getMethod(String name) {
        Method m = methodList.getInstanceMethod(name);
        return m;
    }
    
    public void setSuperclass(KraClass superclass) {
        this.superclass = superclass;
    }
    
    public KraClass getSuperclass() {
        return superclass;
    }
    
    public boolean isFinal() {
        return isFInal;
    }
    
    public void setIsFInal(boolean isFInal) {
        this.isFInal = isFInal;
    }
    
    private String name;
    private KraClass superclass;
    private InstanceVariableList instanceVariableList;
    private MethodList methodList;
    private boolean isFInal;
   // private MethodList publicMethodList, privateMethodList;
    // m�todos p�blicos get e set para obter e iniciar as vari�veis acima,
    // entre outros m�todos
}
