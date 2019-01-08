#!/bin/bash
python StartEyeTracking.py ${1:-10}&
wait
python send.py 