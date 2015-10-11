/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Symbol;

/**
 *
 * @author Bruno
 */
public class Method {
    private boolean isStatic;
    private String name;
    private Type type;
    private ArrayList<Statement> statementList;
    private ParamList paramList;
    private Symbol qualifier;
    public Method(String name, Type type, Symbol qualifier){
        this.name = name;
        this.type = type;
        this.qualifier = qualifier;
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
    
    public Variable getParam(String name){
        return paramList.getParam(name);
    }
    
    public void setParamList(ParamList paramList){
        this.paramList = paramList;
    }
    
    public void setStatementList(ArrayList<Statement> statementList){
        this.statementList = statementList;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
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

    public Symbol getQualifier() {
        return qualifier;
    }
    
    
}
