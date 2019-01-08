
# simple program to read a multi channel time series from LSL
from pylsl import StreamInlet, resolve_stream
import time, datetime
import json
import sys

task = {1.0 : "presentation"}
# n_lines = int(sys.argv[1])

# first resolve an tobii stream on the lab network
print("looking for an shimmer stream...")
streams = resolve_stream('type', 'gsr_ppg')

# create a new inlet to read from the stream
inlet = StreamInlet(streams[0])

time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")

# file = open("buffer/shimmer{}.json".format(time), "a")
f = ""

# f.write("timestamp,GSR,PPG\n")
n = 0
while(True):
    # get a new sample (you can also omit the timestamp part if you're not
    # interested in it)
    sample, timestamp = inlet.pull_sample(timeout=1)
    if(sample is None):
    	break;
    string = "{},{},{},{}\n".format(timestamp,sample[0],sample[1],task[sample[2]])
    f+= string
    n+=1
    # print(timestamp, sample)
# print(f)
jsonString = {"type":"raw", "device":"shimmer", "apiUrl":"/shimmer/normalize", "id": "pythonTest", "attributes": "timestamp,GSR,PPG,task", "data": f}
# file.write(jsonString)
with open('buffer/shimmer.json','w') as jsonShimmer:
	json.dump(jsonString, jsonShimmer)

