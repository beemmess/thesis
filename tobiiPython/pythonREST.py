# http://docs.python-requests.org/en/master/user/quickstart/

import requests
file = open('EyeShimmerLSL/testEye.csv', 'r').read()
url ='http://142.93.109.50:9090/FeatureExtractionServer/api/eyetracker'
data = { "type":"raw","id": "bjarki", "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR", "data": file}

# dataJson= json.dumps(data)
headers = {'Content-type': 'application/json'}
requests.post(url,json=data, headers=headers)