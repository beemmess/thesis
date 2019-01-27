
# simple program to read a multi channel time series from LSL
from pylsl import StreamInlet, resolve_stream
import time, datetime
import json
import sys

######################################################################
############### JSON string configurations ###########################
dataId = "test" 
dataType ="raw" 
device = "shimmer"
apiUrl = "/shimmer/normalize"
attributes = "timestamp,GSR,PPG,task"
######################################################################
######################################################################


task = {1.0 : "demo"}

# first resolve an shimmer stream on the lab network
print("looking for an shimmer stream...")
streams = resolve_stream('type', 'gsr_ppg')

# create a new inlet to read from the stream
inlet = StreamInlet(streams[0])

f = ""

while(True):
    # get a new sample (you can also omit the timestamp part if you're not
    # interested in it)
    sample, timestamp = inlet.pull_sample(timeout=1)
    if(sample is None):
    	break;
    string = "{},{},{},{}\n".format(timestamp,sample[0],sample[1],task[sample[2]])
    f+= string


# Create the JSON string with all the processing procedurs available in the the Python Web Framework 
jsonString = {"type":dataType, "device":device, "apiUrl":apiUrl, "id": dataId, "attributes": attributes, "data": f}
# Buffer the data to a JSON string
with open('buffer/shimmer.json','w') as jsonShimmer:
	json.dump(jsonString, jsonShimmer)

