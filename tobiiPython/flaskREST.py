# http://docs.python-requests.org/en/master/user/quickstart/

import requests, json
eyetrackerfile = open('EyeShimmerLSL/testEye.csv', 'r').read()
shimmerfile = open('EyeShimmerLSL/testShimmer.csv','r').read()
#local
localurl ='http://0.0.0.0:5000/api/eyetracker/substitution'
#local pupil
localurl2 ='http://0.0.0.0:5000/api/eyetracker/avgPupil'


# Server1 
subUrl='http://142.93.109.50:5000/api/eyetracker/substitution'
avgPupilUrl='http://142.93.109.50:5000/api/eyetracker/avgPupil'



# local Shimmer
localShimmerUrl = 'http://0.0.0.0:5000/api/shimmer/avg'


# Data
eyetrackerData = { "id": "bjarki","type":"raw", "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR", "data": eyetrackerfile}

shimmerData = {"id": "bjarkiFlaskTest", "type":"raw", "features":"timestamp,GSR,PPG", "data":shimmerfile}

# dataJson= json.dumps(data)
headers = {'Content-type': 'application/json'}
# r = requests.post(localurl,json=eyetrackerData, headers=headers)
# print(r.text)
# text = json.loads(r.text)
r2 = requests.post(localShimmerUrl,json=shimmerData,headers=headers)
print(r2.text)