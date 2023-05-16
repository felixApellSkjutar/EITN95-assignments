import numpy as np
import matplotlib.pyplot as plt
import statistics
from math import sqrt

def plot_confidence_interval(x, values, z=1.96, color='#2187bb', horizontal_line_width=0.25):
    mean = statistics.mean(values)
    stdev = statistics.stdev(values)
    confidence_interval = z * stdev / sqrt(len(values))

    left = x - horizontal_line_width / 2
    top = mean - confidence_interval
    right = x + horizontal_line_width / 2
    bottom = mean + confidence_interval
    plt.plot([x, x], [top, bottom], color=color)
    plt.plot([left, right], [top, top], color=color)
    plt.plot([left, right], [bottom, bottom], color=color)
    plt.plot(x, mean, 'o', color='#f44336')

    return mean, confidence_interval

throughput = [0.151, 0.185, 0.169, 0.136, 0.103, 0.075, 0.054, 0.036, 0.025, 0.016]
packet_loss = [0.391, 0.628, 0.775, 0.867, 0.919, 0.950, 0.967, 0.982, 0.989, 0.993]
ci_packet_loss = [0.00177, 0.00118, 0.000496, 0.000388, 0.000289, 0.000249, 0.000263, 0.000149, 0.000209, 0.000116]
nbr_sensors = [1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000]

# Create a plot
plt.plot(nbr_sensors, throughput)
plt.plot(nbr_sensors, packet_loss)
plt.xticks(nbr_sensors)

# Plotting confidence interval for the packet loss
for i in range(10):
    top = packet_loss[i] + ci_packet_loss[i]
    bottom = packet_loss[i] - ci_packet_loss[i]
    plt.plot([nbr_sensors[i], nbr_sensors[i]], [top, bottom], color='b')

# Add labels and title
plt.xlabel('Number of Sensors')
plt.ylabel('Probability')
plt.title('Throughput and packet loss for number of sensors')
plt.legend(['Throughput', 'Packet loss'])

plt.show()