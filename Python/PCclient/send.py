
import os, shutil
from glob import glob
import time, datetime
import sys, requests, json


time = str(datetime.datetime.now().strftime("%Y-%m-%d-%H%M%S"))
print(time)

backup = False

def sendRequest(data):
	url ='http://139.59.128.154:8080/ProcessingServer/api/data'
	headers = {'Content-type': 'application/json'}
	r = requests.post(url,json=data, headers=headers)
	jsonReply = json.loads(r.text)
	print(data["device"])
	print(jsonReply["message"])


for file in glob("buffer/*.*"):
	with open(file) as json_data:
		jsonString = json.load(json_data)
		# print(file)
		sendRequest(jsonString)
		
		if(backup):
			# backup for testing purpose
			backup = "buffer/backup/{}".format(time)
			os.mkdir(backup)
			shutil.move(file, backup)
		else:
			os.remove(file)

