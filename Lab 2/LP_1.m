clc
clear all

c = [-13;
    -11];
A = [4 5;
    5 3;
    1 2];
b = [1500;
    1575;
    420];
lb = [0;
    0];
%options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'iter');
options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'off');
[x, fval] = linprog(c', A, b, [], [], lb, [], [], options);

disp('Optimal maximum solution x for task 1:');
disp(x);
disp('Optimal maximum function value for task 1:');
disp(-fval);


c = [-1500;
    -1575;
    -420];
A = [4 5 1;
    5 3 2];
b = [13;
    11];
lb = [0;
    0;
    0];
%options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'iter');
options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
[y, fval] = linprog(c', A, b, [], [], lb, [], [], options);

disp('Optimal minimum solution x for task 2:');
disp(y);
disp('Optimal minimum function value for task 2:');
disp(fval);