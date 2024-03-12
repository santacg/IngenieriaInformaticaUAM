from os import wait
import matplotlib.pyplot as plt
import numpy as np
import subprocess as proc
import timeit
import time
import psutil as psu
import statistics as st
import pdb

client = ["./client", "127.0.0.1", "nchildren",  "bible.txt"]
server = ["./server"]

setup = "server_process = proc.Popen(server)"
stmt = """
        for _ in range(10):
            client_process = proc.run(client)
       """

def launch_clients(i):
    proc_client = ["./client", "127.0.0.1", str(i), "bible.txt"]
    client_process = proc.Popen(proc_client)
    client_process.wait()

def measure_time(n):
    SETUP_CODE = """
from __main__ import launch_clients
    """
    timing = timeit.timeit(f'launch_clients({n})', setup=SETUP_CODE, number=1)
    return timing

server_process = proc.Popen(server)
time.sleep(5)
timings_list = []
for i in range(1, 201):
    time = measure_time(i)
    timings_list.append([i, time])
proc.run(["pkill", "-f", "./server"])
a_timings = np.array(timings_list) 
print(f'\n{a_timings}\n')

plt.plot(a_timings[:,0], a_timings[:,1], "r-")
plt.xlabel("Number of opened clients")
plt.ylabel("Time consumed (s)")
plt.title("Time consumed by server with log 10 for n clients opened")
plt.grid(True)
plt.show()
