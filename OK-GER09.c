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

void _A_m1(_class_A *this, int _n){
   printf("%d ", 1);
   printf("%d ", _n);
}

// apenas os métodos públicos
Func VTclass_A[] = { 
   (void (*) () ) _A_m1
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

void _B_m2(_class_B *this, int _n){
   _A_m1((_class_A *) this, 1);
   
   printf("%d ", 2);
   printf("%d ", _n);
}

// apenas os métodos públicos
Func VTclass_B[] = { 
   (void (*) () ) _A_m1,
   (void (*) () ) _B_m2
};

_class_B *new_B(){
   _class_B *t;
   if((t = malloc(sizeof(_class_B)))!=NULL)
      t->vt = VTclass_B;
   return t;
}

typedef struct _St_C {
   /* ponteiro para um vetor de métodos da classe */
   Func *vt; 
} _class_C;
_class_C *new_C(void);

void _C_m3(_class_C *this, int _n){
   _B_m2((_class_B *) this, 2);
   
   printf("%d ", 3);
   printf("%d ", _n);
}

void _C_m4(_class_C *this, int _n){
   ( (void (*)(_class_C *, int)) this->vt[2] ) (this, 3);
   
   printf("%d ", 4);
   printf("%d ", _n);
}

// apenas os métodos públicos
Func VTclass_C[] = { 
   (void (*) () ) _A_m1,
   (void (*) () ) _B_m2,
   (void (*) () ) _C_m3,
   (void (*) () ) _C_m4
};

_class_C *new_C(){
   _class_C *t;
   if((t = malloc(sizeof(_class_C)))!=NULL)
      t->vt = VTclass_C;
   return t;
}

typedef struct _St_Program {
   /* ponteiro para um vetor de métodos da classe */
   Func *vt; 
} _class_Program;
_class_Program *new_Program(void);

void _Program_run(_class_Program *this){
   _class_C *_c;
   
   puts("");
   printf("\n");
   puts("Ok-ger09");
   printf("\n");
   puts("The output should be :");
   printf("\n");
   puts("1 1 2 2 3 3 4 4");
   printf("\n");
   _c = new_C();
   ( (void (*)(_class_C *, int)) _c->vt[3] ) ((_class_C *) _c, 4);
   
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
