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

int _static_A_n;
int _static_A_get(_class_A *this){
   return _static_A_n;
}

void _static_A_set(_class_A *this, int _n){
   _static_A_n = _n;
}

void _A_print(_class_A *this){
   _static_A_set((_class_A *) this, 2);
   
   printf("%d ", _static_A_get((_class_A *) this));
}

// apenas os métodos públicos
Func VTclass_A[] = { 
   (void (*) () ) _A_print
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

void _static_B_print(_class_B *this){
   _class_A *_a;
   
   _a = new_A();
   ( (void (*)(_class_A *)) _a->vt[0] ) ((_class_A *) _a);
   
}

void _B_m(_class_B *this){
   _static_A_set((_class_A *) this, 1);
   
   printf("%d ", _static_A_get((_class_A *) this));
   _static_B_print((_class_B *) this);
   
}

// apenas os métodos públicos
Func VTclass_B[] = { 
   (void (*) () ) _B_m
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
   _class_B *_b;
   
   puts("");
   printf("\n");
   puts("Ok-ger18");
   printf("\n");
   puts("The output should be: ");
   printf("\n");
   puts("0 1 2");
   printf("\n");
   _static_A_set((_class_A *) this, 0);
   
   printf("%d ", _static_A_get((_class_A *) this));
   _b = new_B();
   ( (void (*)(_class_B *)) _b->vt[0] ) ((_class_B *) _b);
   
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
