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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Variable v : paramList) {
            sb.append(v.getName()).append("_").append(v.getType().getName());
        }
        sb.append("@");
        return sb.toString();

    }

    private ArrayList<Variable> paramList;

}
