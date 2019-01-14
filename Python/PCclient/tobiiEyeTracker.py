from pylsl import StreamInfo, StreamOutlet, resolve_stream, StreamInlet
import time, datetime, re
import tobii_research as tr
import requests
import json
import sys

task = {1.0 : "presentation"}
seconds = int(sys.argv[1])
# print(n_lines)
t_end = time.time() + seconds
print(t_end)

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
    task = 1
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
    f.write("{},{},{},{},{},{}\n".format(glX,glY,grX,grY,pupilL,pupilR))

# This function triggers the LSL into getting the LSL StreamInlet for the eyetracker
def lab_streaming_layer():
	# t = time.time()

	# first resolve an tobii stream on the lab network
	print("looking for an Tobii stream...")
	streams = resolve_stream('type', 'eyetracker')

	# create a new inlet to read from the stream
	inlet = StreamInlet(streams[0])
	# time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")

	f = ""
	n=0

	# This will create 10 lines of eyetracking data and then sent to the server,
	while time.time() < t_end:
	    sample, timestamp = inlet.pull_sample(timeout=1)
	    if 'nan' in str(sample):
		    for m in range(6):
		    	if 'nan' in str(sample[m]):
		    		sample[m]='NaN'
	    string="{},{},{},{},{},{},{},{}\n".format(timestamp,sample[0],sample[1],sample[2],sample[3],sample[4],sample[5],task[sample[6]])
	    n +=1
	    f +=string

	jsonString= {"type":"raw", "device":"eyetracker", "apiUrl":"/eyetracker/substitution,/eyetracker/avgPupil,/eyetracker/avgPupil/perTask,/eyetracker/interpolate", "id": "pythonTest", "attributes": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR,task", "data": f}
	
	# Initial value set to the csv file, a column header
	# file.write(f)
	with open('buffer/eyetracker.json', 'w') as jsonEyetracker:
		json.dump(jsonString, jsonEyetracker)

	# print(jsonString)
	# return jsonString


# This finds the connected tobii eyetracker using the tobii_research library
found_eyetrackers = tr.find_all_eyetrackers()
eyetracker = found_eyetrackers[0]

# Apply a DTU license to the python Tobbi SDK, a license is needed and it needs 
# to be in the same folder
# and the license name should be "licensekey"
apply_license()

# Configure a streaminfo
info = StreamInfo(name='Tobii', type='eyetracker', channel_count=7, channel_format='float32',source_id='myuid34234')
# next make an outlet
outlet = StreamOutlet(info)


# OPTIONAL Create a csv file to write to (gazedata folder needs to be created)
# time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")
# f = open("gazedata/gazedata{}.csv".format(time), "a")
# # Initial value set to the csv file, a column header
# f.write("leftX,leftY,rightX,rightY,pupilL,pupilR\n")



# Subsrice to the eytracker then use the gaze_data_callback function to get the gaza data
eyetracker.subscribe_to(tr.EYETRACKER_GAZE_DATA, gaze_data_callback, as_dictionary=True)


# Invoke the labstreaming layer
json = lab_streaming_layer()

# unsubscribe to the eyetracker
eyetracker.unsubscribe_from(tr.EYETRACKER_GAZE_DATA, gaze_data_callback)

# Send the json string to the server
# sendRequest(json)
