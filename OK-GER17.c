/* deve-se incluir alguns headers porque algumas funções da biblioteca padrão de C são utilizadas na tradução. */

#include <malloc.h>
#include <stdlib.h>
#include <stdio.h>

/* define o tipo boolean */
typedef int boolean;
#define true  1
#define false 0

/* define um tipo Func que é um ponteiro para função*/ 
typedef void (*Func)();

typedef struct _St_A {
   /* ponteiro para um vetor de métodos da classe */
   Func *vt; 
} _class_A;
_class_A *new_A(void);

int _static_A_get0(_class_A *this){
   return 0;
}

int _static_A_get1(_class_A *this){
   return 1;
}

int _static_A_ident(_class_A *this, int _n){
   return _n;
}

// apenas os métodos públicos
Func VTclass_A[] = { 
};

_class_A *new_A(){
   _class_A *t;
   if((t = malloc(sizeof(_class_A)))!=NULL)
      t->vt = VTclass_A;
   return t;
}

typedef struct _St_B {
   /* ponteiro para um vetor de métodos da classe */
   Func *vt; 
} _class_B;
_class_B *new_B(void);

int _static_B_get(_class_B *this){
   return 3;
}

void _static_B_print(_class_B *this){
   printf("%d ", _static_A_ident((_class_A *) this, 2));
   printf("%d ", _static_B_get((_class_B *) this));
   printf("%d ", 4);
}

// apenas os métodos públicos
Func VTclass_B[] = { 
};

_class_B *new_B(){
   _class_B *t;
   if((t = malloc(sizeof(_class_B)))!=NULL)
      t->vt = VTclass_B;
   return t;
}

typedef struct _St_Program {
   /* ponteiro para um vetor de métodos da classe */
   Func *vt; 
} _class_Program;
_class_Program *new_Program(void);

void _Program_run(_class_Program *this){
   puts("");
   printf("\n");
   puts("Ok-ger17");
   printf("\n");
   puts("The output should be: ");
   printf("\n");
   puts("0 1 2 3 4");
   printf("\n");
   printf("%d ", _static_A_get0((_class_A *) this));
   printf("%d ", _static_A_get1((_class_A *) this));
   _static_B_print((_class_B *) this);
   
}

// apenas os métodos públicos
Func VTclass_Program[] = { 
   (void (*) () ) _Program_run
};

_class_Program *new_Program(){
   _class_Program *t;
   if((t = malloc(sizeof(_class_Program)))!=NULL)
      t->vt = VTclass_Program;
   return t;
}

   /* genC de Program da ASA deve gerar a função main exatamente como abaixo. */
int main() {
  _class_Program *program;

    /* crie objeto da classe Program e envie a mensagem run para ele */
  program = new_Program();
  ( ( void (*)(_class_Program *) ) program->vt[0] )(program);
  return 0;
}
