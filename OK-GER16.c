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

void _A_set(_class_A *this, int _k){
   this->_A_k = _k;
}

void _A_print(_class_A *this){
   printf("%d ", ( (int (*)(_class_A *)) this->vt[0] ) (this));
}

void _A_init(_class_A *this){
   ( (void (*)(_class_A *, int)) this->vt[1] ) (this, 0);
   
}

// apenas os métodos públicos
Func VTclass_A[] = { 
   (void (*) () ) _A_get_A,
   (void (*) () ) _A_set,
   (void (*) () ) _A_print,
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

void _B_print(_class_B *this){
   printf("%d ", ( (int (*)(_class_B *)) this->vt[4] ) (this));
   printf("%d ", ( (int (*)(_class_A *)) this->vt[0] ) ((_class_A *) this));
   _A_print((_class_A *) this);
   
}

// apenas os métodos públicos
Func VTclass_B[] = { 
   (void (*) () ) _A_get_A,
   (void (*) () ) _A_set,
   (void (*) () ) _B_print,
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
} _class_C;
_class_C *new_C(void);

int _C_get_A(_class_C *this){
   return 0;
}

// apenas os métodos públicos
Func VTclass_C[] = { 
   (void (*) () ) _C_get_A,
   (void (*) () ) _A_set,
   (void (*) () ) _A_print,
   (void (*) () ) _A_init
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
   _class_A *_a;
   
   _class_B *_b;
   
   _class_C *_c;
   
   puts("");
   printf("\n");
   puts("Ok-ger16");
   printf("\n");
   puts("The output should be: ");
   printf("\n");
   puts("2 2 0 0 2 0 0 0 0 0 0");
   printf("\n");
   _b = new_B();
   ( (void (*)(_class_B *)) _b->vt[3] ) ((_class_B *) _b);
   
   _c = new_C();
   ( (void (*)(_class_A *)) _c->vt[3] ) ((_class_A *) _c);
   
   printf("%d ", ( (int (*)(_class_B *)) _b->vt[4] ) ((_class_B *) _b));
   _a = _b;
   ( (void (*)(_class_A *)) _a->vt[2] ) ((_class_A *) _a);
   
   ( (void (*)(_class_B *)) _b->vt[2] ) ((_class_B *) _b);
   
   ( (void (*)(_class_A *)) _a->vt[3] ) ((_class_A *) _a);
   
   ( (void (*)(_class_B *)) _b->vt[3] ) ((_class_B *) _b);
   
   printf("%d ", ( (int (*)(_class_A *)) _a->vt[0] ) ((_class_A *) _a));
   printf("%d ", ( (int (*)(_class_A *)) _b->vt[0] ) ((_class_A *) _b));
   _a = _c;
   printf("%d ", ( (int (*)(_class_A *)) _a->vt[0] ) ((_class_A *) _a));
   _c = new_C();
   printf("%d ", ( (int (*)(_class_C *)) _c->vt[0] ) ((_class_C *) _c));
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
