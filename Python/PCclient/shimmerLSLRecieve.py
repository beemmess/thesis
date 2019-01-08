
# simple program to read a multi channel time series from LSL
from pylsl import StreamInlet, resolve_stream
import time, datetime

# first resolve an tobii stream on the lab network
print("looking for an shimmer stream...")
streams = resolve_stream('type', 'gsr_ppg')

# create a new inlet to read from the stream
inlet = StreamInlet(streams[0])

time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")

file = open("buffer/shimmer{}.csv".format(time), "a")
f = ""

# f.write("timestamp,GSR,PPG\n")

while True:
    # get a new sample (you can also omit the timestamp part if you're not
    # interested in it)
    sample, timestamp = inlet.pull_sample(timeout=1)
    print("{},{},{},{}".format(timestamp,sample[0],sample[1]))
    string = "{},{},{},{}\n".format(timestamp,sample[0],sample[1],sample[2])
    # f.write("{},{},{},{}\n".format(timestamp,sample[0],sample[1]))
    f+= string
    # print(timestamp, sample)
jsonString = {"type":"raw", "device":"eyetracker", "apiUrl":"/shimmer/normalize", "id": "pythonTest", "attributes": "timestamp,GSR,PPG,task", "data": f}
file.write(jsonString)

