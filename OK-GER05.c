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

void _A_m(_class_A *this){
   int _a;
   
   int _b;
   
   int _c;
   
   int _d;
   
   int _e;
   
   int _f;
   
   {
      char __s[512];
      gets(__s);
      sscanf(__s, "%d", &_a);
   }
   {
      char __s[512];
      gets(__s);
      sscanf(__s, "%d", &_b);
   }
   {
      char __s[512];
      gets(__s);
      sscanf(__s, "%d", &_c);
   }
   {
      char __s[512];
      gets(__s);
      sscanf(__s, "%d", &_d);
   }
   {
      char __s[512];
      gets(__s);
      sscanf(__s, "%d", &_e);
   }
   {
      char __s[512];
      gets(__s);
      sscanf(__s, "%d", &_f);
   }
   printf("%d ", _a);
   printf("%d ", _b);
   printf("%d ", _c);
   printf("%d ", _d);
   printf("%d ", _e);
   printf("%d ", _f);
}

// apenas os métodos públicos
Func VTclass_A[] = { 
   (void (*) () ) _A_m
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
} _class_Program;
_class_Program *new_Program(void);

void _Program_run(_class_Program *this){
   _class_A *_a;
   
   puts("");
   printf("\n");
   puts("Ok-ger05");
   printf("\n");
   puts("The output should be what you give as input.");
   printf("\n");
   puts("Type in six numbers");
   printf("\n");
   _a = new_A();
   ( (void (*)(_class_A *)) _a->vt[0] ) ((_class_A *) _a);
   
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
