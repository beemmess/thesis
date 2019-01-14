#!/bin/bash
python shimmerCollect.py /dev/tty.Shimmer3-F57C-RNI-SPP ${1:-10} &
python shimmerLSLRecieve.py ${1:-10}
