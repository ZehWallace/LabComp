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
   int _A_k;
} _class_A;
_class_A *new_A(void);

int _A_get_A(_class_A *this){
   return this->_A_k;
}

void _A_init(_class_A *this){
   this->_A_k = 1;
}

// apenas os métodos públicos
Func VTclass_A[] = { 
   (void (*) () ) _A_get_A,
   (void (*) () ) _A_init
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
   int _A_k;
   int _B_k;
} _class_B;
_class_B *new_B(void);

int _B_get_B(_class_B *this){
   return this->_B_k;
}

void _B_init(_class_B *this){
   _A_init((_class_A *) this);
   
   this->_B_k = 2;
}

// apenas os métodos públicos
Func VTclass_B[] = { 
   (void (*) () ) _A_get_A,
   (void (*) () ) _B_init,
   (void (*) () ) _B_get_B
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
   int _A_k;
   int _B_k;
   int _C_k;
} _class_C;
_class_C *new_C(void);

int _C_get_C(_class_C *this){
   return this->_C_k;
}

void _C_init(_class_C *this){
   _B_init((_class_B *) this);
   
   this->_C_k = 3;
}

// apenas os métodos públicos
Func VTclass_C[] = { 
   (void (*) () ) _A_get_A,
   (void (*) () ) _C_init,
   (void (*) () ) _B_get_B,
   (void (*) () ) _C_get_C
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
   int _A_k;
   int _B_k;
   int _C_k;
   int _D_k;
} _class_D;
_class_D *new_D(void);

int _D_get_D(_class_D *this){
   return this->_D_k;
}

void _D_init(_class_D *this){
   _C_init((_class_C *) this);
   
   this->_D_k = 4;
}

// apenas os métodos públicos
Func VTclass_D[] = { 
   (void (*) () ) _A_get_A,
   (void (*) () ) _D_init,
   (void (*) () ) _B_get_B,
   (void (*) () ) _C_get_C,
   (void (*) () ) _D_get_D
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
   _class_A *_a;
   
   _class_B *_b;
   
   _class_C *_c;
   
   _class_D *_d;
   
   puts("");
   printf("\n");
   puts("Ok-ger14");
   printf("\n");
   puts("The output should be :");
   printf("\n");
   puts("4 3 2 1");
   printf("\n");
   _d = new_D();
   ( (void (*)(_class_D *)) _d->vt[1] ) ((_class_D *) _d);
   
   printf("%d ", ( (int (*)(_class_D *)) _d->vt[4] ) ((_class_D *) _d));
   _c = _d;
   printf("%d ", ( (int (*)(_class_C *)) _c->vt[3] ) ((_class_C *) _c));
   _b = _c;
   printf("%d ", ( (int (*)(_class_B *)) _b->vt[2] ) ((_class_B *) _b));
   _a = _b;
   printf("%d ", ( (int (*)(_class_A *)) _a->vt[0] ) ((_class_A *) _a));
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
