import numpy as np
import matplotlib.pyplot as plt

customer_numbers, measurement_numbers = [], []

# Opening file and fetching the results
with open("measurement_result.txt", "r") as file:
    line = file.readline()
    while line:
        result = line.strip().split(', ')
        customer_numbers.append(int(result[0]))
        measurement_numbers.append(int(result[1]))
        line = file.readline()

N = len(customer_numbers)
mean = np.mean(customer_numbers)
var = np.sum((customer_numbers - mean)**2) / N
std = np.sqrt(var)

# Multiply by 1.96 because of 95%
confidence_interval = 1.96 * (std / np.sqrt(N))

print("Confidence interval:", confidence_interval)

# # Create a plot
# plt.plot(measurement_numbers, customer_numbers)

# # Add labels and title
# plt.xlabel('measurement')
# plt.ylabel('customers')
# plt.title('Number of customers for each measurement')

# plt.show()
