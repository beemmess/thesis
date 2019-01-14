#!/bin/bash
python shimmerLSLRecieve.py ${1:-10} &
python shimmerCollect.py /dev/tty.Shimmer3-F57C-RNI-SPP ${1:-10} &
sleep 2
python tobiiEyeTracker.py ${1:-10} &
wait
python send.py  