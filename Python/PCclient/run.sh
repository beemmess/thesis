#!/bin/bash
python shimmerLSLRecieve.py ${1:-100} &
python shimmer.py /dev/tty.Shimmer3-F57C-RNI-SPP ${1:-100} &
sleep 2
python StartEyeTracking.py ${1:-100} &
wait
python send.py  
# python helloworld2.py &
# python helloworld3.py