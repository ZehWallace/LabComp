package ast;

import java.util.ArrayList;

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
        pw.printIdent("class " + name);
        if (this.superclass != null) {
            pw.print(" extends " + this.superclass.getName());
        }
        pw.println(" {");
        pw.add();
        instanceVariableList.genKra(pw);
        methodList.genKra(pw);
        //chamar outro genKra
        pw.sub();
        pw.printlnIdent("}");
    }

    void genC(PW pw) {
        // fazer alguma coisa com esse final

        pw.printlnIdent("typedef struct _St_" + name + " {");
        pw.add();
        pw.printlnIdent("/* ponteiro para um vetor de métodos da classe */");
        pw.printlnIdent("Func *vt; ");
        
        instanciarVariaveis(this, pw);
        pw.sub();
        pw.printlnIdent("} _class_" + name);
        pw.printlnIdent("_class_" + name + " *new_" + name + "(void);\n");
        
        //VARIAVEIS DE INSTANCIA
        ArrayList<InstanceVariable> vl = instanceVariableList.getInstanceVariableList();
        for (InstanceVariable v : vl) {
            if (v.isStatic()) {
                pw.println(v.getType().getCname() + " _static_" + name + "_" + v.getName() + ";");
            }
        }
        //METODOS
        methodList.genC(pw);

        pw.printlnIdent("// apenas os métodos públicos");
        pw.printlnIdent("Func VTclass_" + this.name + "[] = { ");
        pw.add();

        gerarVetorMetodosPublicos(this, pw);

        pw.sub();
        pw.printlnIdent("};\n");

        pw.printlnIdent("_class_" + this.name + " *new_" + this.name + "(){");
        pw.add();
        pw.printlnIdent("_class_" + this.name + " *t;");
        pw.printlnIdent("if((t = malloc(sizeof(_class_" + this.name + ")))!=NULL)");
        pw.add();
        pw.printlnIdent("t->vt = VTclass_" + this.name + ";");
        pw.sub();
        pw.printlnIdent("return t;");
        pw.sub();
        pw.printlnIdent("}\n");

    }

    public void instanciarVariaveis(KraClass kc, PW pw) {
        if (kc.superclass != null) {
            instanciarVariaveis(kc.superclass, pw);
        }
        ArrayList<InstanceVariable> ivl = kc.instanceVariableList.getInstanceVariableList();
        for (InstanceVariable iv : ivl) {
            if (!iv.isStatic()) {
                pw.printIdent(iv.getType().getCname() + " _" + kc.name + "_" + iv.getName());
                pw.println(";");
            }
        }
    }

    public void gerarVetorMetodosPublicos(KraClass kc, PW pw) {
        if (kc.superclass != null) {
            gerarVetorMetodosPublicos(kc.superclass, pw);
        }
        ArrayList<Method> ml = kc.getMethodList().getMethodList();
        for (Method m : ml) {
            pw.printIdent("(void (*) () ) ");
            pw.print("_" + m.getKc().getName() + "_" + m.getName());
            if (!(kc.equals(this) && m.equals(ml.get(ml.size()-1)))) {
                pw.print(",");
            }
            pw.println("");
        }
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
        Method m = methodList.getMethod(name);
        return m;
    }

    public Method getNonStaticMethod(String name) {
        Method m = methodList.getNonStaticMethod(name);
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

    public MethodList getMethodList() {
        return methodList;
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
