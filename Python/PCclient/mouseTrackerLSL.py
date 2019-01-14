# simple program to read a multi channel time series from LSL
from pylsl import StreamInlet, resolve_stream
import time, datetime
import json
import sys

task = {1.0 : "demo"}
# n_lines = int(sys.argv[1])

# first resolve an Mouse stream on the lab network
print("looking for an mouse stream...")
streams = resolve_stream('type', 'mouse')

# create a new inlet to read from the stream
inlet = StreamInlet(streams[0])

# time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")

f = ""

n = 0
while(True):
    sample, timestamp = inlet.pull_sample(timeout=1)
    if(sample is None):
        break;
    string = "{},{},{},{}\n".format(timestamp,sample[0],sample[1],task[sample[2]])
    f+= string
    n+=1
    # print(timestamp, sample)
# print(f)
jsonString = {"type":"raw", "device":"mouse", "apiUrl":"none,", "id": "test", "attributes": "timestamp,xpos,ypos,task", "data": f}

with open('buffer/mouse.json','w') as jsonMouse:
    json.dump(jsonString, jsonMouse)

