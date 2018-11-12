# http://docs.python-requests.org/en/master/user/quickstart/

import requests
file = open('EyeShimmerLSL/testEye.csv', 'r').read()
shimmerfile = open('EyeShimmerLSL/testShimmer.csv','r').read()
shimmerurl ='http://142.93.109.50:9090/FeatureExtractionServer/api/shimmer'
eyetrackerurl ='http://142.93.109.50:9090/FeatureExtractionServer/api/eytracker'
data = { "type":"raw","id": "bjarki", "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR", "data": file}
shimmerData = {"id": "bjarkiFlaskTest", "type":"raw", "features":"timestamp,GSR,PPG", "data":shimmerfile}


# dataJson= json.dumps(data)
headers = {'Content-type': 'application/json'}
requests.post(shimmerurl,json=shimmerData, headers=headers)