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
        instanceMethodList = new ArrayList<>();
    }
    
    public void addElement(Method meth){
        instanceMethodList.add(meth);
    }
    
    public int getSize(){
        return instanceMethodList.size();
    }
    
    public Method getInstanceMethod(String name){
        for(Method m : instanceMethodList){
            if(m.getName().equals(name)){
                return m;
            }
        }
        return null;
    }
    
    ArrayList<Method> instanceMethodList;
}
