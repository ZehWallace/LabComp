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

int _A_f(_class_A *this){
   return 0;
}

void _A_m(_class_A *this){
   printf("%d ", ( (int (*)(_class_A *)) this->vt[0] ) (this));
}

// apenas os métodos públicos
Func VTclass_A[] = { 
   (void (*) () ) _A_f,
   (void (*) () ) _A_m
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

int _B_g(_class_B *this){
   return 1;
}

void _B_p(_class_B *this){
   printf("%d ", ( (int (*)(_class_B *)) this->vt[2] ) (this) + ( (int (*)(_class_A *)) this->vt[0] ) ((_class_A *) this));
}

void _B_r(_class_B *this){
   printf("%d ", 2);
}

int _B_f(_class_B *this){
   return 10;
}

// apenas os métodos públicos
Func VTclass_B[] = { 
   (void (*) () ) _B_f,
   (void (*) () ) _A_m,
   (void (*) () ) _B_g,
   (void (*) () ) _B_p,
   (void (*) () ) _B_r
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

int _C_f(_class_C *this){
   return 20;
}

int _C_g(_class_C *this){
   return 101;
}

void _C_r(_class_C *this){
   printf("%d ", 200);
}

// apenas os métodos públicos
Func VTclass_C[] = { 
   (void (*) () ) _C_f,
   (void (*) () ) _A_m,
   (void (*) () ) _C_g,
   (void (*) () ) _B_p,
   (void (*) () ) _C_r
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
} _class_D;
_class_D *new_D(void);

// apenas os métodos públicos
Func VTclass_D[] = { 
   (void (*) () ) _A_f,
   (void (*) () ) _A_m
};

_class_D *new_D(){
   _class_D *t;
   if((t = malloc(sizeof(_class_D)))!=NULL)
      t->vt = VTclass_D;
   return t;
}

typedef struct _St_F {
   /* ponteiro para um vetor de métodos da classe */
   Func *vt; 
} _class_F;
_class_F *new_F(void);

int _F_f(_class_F *this){
   return 3;
}

void _F_m(_class_F *this){
   printf("%d ", ( (int (*)(_class_F *)) this->vt[0] ) (this));
}

// apenas os métodos públicos
Func VTclass_F[] = { 
   (void (*) () ) _F_f,
   (void (*) () ) _F_m
};

_class_F *new_F(){
   _class_F *t;
   if((t = malloc(sizeof(_class_F)))!=NULL)
      t->vt = VTclass_F;
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
   puts("Ok-ger22");
   printf("\n");
   puts("The output should be: ");
   printf("\n");
   puts("0 10 11 2 20 121 200 20 121 200 0 0 3");
   printf("\n");
   _class_A *_a;
   
   _a = new_A();
   ( (void (*)(_class_A *)) _a->vt[1] ) ((_class_A *) _a);
   
   _class_B *_b;
   
   _b = new_B();
   ( (void (*)(_class_A *)) _b->vt[1] ) ((_class_A *) _b);
   
   ( (void (*)(_class_B *)) _b->vt[3] ) ((_class_B *) _b);
   
   ( (void (*)(_class_B *)) _b->vt[4] ) ((_class_B *) _b);
   
   _class_C *_c;
   
   _c = new_C();
   ( (void (*)(_class_A *)) _c->vt[1] ) ((_class_A *) _c);
   
   ( (void (*)(_class_B *)) _c->vt[3] ) ((_class_B *) _c);
   
   ( (void (*)(_class_C *)) _c->vt[4] ) ((_class_C *) _c);
   
   _b = _c;
   ( (void (*)(_class_A *)) _b->vt[1] ) ((_class_A *) _b);
   
   ( (void (*)(_class_B *)) _b->vt[3] ) ((_class_B *) _b);
   
   ( (void (*)(_class_B *)) _b->vt[4] ) ((_class_B *) _b);
   
   _class_D *_d;
   
   _d = new_D();
   ( (void (*)(_class_A *)) _d->vt[1] ) ((_class_A *) _d);
   
   _a = _d;
   ( (void (*)(_class_A *)) _a->vt[1] ) ((_class_A *) _a);
   
   _class_F *_f;
   
   _f = new_F();
   ( (void (*)(_class_F *)) _f->vt[1] ) ((_class_F *) _f);
   
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
