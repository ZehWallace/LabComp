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
public class Method {
    private String name;
    private Type type;
    private ArrayList<Statement> statementList;
    private ParamList paramList;
    public Method(String name, Type type){
        this.name = name;
        this.type = type;
        statementList = new ArrayList<>();
        paramList = new ParamList();
    }
    
    public void addStatement(Statement statement){
        statementList.add(statement);
    }
    
    public void addParam(Variable param){
        paramList.addElement(param);
    }
    
    public ParamList getParamList(){
        return paramList;
    }
    
    public void setParamList(ParamList paramList){
        this.paramList = paramList;
    }
    
    public void setStatementList(ArrayList<Statement> statementList){
        this.statementList = statementList;
    }
    
    @Override
    public String toString(){
        return paramList.toString();
    }
    
    
    public String getName(){
        return name;
    }

    public Type getType() {
        return type;
    }
}
