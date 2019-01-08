
import os
from glob import glob



def sendRequest(data):
	url ='http://142.93.109.50:9090/ProcessingServer/api/data'
	headers = {'Content-type': 'application/json'}
	# requests.post(url,json=data, headers=headers)
	print(data)


for file in glob("buffer/*.*"):
	json = open(file).read()
	print(file)
	sendRequest(json)
	os.remove(file)
