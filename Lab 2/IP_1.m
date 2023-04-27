%% 2.1 
clc
clear all

c = [1;
     1;
     1;
     1;
     1;
     1;
     1];
 
A = [1 0 0 1 1 1 1;
    1 1 0 0 1 1 1;
    1 1 1 0 0 1 1;
    1 1 1 1 0 0 1;
    1 1 1 1 1 0 0;
    0 1 1 1 1 1 0;
    0 0 1 1 1 1 1
    ];
 b = [8;
      6;
      5;
      4;
      6;
      7;
      9];
 lb = [0;
       0;
       0;
       0;
       0;
       0;
       0];
 %options = optimoptions('intlinprog', 'Display', 'iter');
 options = optimoptions('intlinprog', 'Display', 'off');
 intcon = [1;
           2;
           3;
           4;
           5;
           6;
           7];
[x, fval, exitflag, output] = intlinprog(c', intcon, -A, -b, [], [], lb, [], options)

%% 2.2
clc
clear all

c = [1;
     1;
     1;
     1;
     1;
     1;
     1];
 
A = [1 0 0 1 1 1 1;
    1 1 0 0 1 1 1;
    1 1 1 0 0 1 1;
    1 1 1 1 0 0 1;
    1 1 1 1 1 0 0;
    0 1 1 1 1 1 0;
    0 0 1 1 1 1 1
    ];
 b = [8;
      6;
      5;
      4;
      6;
      7;
      9];
 lb = [0;
       0;
       0;
       0;
       0;
       0;
       0];
 %options = optimoptions('intlinprog', 'Display', 'iter');
 options = optimoptions('intlinprog', 'Display', 'off');

[x, fval, exitflag, output] = linprog(c', -A, -b, [], [], lb, [], options)
