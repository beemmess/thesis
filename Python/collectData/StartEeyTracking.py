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


def doTasks():
	# eyetracker.subscribe_to(tr.EYETRACKER_GAZE_DATA, gaze_data_callback, as_dictionary=True)
	# t2 = Thread(target=lab_streaming_layer())
	# t2.start()
	# t2.join()
	global task
	task = 1.0
	print("welcome, this is application tracks your eyes, gazedata and pupil diameter. Please do the followoing simple tasks\n")
	time.sleep(3)
	print("please calculate simple thing\n")
	task = 2.0
	n = input("What is the square root of 225: \n")
	if(n=="15"):
		print("thats correct, fantastic\n")
	if(n!="15"):
		n = input("incorrect, please try one more time, what is the square root of 225: \n")
		if(n=="15"):
			print("Thats correct, good job\n")
		else:
			print("inccorect, it is 15, lets move on\n")
	task = 3.0
	print("Please read the following text and find the words 'I' in the sentence and write how many times it appears\n")
	I = input("Simple. I got very bored and depressed, so I went and plugged myself in to its external computer feed. I talked to the computer at great length and explained my view of the Universe to it, said Marvin.And what happened? pressed Ford. It committed suicide,said Marvin and stalked off back to the Heart of Gold.\n")
	if(I=="3"):
		print("thats correct, fantastic\n")
	if(I!="3"):
		n = input("incorrect, please try one more time: \n")
		if(n=="3"):
			print("Thats correct, good job\n")
		else:
			print("inccorect, it is 3 times, lets move on\n")
	print("Now the experiment is finished, thank you, and have a good day\n")
	time.sleep(3)
	finish = True

def initiateEytracking():
	# This finds the connected tobii eyetracker using the tobii_research library
	found_eyetrackers = tr.find_all_eyetrackers()
	global eyetracker
	eyetracker = found_eyetrackers[0]

	# Apply a DTU license to the python Tobbi SDK, a license is needed and it needs 
	# to be in the same folder
	# and the license name should be "licensekey"
	apply_license()

	# Configure a streaminfo
	info = StreamInfo(name='Tobii', type='eyetracker', channel_count=7,source_id='myuid34234')
	# next make an outlet
	global outlet
	outlet = StreamOutlet(info)

	eyetracker.subscribe_to(tr.EYETRACKER_GAZE_DATA, gaze_data_callback, as_dictionary=True)
	# lab_streaming_layer()
	




if __name__ == "__main__":
	# global jsonString
	# jsonString = ""
	global task
	task = 1
	global finish
	finish = False





	# OPTIONAL Create a csv file to write to (gazedata folder needs to be created)
	# time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")
	# f = open("gazedata/gazedata{}.csv".format(time), "a")
	# Initial value set to the csv file, a column header
	# f.write("leftX,leftY,rightX,rightY,pupilL,pupilR\n")


	# tasks = {1:"welcome",2:"calculate",3:"read"}
	# Subsribe to the eytracker then use the gaze_data_callback function to get the gaza data
	# 
	# Thread(target=doTasks()).start()
	# Thread(target=initiateEytracking()).start()

	initiateEytracking()
	doTasks()
	eyetracker.unsubscribe_from(tr.EYETRACKER_GAZE_DATA, gaze_data_callback)


	# t3 = Thread(target=lab_streaming_layer())

	
	# t2.start()
	# t1.start()


	# t3.start()

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


	# print(jsonString)
	# Send the json string to the server
	# sendRequest(json)
