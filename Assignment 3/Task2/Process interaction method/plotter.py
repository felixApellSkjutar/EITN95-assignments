import numpy as np
import matplotlib.pyplot as plt

minutes_spent_x, freq_students_y  = [], []

# Opening file and fetching the results
with open("output.txt", "r") as file:
    line = file.readline()
    while line:
        result = line.strip().split(' ')
        minutes_spent_x.append(int(result[0]))
        freq_students_y.append(int(result[1]))
        line = file.readline()



# # Create a plot
plt.plot(minutes_spent_x, freq_students_y)

# # Add labels and title
plt.xlabel('minutes spent')
plt.ylabel('nmbr of students')
plt.title('Frequency of students vs minutes spent')
plt.show()