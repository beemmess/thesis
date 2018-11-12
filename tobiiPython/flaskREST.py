# http://docs.python-requests.org/en/master/user/quickstart/

import requests, json
file = open('EyeShimmerLSL/testEye.csv', 'r').read()
#local
localurl ='http://0.0.0.0:5000/api/eyetracker/substitution'
#local pupil
localurl2 ='http://0.0.0.0:5000/api/eyetracker/avgPupil'

# Server1 substitution
subUrl='http://142.93.109.50:5000/api/eyetracker/substitution'

avgPupilUrl='http://142.93.109.50:5000/api/eyetracker/avgPupil'

# print(file)
data = { "userId": "bjarki", "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR", "data": file}
# print(data)

# dataJson= json.dumps(data)
headers = {'Content-type': 'application/json'}
r = requests.post(subUrl,json=data, headers=headers)
print(r.text)
text = json.loads(r.text)
r2 = requests.post(avgPupilUrl,json=text,headers=headers)
print(r2.text)