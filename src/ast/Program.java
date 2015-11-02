package ast;

import java.util.*;
import comp.CompilationError;

public class Program {

    public Program(ArrayList<KraClass> classList, ArrayList<MetaobjectCall> metaobjectCallList,
            ArrayList<CompilationError> compilationErrorList) {
        this.classList = classList;
        this.metaobjectCallList = metaobjectCallList;
        this.compilationErrorList = compilationErrorList;
    }

    public void genKra(PW pw) {
        for (KraClass kc : classList) {
            kc.genKra(pw);
        }
    }

    public void genC(PW pw) {
        pw.println("#include <malloc.h>");
        pw.println("#include <stdlib.h>");
        pw.println("#include <stdio.h>");
        pw.println("/* define o tipo boolean */");
        pw.println("typedef int boolean;");
        pw.println("#define true 1");
        pw.println("#define false 0");
        pw.println("/* define um tipo Func que ´e um ponteiro para fun¸c~ao */ ");
        pw.println("typedef void (*Func)();");
        for (KraClass kc : classList) {
            kc.genC(pw);
        }
    }

    public ArrayList<KraClass> getClassList() {
        return classList;
    }

    public ArrayList<MetaobjectCall> getMetaobjectCallList() {
        return metaobjectCallList;
    }

    public boolean hasCompilationErrors() {
        return compilationErrorList != null && compilationErrorList.size() > 0;
    }

    public ArrayList<CompilationError> getCompilationErrorList() {
        return compilationErrorList;
    }

    private ArrayList<KraClass> classList;
    private ArrayList<MetaobjectCall> metaobjectCallList;

    ArrayList<CompilationError> compilationErrorList;

}
