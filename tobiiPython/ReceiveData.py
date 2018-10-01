
# simple program to read a multi channel time series from LSL
from pylsl import StreamInlet, resolve_stream
import time, datetime

# first resolve an tobii stream on the lab network
print("looking for an Tobii stream...")
streams = resolve_stream('type', 'eyetracker')

# create a new inlet to read from the stream
inlet = StreamInlet(streams[0])

f = open("gazedataLSL/gazedataLSL{}.csv".format(datetime.datetime.utcnow()), "a")
f.write("timestamp,leftX,leftY,rightX,rightY\n")

while True:
    # get a new sample (you can also omit the timestamp part if you're not
    # interested in it)
    sample, timestamp = inlet.pull_sample(timeout=1)
    f.write("{},{},{},{},{}\n".format(timestamp,sample[0],sample[1],sample[2],sample[3]))
    # print(timestamp, sample)



