from pylsl import StreamInfo, StreamOutlet, resolve_stream, StreamInlet
import time, datetime, re
import tobii_research as tr
import numpy as np
import pandas as pd
import requests
import sys
from threading import Thread



# tobii_research documentation for python
# http://devtobiipro.azurewebsites.net/tobii.research/python/reference/1.5.0.28-alpha-g427ed891/index.html

# apply license function
# author: Tobii
# http://devtobiipro.azurewebsites.net/tobii.research/python/reference/1.1.0.23-beta-g9262468f/classtobii__research_1_1EyeTracker.html 
def apply_license():
	print("Applying license from {0}.".format("dtu"))
	with open("licensekey", "rb") as f:
		license = f.read()
		
		failed_licenses_applied_as_list_of_keys = eyetracker.apply_licenses([tr.LicenseKey(license)])
		failed_licenses_applied_as_list_of_bytes = eyetracker.apply_licenses([license])
		failed_licenses_applied_as_key = eyetracker.apply_licenses(tr.LicenseKey(license))
		failed_licenses_applied_as_bytes = eyetracker.apply_licenses(license)
		
		if len(failed_licenses_applied_as_list_of_keys) == 0:
			print("Successfully applied license from list of keys.")
		else:
			print("Failed to apply license from list of keys. Validation result: {0}.".
			format(failed_licenses_applied_as_list_of_keys[0].validation_result))
			
			if len(failed_licenses_applied_as_list_of_bytes) == 0:
				print("Successfully applied license from list of bytes.")
			else:
				print("Failed to apply license from list of bytes. Validation result: {0}.".
				format(failed_licenses_applied_as_list_of_bytes[0].validation_result))
				
				if len(failed_licenses_applied_as_key) == 0:
					print("Successfully applied license from single key.")
				else:
					print("Failed to apply license from single key. Validation result: {0}.".
					format(failed_licenses_applied_as_key[0].validation_result))
					
					if len(failed_licenses_applied_as_bytes) == 0:
						print("Successfully applied license from bytes object.")
					else:
						print("Failed to apply license from bytes object. Validation result: {0}.".
						format(failed_licenses_applied_as_bytes[0].validation_result))



# Gaze data callback function that maps the data: gl = gaze left, gr = gaze right
# This function also pushes the data into a LSL stream
# The optional part is to save the data also into a csv file so that there is a copy of non LSL file and a LSL file
def gaze_data_callback(gaze_data):

    
    glX=gaze_data['left_gaze_point_on_display_area'][0] # in xy coordinates
    glY=gaze_data['left_gaze_point_on_display_area'][1] 
    grX=gaze_data['right_gaze_point_on_display_area'][0]
    grY=gaze_data['right_gaze_point_on_display_area'][1]
    pupilL = gaze_data['left_pupil_diameter'] # in millimeters
    pupilR = gaze_data['right_pupil_diameter'] # in millimeters
    mysample = [glX,glY,grX,grY,pupilL,pupilR,task]
    # Print the gaze data
    # print(glX,glY,grX,grY)
    outlet.push_sample(mysample)

    # OPTINAL Write data to file
    # f.write("{},{},{},{},{},{},{},{}\n".format(glX,glY,grX,grY,pupilL,pupilR,task))

# This function triggers the LSL into getting the LSL StreamInlet for the eyetracker
def lab_streaming_layer():
	# t = time.time() + seconds

	# first resolve an tobii stream on the lab network
	print("looking for an Tobii stream...")
	streams = resolve_stream('type', 'eyetracker')

	# create a new inlet to read from the stream
	inlet = StreamInlet(streams[0])
	# time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")

	# fileName = "EyeShimmerLSL/gazedataLSL{}.csv".format(time)
	f = ""

	# f +="timestamp,leftX,leftY,rightX,rightY\n"
	n=0


	# This will create 10 lines of eyetracking data and then sent to the server,
	while(n<10):
		print("am I here")
		# get a new sample (you can also omit the timestamp part if you're not
		# interested in it)
		sample, timestamp=inlet.pull_sample(timeout=1)
		# print(string)
		string="{},{},{},{},{},{},{},{}\n".format(timestamp,sample[0],sample[1],sample[2],sample[3],sample[4],sample[5],tasks[sample[6]])
		n +=1
		f +=string
		# print(timestamp, sample)
	global jsonString    
	jsonString= { "userId": "pythonTest", "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR,task", "data": f}
	# finish = True
	# print(jsonString)


