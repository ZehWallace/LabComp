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

void _A_set(_class_A *this, int _n){
   this->_A_n = _n;
}

int _A_get(_class_A *this){
   return this->_A_n;
}

// apenas os métodos públicos
Func VTclass_A[] = { 
   (void (*) () ) _A_set,
   (void (*) () ) _A_get
};

_class_A *new_A(){
   _class_A *t;
   if((t = malloc(sizeof(_class_A)))!=NULL)
      t->vt = VTclass_A;
   return t;
}

typedef struct _St_Program {
   /* ponteiro para um vetor de métodos da classe */
   Func *vt; 
   struct _St_A *_Program_a;
} _class_Program;
_class_Program *new_Program(void);

void _Program_run(_class_Program *this){
   puts("");
   printf("\n");
   puts("Ok-ger21");
   printf("\n");
   puts("The output should be :");
   printf("\n");
   puts("0");
   printf("\n");
   puts("0");
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
