#!/bin/bash
python tobiiEyeTracker.py ${1:-10}&
wait
python send.py 