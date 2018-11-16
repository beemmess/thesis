# http://docs.python-requests.org/en/master/user/quickstart/

import requests, json
eyetrackerfile = open('EyeShimmerLSL/testEye.csv', 'r').read()
shimmerfile = open('EyeShimmerLSL/testShimmerTasks.csv','r').read()
#local Eyetracker urls
lsubstitution ='http://0.0.0.0:5000/api/eyetracker/substitution'
lfillnan ='http://142.93.109.50:5000/api/eyetracker/fillnan'
linterpolate ='http://0.0.0.0:5000/api/eyetracker/interpolate'
lavgpupil ='http://0.0.0.0:5000/api/eyetracker/avgPupil'

# Local shimmer urls
lnormalize = 'http://0.0.0.0:5000/api/shimmer/normalize'

EyetrackerurlList = [lsubstitution,lfillnan,linterpolate,lavgpupil]




# Data
eyetrackerData = { "id": "bjarki","type":"raw", "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR", "data": eyetrackerfile}

shimmerData = {"id": "bjarkiFlaskTest", "type":"raw", "features":"timestamp,GSR,PPG,task", "data":shimmerfile}

headers = {'Content-type': 'application/json'}

for url in EyetrackerurlList:
	r = requests.post(url,json=eyetrackerData, headers=headers)
	print(r.text)
	print(r.status_code)


r2 = requests.post(lnormalize,json=shimmerData,headers=headers)
print(r2.text)
print(r2.status_code)








# # Server1 
# subUrl='http://142.93.109.50:5000/api/eyetracker/substitution'
# avgPupilUrl='http://142.93.109.50:5000/api/eyetracker/avgPupil'