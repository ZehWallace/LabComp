package ast;

import java.util.*;

public class InstanceVariableList {

    public InstanceVariableList() {
       instanceVariableList = new ArrayList<InstanceVariable>();
    }

    public void addElement(InstanceVariable instanceVariable) {
       instanceVariableList.add( instanceVariable );
    }

    public Iterator<InstanceVariable> elements() {
    	return this.instanceVariableList.iterator();
    }

    public int getSize() {
        return instanceVariableList.size();
    }

    public ArrayList<InstanceVariable> getInstanceVariableList() {
        return instanceVariableList;
    }
    
    
    public InstanceVariable getInstanceVariable(String name){
        for(InstanceVariable v : instanceVariableList){
            if(v.getName().equals(name)){
                return v;
            }
        }
        return null;
    }
    
    public void genKra(PW pw){
        for (InstanceVariable iv : instanceVariableList){
            iv.genKra(pw);
        }
    }
    void genC(PW pw) {
        for (InstanceVariable iv : instanceVariableList){
            iv.genC(pw);
        }
    }

    private ArrayList<InstanceVariable> instanceVariableList;


}
