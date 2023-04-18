import numpy as np
import matplotlib.pyplot as plt

number_of_customer, number_of_measurement = [], []

# Opening file and fetching the results
with open("measurement_result.txt", "r") as file:
    line = file.readline()
    while line:
        result = line.strip().split(', ')
        number_of_customer.append(int(result[0]))
        number_of_measurement.append(int(result[1]))
        line = file.readline()

# Create a plot
plt.plot(number_of_measurement, number_of_customer)

# Add labels and title
plt.xlabel('measurement')
plt.ylabel('customers')
plt.title('Number of customers for each measurement')

plt.show()