# Send the request to the server, the server url needs to be defined.
def sendRequest(data):
	url ='http://142.93.109.50:9090/FeatureExtractionServer/api/eyetracker'
	headers = {'Content-type': 'application/json'}
	requests.post(url,json=data, headers=headers)


def doTasks():
	eyetracker.subscribe_to(tr.EYETRACKER_GAZE_DATA, gaze_data_callback, as_dictionary=True)
	t2 = Thread(target=lab_streaming_layer())
	t2.start()
	t2.join()
	global task
	task = 1
	print("welcome, this is application tracks your eyes, gazedata and pupil diameter. Please do the followoing simple tasks\n")
	time.sleep(1)
	print("please calculate simple thing\n")
	task = 2
	n = input("What is the square root of 225: \n")
	if(n==15):
		print("thats correct, fantastic\n")
	if(n!=15):
		n = input("incorrect, please try one more time, what is the square root of 225: \n")
		if(n==15):
			print("Thats correct, good job\n")
		else:
			print("inccorect, it is 15, lets move on\n")
	task = 3
	print("Please read the following text and find the words 'I' in the sentence and write how many times it appears\n")
	I = input("Simple. I got very bored and depressed, so I went and plugged myself in to its external computer feed. I talked to the computer at great length and explained my view of the Universe to it, said Marvin.And what happened? pressed Ford. It committed suicide,said Marvin and stalked off back to the Heart of Gold.\n")
	if(I==3):
		print("thats correct, fantastic\n")
	if(I!=3):
		n = input("incorrect, please try one more time: \n")
		if(n==3):
			print("Thats correct, good job\n")
		else:
			print("inccorect, it is 3 times, lets move on\n")
	print("Now the experiment is finished, thank you, and have a good day\n")
	time.sleep(3)
	finish = True

def initiateEytracking():
	# This finds the connected tobii eyetracker using the tobii_research library
	found_eyetrackers = tr.find_all_eyetrackers()
	eyetracker = found_eyetrackers[0]

	# Apply a DTU license to the python Tobbi SDK, a license is needed and it needs 
	# to be in the same folder
	# and the license name should be "licensekey"
	apply_license()

	# Configure a streaminfo
	info = StreamInfo(name='Tobii', type='eyetracker', channel_count=7,source_id='myuid34234')
	# next make an outlet
	outlet = StreamOutlet(info)

	eyetracker.subscribe_to(tr.EYETRACKER_GAZE_DATA, gaze_data_callback, as_dictionary=True)
	lab_streaming_layer()




if __name__ == "__main__":
	# global jsonString
	# jsonString = ""
	global task
	task = 1
	global finish
	finish = False
	tasks = {1.0:"welcome",2.0:"calculate",3.0:"read"}




	# OPTIONAL Create a csv file to write to (gazedata folder needs to be created)
	# time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")
	# f = open("gazedata/gazedata{}.csv".format(time), "a")
	# Initial value set to the csv file, a column header
	# f.write("leftX,leftY,rightX,rightY,pupilL,pupilR\n")


	# tasks = {1:"welcome",2:"calculate",3:"read"}
	# Subsribe to the eytracker then use the gaze_data_callback function to get the gaza data
	# 

	t1 = Thread(target=initiateEytracking())
    t2 = Thread(target=doTasks())
    # t3 = Thread(target=lab_streaming_layer())

    t1.start()
    t2.start()
    t3.start()

	# t1 = Thread(target=)
	# t3 = Thread(target=doTasks())
	# t2 = Thread(target=lab_streaming_layer())


	# # t1.setDaemon(True)
	# t3.setDaemon(True)
	# t2.setDaemon(True)

	# # t1.start()
	# t3.start()
	# t2.start()




	# lab_streaming_layer()




	# unsubscribe to the eyetracker

	if(finish):
		eyetracker.unsubscribe_from(tr.EYETRACKER_GAZE_DATA, gaze_data_callback)
	print(jsonString)
	# Send the json string to the server
	# sendRequest(json)
