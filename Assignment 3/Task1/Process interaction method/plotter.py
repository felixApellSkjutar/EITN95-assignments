import numpy as np
import matplotlib.pyplot as plt

nbr_sensors = [1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000]

# Task 1a and task 1b
throughput1 = [0.151, 0.185, 0.169, 0.136, 0.103, 0.075, 0.054, 0.036, 0.025, 0.016]
packet_loss1 = [0.391, 0.628, 0.775, 0.867, 0.919, 0.950, 0.967, 0.982, 0.989, 0.993]
ci_packet_loss1 = [0.000177, 0.000118, 0.000496, 0.000388, 0.000289, 0.000249, 0.000263, 0.000149, 0.000209, 0.000116]

# # Create the plots
# plt.plot(nbr_sensors, throughput1)
# plt.plot(nbr_sensors, packet_loss1)
# plt.xticks(nbr_sensors)

# # Plotting confidence interval for the packet loss
# for i in range(10):
#     top = packet_loss1[i] + ci_packet_loss1[i]
#     bottom = packet_loss1[i] - ci_packet_loss1[i]
#     plt.plot([nbr_sensors[i], nbr_sensors[i]], [top, bottom], color='b')

# # Add labels and title
# plt.xlabel('Number of Sensors')
# plt.ylabel('Probability')
# plt.title('Throughput and packet loss for number of sensors')
# plt.legend(['Throughput', 'Packet loss'])

# plt.show()

# Task 1c
throughput2_10_50 = [0.227, 0.357, 0.415, 0.391, 0.312, 0.240, 0.186, 0.137, 0.099, 0.074]
packet_loss2_10_50 = [0.143, 0.278, 0.447, 0.611, 0.743, 0.818, 0.879, 0.916, 0.942, 0.958]
ci_packet_loss2_10_50 = [0.00366, 0.00207, 0.000477, 0.000481, 0.000680, 0.000788, 0.000804, 0.000981, 0.000681, 0.000548]

throughput2_100_500 = [0.209, 0.350, 0.408, 0.392, 0.352, 0.309, 0.257, 0.211, 0.184, 0.156]
packet_loss2_100_500 = [0.141, 0.275, 0.417, 0.532, 0.637, 0.723, 0.788, 0.832, 0.874, 0.898]
ci_packet_loss2_100_500 = [0.000336, 0.000505, 0.000727, 0.000908, 0.000928, 0.000982, 0.00153, 0.00125, 0.00105, 0.00130]

throughput2_1000_5000 = [0.185, 0.289, 0.341, 0.362, 0.375, 0.370, 0.377, 0.350, 0.327, 0.315]
packet_loss2_1000_5000 = [0.129, 0.220, 0.308, 0.400, 0.454, 0.514, 0.542, 0.606, 0.656, 0.682]
ci_packet_loss2_1000_5000 = [0.000288, 0.000355, 0.000327, 0.000294, 0.000495, 0.000341, 0.000575, 0.000255, 0.000206, 0.000324]

# # Create the plots
# plt.plot(nbr_sensors, throughput2_10_50)
# plt.plot(nbr_sensors, throughput2_100_500)
# plt.plot(nbr_sensors, throughput2_1000_5000)
# plt.xticks(nbr_sensors)

# # Add labels and title for throughput
# plt.xlabel('Number of Sensors')
# plt.ylabel('Transmission rate')
# plt.title('Throughput for number of sensors')
# plt.legend(['T_put for lb=10, ub=50', 'T_put for lb=100, ub=500', 'T_put for lb=1000, ub=5000'])

# plt.show()

# plt.plot(nbr_sensors, packet_loss2_10_50)
# plt.plot(nbr_sensors, packet_loss2_100_500)
# plt.plot(nbr_sensors, packet_loss2_1000_5000)
# plt.xticks(nbr_sensors)

# # Plotting confidence interval for the packet loss
# for i in range(10):
#     top1, bottom1 = packet_loss2_10_50[i] + ci_packet_loss2_10_50[i], packet_loss2_10_50[i] - ci_packet_loss2_10_50[i]
#     plt.plot([nbr_sensors[i], nbr_sensors[i]], [top1, bottom1], color='b')
#     top2, bottom2 = packet_loss2_100_500[i] + ci_packet_loss2_100_500[i], packet_loss2_100_500[i] - ci_packet_loss2_100_500[i]
#     plt.plot([nbr_sensors[i], nbr_sensors[i]], [top2, bottom2], color='b')
#     top3, bottom3 = packet_loss2_1000_5000[i] + ci_packet_loss2_1000_5000[i], packet_loss2_1000_5000[i] - ci_packet_loss2_1000_5000[i]
#     plt.plot([nbr_sensors[i], nbr_sensors[i]], [top3, bottom3], color='b')

# # Add labels and title for packet loss
# plt.xlabel('Number of Sensors')
# plt.ylabel('Probability')
# plt.title('Packet loss for number of sensors')
# plt.legend(['PL for lb=10, ub=50', 'PL for lb=100, ub=500', 'PL for lb=1000, ub=5000'])

# plt.show()

# Task 1d
throughput1d = [0.267, 0.283, 0.309, 0.330, 0.335, 0.334]
radius = [6000, 7000, 8000, 9000, 10000, 11000]

# Create the plots
plt.plot(radius, throughput1d)
plt.xticks(radius)
plt.yticks(throughput1d)

# Add labels and title for throughput
plt.xlabel('Radius')
plt.ylabel('Transmission rate')
plt.title('Throughput for radius')

plt.show()