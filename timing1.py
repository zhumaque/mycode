"""
File: timing1.py in page 38
Prints the running times fro problem size that double,
using a single loop.
"""
import time 

problem_size = 10000000

print("%12s%16s" % ("Problem Size", "Seconds"))

for count in range(5):
    start = time.time()
    work = 1 
    for work in range(problem_size):
        work += 1
        work -= 1

    elapsed = time.time() - start

    print("%12d%16.3f" % (problem_size, elapsed))
    problem_size *= 2

