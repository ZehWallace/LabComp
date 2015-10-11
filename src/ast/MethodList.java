/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;

/**
 *
 * @author Bruno
 */
public class MethodList {

    public MethodList() {
        methodList = new ArrayList<>();
    }
    
    public void addElement(Method meth){
        methodList.add(meth);
    }
    
    public int getSize(){
        return methodList.size();
    }
    
    public Method getInstanceMethod(String name){
        for(Method m : methodList){
            if(m.getName().equals(name)){
                return m;
            }
        }
        return null;
    }
    
    ArrayList<Method> methodList;

    public void genKra(PW pw) {
        for(Method m : methodList){
            m.genKra(pw);
        }
    }
}
