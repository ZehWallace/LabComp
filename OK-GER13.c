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
   int _A_n;
} _class_A;
_class_A *new_A(void);

int _A_get(_class_A *this){
   return this->_A_n;
}

void _A_set(_class_A *this, int _n){
   this->_A_n = _n;
}

void _A_m1(_class_A *this){
   printf("%d ", this->_A_n);
}

// apenas os métodos públicos
Func VTclass_A[] = { 
   (void (*) () ) _A_get,
   (void (*) () ) _A_set,
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
   int _A_n;
} _class_B;
_class_B *new_B(void);

void _B_m2(_class_B *this){
}

// apenas os métodos públicos
Func VTclass_B[] = { 
   (void (*) () ) _A_get,
   (void (*) () ) _A_set,
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
   int _A_n;
} _class_C;
_class_C *new_C(void);

void _C_m1(_class_C *this){
   printf("%d ", 8);
}

void _C_teste(_class_C *this){
   _A_m1((_class_A *) this);
   
}

// apenas os métodos públicos
Func VTclass_C[] = { 
   (void (*) () ) _A_get,
   (void (*) () ) _A_set,
   (void (*) () ) _C_m1,
   (void (*) () ) _B_m2,
   (void (*) () ) _C_teste
};

_class_C *new_C(){
   _class_C *t;
   if((t = malloc(sizeof(_class_C)))!=NULL)
      t->vt = VTclass_C;
   return t;
}

typedef struct _St_D {
   /* ponteiro para um vetor de métodos da classe */
   Func *vt; 
   int _A_n;
} _class_D;
_class_D *new_D(void);

void _D_m1(_class_D *this){
   printf("%d ", 9);
}

// apenas os métodos públicos
Func VTclass_D[] = { 
   (void (*) () ) _A_get,
   (void (*) () ) _A_set,
   (void (*) () ) _D_m1,
   (void (*) () ) _B_m2,
   (void (*) () ) _C_teste
};

_class_D *new_D(){
   _class_D *t;
   if((t = malloc(sizeof(_class_D)))!=NULL)
      t->vt = VTclass_D;
   return t;
}

typedef struct _St_Program {
   /* ponteiro para um vetor de métodos da classe */
   Func *vt; 
} _class_Program;
_class_Program *new_Program(void);

void _Program_run(_class_Program *this){
   _class_D *_d;
   
   puts("");
   printf("\n");
   puts("Ok-ger09");
   printf("\n");
   puts("The output should be :");
   printf("\n");
   puts("0");
   printf("\n");
   _d = new_D();
   ( (void (*)(_class_A *, int)) _d->vt[1] ) ((_class_A *) _d, 0);
   
   ( (void (*)(_class_C *)) _d->vt[4] ) ((_class_C *) _d);
   
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
