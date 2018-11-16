# http://docs.python-requests.org/en/master/user/quickstart/

import requests
file = open('EyeShimmerLSL/testEye.csv', 'r').read()
shimmerfile = open('EyeShimmerLSL/testShimmerTasks.csv','r').read()
shimmerurl ='http://142.93.109.50:8080/FeatureExtractionServer/api/shimmer'
eyetrackerurl ='http://142.93.109.50:8080/FeatureExtractionServer/api/eyetracker'
data = { "type":"raw","id": "bjarki", "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR", "data": file}
shimmerData = {"id": "bjarkiFlaskTest", "type":"raw", "features":"timestamp,GSR,PPG,task", "data":shimmerfile}


# dataJson= json.dumps(data)
headers = {'Content-type': 'application/json'}
for n in range(1):
	r1 = requests.post(eyetrackerurl,json=data, headers=headers)
	# r2 = requests.post(shimmerurl,json=shimmerData, headers=headers)
	# print(r.json)
	# print(r.text)
	print(r1.status_code)
	# print(r2.status_code)