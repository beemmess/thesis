from pynput import mouse
from pylsl import StreamInfo, StreamOutlet, resolve_stream, StreamInlet
import json
import sys, time

task = 1.0
seconds = int(sys.argv[1])
t_end = time.time() + seconds

def on_move(x, y):
    mysample = [x,y,task]
    if(time.time() > t_end):
    	return False
    outlet.push_sample(mysample)
    # print('Pointer moved to {0}'.format(
    #     (x, y)))

info = StreamInfo(name='Touchpad', type='mouse', channel_count=3, channel_format='float32',source_id='myuid34234')
outlet = StreamOutlet(info)

with mouse.Listener(on_move=on_move) as listener:
    listener.join()
