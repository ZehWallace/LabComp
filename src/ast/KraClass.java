package ast;
/*
 * Krakatoa Class
 */

public class KraClass extends Type {

    public KraClass(String name) {
        super(name);
        instanceVariableList = new InstanceVariableList();
    }

    public String getCname() {
        return getName();
    }

    public void addInstanceVariable(InstanceVariable v) {
        instanceVariableList.addElement(v);
    }

    public InstanceVariable getInstanceVariable(String name) {
        InstanceVariable v = instanceVariableList.getInstanceVariable(name);
        return v;
    }

    public void setSuperclass(KraClass superclass) {
        this.superclass = superclass;
    }

    private String name;
    private KraClass superclass;
    private InstanceVariableList instanceVariableList;₢
   // private MethodList publicMethodList, privateMethodList;
    // m�todos p�blicos get e set para obter e iniciar as vari�veis acima,
    // entre outros m�todos
}
