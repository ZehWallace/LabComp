package ast;

import java.util.*;

public class ParamList {

    public ParamList() {
        paramList = new ArrayList<Variable>();
    }

    public void addElement(Variable v) {
        paramList.add(v);
    }

    public Iterator<Variable> elements() {
        return paramList.iterator();
    }

    public int getSize() {
        return paramList.size();
    }

    public Variable getParam(String name) {
        for (Variable v : paramList) {
            if (v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Variable v : paramList) {
            sb.append(v.getName()).append("_").append(v.getType().getName());
        }
        sb.append("@");
        return sb.toString();

    }

    public String getTypeNames() {
        StringBuilder sb = new StringBuilder();
        for (Variable v : paramList) {
            sb.append(v.getType().getName());
        }
        sb.append("@");
        return sb.toString();
    }

    private ArrayList<Variable> paramList;

    void genKra(PW pw) {
        int cont = 0;
        for (Variable v : paramList) {
            if (cont > 0) {
                pw.print(", ");
            }
            pw.print(v.getType().getName() + " " + v.getName());
            cont++;
        }
    }

    void genC(PW pw) {
        int cont = 0;
        for (Variable v : paramList) {
            if (cont > 0) {
                pw.print(", ");
            }
            if (v.getType() instanceof KraClass) {
                pw.print("_class_");
            }
            pw.print(v.getType().getCname() + " ");
            if (v.getType() instanceof KraClass) {
                pw.print("*");
            }
            pw.print(v.getCname());

            cont++;
        }
    }

    boolean isEmpty() {
        return paramList.isEmpty();
    }

}
