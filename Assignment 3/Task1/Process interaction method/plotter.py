import numpy as np
import matplotlib.pyplot as plt

throughput = [0.151, 0.185, 0.169, 0.136, 0.103, 0.075, 0.054, 0.036, 0.025, 0.016]
packet_loss = [0.391, 0.628, 0.775, 0.867, 0.919, 0.950, 0.967, 0.982, 0.989, 0.993]
nbr_sensors = [1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000]

# Create a plot
plt.plot(nbr_sensors, throughput)
plt.xticks(nbr_sensors)
# plt.yticks(throughput)

# Add labels and title
plt.xlabel('Number of Sensors')
plt.ylabel('Throughput')
plt.title('Throughput for number of sensors')

plt.show()