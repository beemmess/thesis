
# simple program to read a multi channel time series from LSL
from pylsl import StreamInlet, resolve_stream

# first resolve an tobii stream on the lab network
print("looking for an Tobii stream...")
streams = resolve_stream('type', 'eyetracker')

# create a new inlet to read from the stream
inlet = StreamInlet(streams[0])

while True:
    # get a new sample (you can also omit the timestamp part if you're not
    # interested in it)
    sample, timestamp = inlet.pull_sample()
    print(timestamp, sample)
