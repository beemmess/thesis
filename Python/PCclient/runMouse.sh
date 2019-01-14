#!/bin/bash
python mouseTracker.py ${1:-10} &
python mouseTrackerLSL.py &
wait
python send.py  