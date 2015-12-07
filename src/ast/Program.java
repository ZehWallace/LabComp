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
        pw.println("/* deve-se incluir alguns headers porque algumas funções da biblioteca padrão de C são utilizadas na tradução. */\n");
        pw.println("#include <malloc.h>");
        pw.println("#include <stdlib.h>");
        pw.println("#include <stdio.h>\n");
        pw.println("/* define o tipo boolean */");
        pw.println("typedef int boolean;");
        pw.println("#define true  1");
        pw.println("#define false 0\n");
        pw.println("/* define um tipo Func que é um ponteiro para função*/ ");
        pw.println("typedef void (*Func)();\n");
        for (KraClass kc : classList) {
            kc.genC(pw);
        }
        pw.printlnIdent("   /* genC de Program da ASA deve gerar a função main exatamente como abaixo. */\n"
                + "int main() {\n"
                + "  _class_Program *program;\n"
                + "\n"
                + "    /* crie objeto da classe Program e envie a mensagem run para ele */\n"
                + "  program = new_Program();\n"
                + "  ( ( void (*)(_class_Program *) ) program->vt[0] )(program);\n"
                + "  return 0;\n"
                + "}");
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
