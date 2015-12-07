/* deve-se incluir alguns headers porque algumas fun��es da biblioteca padr�o de C s�o utilizadas na tradu��o. */

#include <malloc.h>
#include <stdlib.h>
#include <stdio.h>

/* define o tipo boolean */
typedef int boolean;
#define true  1
#define false 0

/* define um tipo Func que � um ponteiro para fun��o */
typedef void (*Func)();

typedef struct _St_Program {
       /* ponteiro para um vetor de m�todos da classe */
    Func *vt;
    } _class_Program;
_class_Program *new_Program(void);

void _Program_run( _class_Program *this ){
   int _i;
   int _b;
   
   boolean _primo;
   
   char *_msg;

   puts( "Ola, este e o meu primeiro programa" );
   puts( "Digite um numero: ");
   { 
     char __s[512];
     gets(__s);
     sscanf(__s, "%d", &_b);
   }
   _primo = true;
   _i = 2;
   while ( _i < _b ){
      if ( _b%_i == 0 ) {
         _primo = false;
         break;
      }
	  else{
         _i++;
	  }
   }
   if ( _primo != false ){
      _msg = "Este numero e primo";
   }
   else{
      _msg = "Este numero nao e primo";
   }
   puts(_msg);
}

// apenas os m�todos p�blicos
Func VTclass_Program[] = {
  ( void (*)() ) _Program_run
};

_class_Program *new_Program(){
  _class_Program *t;

  if ( (t = malloc(sizeof(_class_Program))) != NULL )
       /* o texto explica porque vt � inicializado */ 
    t->vt = VTclass_Program;
  return t;
}

   /* genC de Program da ASA deve gerar a fun��o main exatamente como abaixo. */
int main() {
  _class_Program *program;

    /* crie objeto da classe Program e envie a mensagem run para ele */
  program = new_Program();
  ( ( void (*)(_class_Program *) ) program->vt[0] )(program);
  return 0;
}


