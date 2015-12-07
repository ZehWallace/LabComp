REM Segundo trabalho. 
REM testa a geração de código 

del ok-*.txt
del er-*.txt
del *.c
del z.txt
del r.txt
del ok-*.exe
del er-*.exe

rem del ..\..\t\ok-*.txt
rem del ..\..\t\er-*.txt
rem del ..\..\t\ok-*.exe
rem del ..\..\t\er-*.exe
rem del ..\..\..\ktests\ok-*.txt
rem del ..\..\..\ktests\er-*.txtcd
rem del ..\..\..\ktests\ok-*.exe
rem del ..\..\..\ktests\er-*.exe
rem del ..\..\..\ktests\ok-*.c




java -cp bin comp.Comp ..\..\..\ktests\OK-GER01.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER02.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER03.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER04.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER05.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER06.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER07.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER08.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER09.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER10.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER11.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER12.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER14.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER15.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER16.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER17.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER18.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER19.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER20.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER21.KRA
java -cp bin comp.Comp ..\..\..\ktests\OK-GER22.KRA


rem move ..\..\t\ok-*.txt .
rem move ..\..\t\er-*.txt .
rem move ..\..\t\ok-*.exe .
rem move ..\..\..\ktests\ok-*.txt .
rem move ..\..\..\ktests\er-*.txt .
rem move ..\..\..\ktests\ok-*.c .
rem move ..\..\..\ktests\ok-*.exe .



set path="C:\Program Files (x86)\CodeBlocks\MinGW\bin";%path%

del z.txt

gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER01.exe OK-GER01.c
OK-GER01  < ..\..\..\soft\t\30-enters.txt > OK-Out01.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER02.exe OK-GER02.c
OK-GER02  < ..\..\..\soft\t\30-enters.txt > OK-Out02.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER03.exe OK-GER03.c
OK-GER03  < ..\..\..\soft\t\30-enters.txt > OK-Out03.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER04.exe OK-GER04.c
OK-GER04  < ..\..\..\soft\t\30-enters.txt > OK-Out04.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER06.exe OK-GER06.c
OK-GER06  < ..\..\..\soft\t\30-enters.txt > OK-Out06.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER07.exe OK-GER07.c
OK-GER07  < ..\..\..\soft\t\30-enters.txt > OK-Out07.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER08.exe OK-GER08.c
OK-GER08  < ..\..\..\soft\t\30-enters.txt > OK-Out08.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER09.exe OK-GER09.c
OK-GER09  < ..\..\..\soft\t\30-enters.txt > OK-Out09.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER10.exe OK-GER10.c
OK-GER10  < ..\..\..\soft\t\30-enters.txt > OK-Out10.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER12.exe OK-GER12.c
OK-GER12  < ..\..\..\soft\t\30-enters.txt > OK-Out12.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER14.exe OK-GER14.c
OK-GER14  < ..\..\..\soft\t\30-enters.txt > OK-Out14.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER15.exe OK-GER15.c
OK-GER15  < ..\..\..\soft\t\30-enters.txt > OK-Out15.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER16.exe OK-GER16.c
OK-GER16  < ..\..\..\soft\t\30-enters.txt > OK-Out16.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER17.exe OK-GER17.c
OK-GER17  < ..\..\..\soft\t\30-enters.txt > OK-Out17.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER18.exe OK-GER18.c
OK-GER18  < ..\..\..\soft\t\30-enters.txt > OK-Out18.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER19.exe OK-GER19.c
OK-GER19  < ..\..\..\soft\t\30-enters.txt > OK-Out19.txt

gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER20.exe OK-GER20.c
OK-GER20  < ..\..\..\soft\t\30-enters.txt > OK-Out20.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER21.exe OK-GER21.c
OK-GER21  < ..\..\..\soft\t\30-enters.txt > OK-Out21.txt
gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER22.exe OK-GER22.c
OK-GER22  < ..\..\..\soft\t\30-enters.txt > OK-Out22.txt


copy OK-Out*.txt z.txt

gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER05.exe OK-GER05.c
OK-GER05  < ..\..\t\sixnum.txt  > OK-Out05.txt

gcc  C:\Program Files (x86)\CodeBlocks\MinGW\include  -o OK-GER11.exe OK-GER11.c
OK-GER11  < ..\..\..\soft\t\30-enters.txt > OK-Out11.txt


type OK-Out05.txt >> z.txt
type OK-Out11.txt >> z.txt


del *.tds
del *.obj
del *.bak

