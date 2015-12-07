/* deve-se incluir alguns headers porque algumas fun��es da biblioteca padr�o de C s�o utilizadas na tradu��o. */

#include <malloc.h>
#include <stdlib.h>
#include <stdio.h>

/* define o tipo boolean */
typedef int boolean;
#define true  1
#define false 0

/* define um tipo Func que � um ponteiro para fun��o*/ 
typedef void (*Func)();

typedef struct _St_A {
   /* ponteiro para um vetor de m�todos da classe */
    Func *vt;
    int _A_i;
} _class_A;
_class_A *new_A(void);

int _A_get( _class_A *this ) {
  return this->_A_i;
}

void _A_put( _class_A *this, int _p_i ) {
  this->_A_i = _p_i;
  }

  /* tabela de m�todos da classe A -- virtual table */
Func VTclass_A[] = {
  ( void (*)() ) _A_get,
  ( void (*)() ) _A_put
};

_class_A *new_A(){
  _class_A *t;
  if ( (t = malloc(sizeof(_class_A))) != NULL )
    t->vt = VTclass_A;
  return t;
  }

typedef struct _St_Program {
   /* ponteiro para um vetor de m�todos da classe */
    Func *vt;
} _class_Program;
_class_Program *new_Program(void);

void _Program_run( _class_Program *this ){
  _class_A *_a;
  
  int _k;

  _a = new_A();
     /* a.put(5); */
  ( (void (*)(_class_A *, int)) _a->vt[1] )(_a, 5);  
     /* k = a.get();  */
  _k = ( (int (*)(_class_A *)) _a->vt[0] )(_a);       
     /* write(k);  */
  printf("%d ", _k );
  }

Func VTclass_Program[] = {
  ( void (*)() ) _Program_run
  };

_class_Program *new_Program()
{
  _class_Program *t;

  if ( (t = malloc(sizeof(_class_Program))) != NULL )
    t->vt = VTclass_Program;
  return t;
}

int main() {
  _class_Program *program;

    /* crie objeto da classe Program e envie a mensagem run para ele */
  program = new_Program();
  ( ( void (*)(_class_Program *) ) program->vt[0] )(program);
  return 0;
}

 
