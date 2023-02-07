The interpreter of Programming language based on uzbek-words

Goals of basic functionality:
1) Variables, constants, primitive values
2) Value assignment, binary operators
2) Conditional operator "if"
3) "while"-loop, "for"-loop
4) standard console (in-out) stream

Code sample:
//*************************************

doimiy PI = 3.14159265;
a = "asd";

har(i = 1; i < 10; i = i + 1) 
bosh
    agar (i % 2 == 1 va i > 5)
    bosh
        konsol.yoz(i);
    oxir
    bo'lmasa
    bosh
        konsol.yoz(i * 2);
    oxir
oxir

b = 1;
i = 1;
j = 1;
toki(b < 100)
bosh
   j = b;
   b = i + b;
   i = j;
oxir
konsol.yoz(b);


obj = {};
obj.a = "asd";
obj.b = 1;

//*************************************