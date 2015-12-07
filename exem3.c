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
   int _A_i;
} _class_A;
_class_A *new_A(void);

int _A_get(_class_A *this){
   return this->_A_i;
}

void _A_put(_class_A *this, int _p_i){
   this->_A_i = _p_i;
}

// apenas os métodos públicos
Func VTclass_A[] = { 
   (void (*) () ) _A_get,
   (void (*) () ) _A_put
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
   int _A_i;
   int _B_lastInc;
} _class_B;
_class_B *new_B(void);

void _B_add(_class_B *this, int _n){
   this->_B_lastInc = _n;
   _A_put((_class_A *) this, _A_get((_class_A *) this) + _n);
   
}

void _B_print(_class_B *this){
   printf("%d ", ( (int (*)(_class_A *)) this->vt[0] ) ((_class_A *) this));
}

void _B_put(_class_B *this, int _p_i){
   if (_p_i > 0){
      _A_put((_class_A *) this, _p_i);
   }
   
}

void _B_inc(_class_B *this){
   _B_add((_class_B *) this, 1);
   
}

int _B_getLastInc(_class_B *this){
   return this->_B_lastInc;
}

// apenas os métodos públicos
Func VTclass_B[] = { 
   (void (*) () ) _A_get,
   (void (*) () ) _B_put,
   (void (*) () ) _B_print,
   (void (*) () ) _B_inc,
   (void (*) () ) _B_getLastInc
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
   _class_A *_a;
   
   int _k;
   
   _class_B *_b;
   
   _a = new_A();
   ( (void (*)(_class_A *, int)) _a->vt[1] ) ((_class_A *) _a, 5);
   
   _k = ( (int (*)(_class_A *)) _a->vt[0] ) ((_class_A *) _a);
   printf("%d ", _k);
   _b = new_B();
   ( (void (*)(_class_B *, int)) _b->vt[1] ) ((_class_B *) _b, 2);
   
   ( (void (*)(_class_B *)) _b->vt[3] ) ((_class_B *) _b);
   
   ( (void (*)(_class_B *)) _b->vt[2] ) ((_class_B *) _b);
   
   printf("%d ", ( (int (*)(_class_B *)) _b->vt[4] ) ((_class_B *) _b));
   printf("%d ", ( (int (*)(_class_A *)) _b->vt[0] ) ((_class_A *) _b));
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
