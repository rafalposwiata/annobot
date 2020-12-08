import numpy as np
import matplotlib
import matplotlib.pyplot as plt
matplotlib.rcParams.update({'font.size': 14})

# # data to plot
# n_groups = 2
# annobot = (63, 43)
# label_studio = (65, 77)
# doccano = (87, 69)
#
# # create plot
# fig, ax = plt.subplots()
# index = np.arange(n_groups)
# bar_width = 0.2
# opacity = 1
#
# rects1 = plt.bar(index, annobot, bar_width, alpha=opacity, color='g', label='Annobot')
# rects2 = plt.bar(index + bar_width, label_studio, bar_width, alpha=opacity, color='b', label='Label Studio')
# rects3 = plt.bar(index + 2 * bar_width, doccano, bar_width, alpha=opacity, color='r', label='Doccano')
#
# plt.xlabel('System version')
# plt.ylabel('Average time per user [sec.]')
# plt.xticks(index + bar_width, ('Desktop', 'Mobile'))
# plt.legend()
#
# plt.tight_layout()
# plt.show()


import pandas as pd

# data
simple = pd.DataFrame({'x': range(10, 110, 10),
                   'y': [0.0, 0.0, 6, 10, 15, 30, 23, 51, 56, 58]})


intelligent = pd.DataFrame({'x': range(10, 110, 10),
                   'y': [0.0, 0.0, 18, 31, 41, 65, 69, 60, 70, 72]})


# plot
plt.plot('x', 'y', data=simple, linestyle='-', color='gray', marker='o', label='w/o active sampling')
plt.plot('x', 'y', data=intelligent, linestyle='-', color='g', marker='o', label='w/ active sampling')
plt.xlabel('Number of Instances')
plt.ylabel('F1-score [%]')
plt.legend()
plt.show()

