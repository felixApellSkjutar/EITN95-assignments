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

disp('Optimal maximized solution x for task 1:');
disp(x);
disp('Optimal value for task 1:');
disp(-fval);


c = [1500;
    1575;
    420];
A = [-4 -5 -1;
    -5 -3 -2];
b = [-13;
    -11];
lb = [0;
    0;
    0];
%options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'iter');
options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
[y, fval] = linprog(c', A, b, [], [], lb, [], [], options);

disp('Optimal minimized solution x for task 2:');
disp(y);
disp('Optimal value for task 2:');
disp(fval);


dataPoints = [1575, 1590, 1605, 1620, 1635, 1650, 1665, 1680, 1695, 1710, 1725];
objectiveValues = [4335, 4367.142857, 4399.285714, 4431.428571, 4463.571429, 4495.714286, 4527.857143, 4560, 4584.230769, 4608.461538, 4632.692308];
plot(dataPoints, objectiveValues, '-o')

