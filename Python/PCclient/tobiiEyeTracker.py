from pylsl import StreamInfo, StreamOutlet, resolve_stream, StreamInlet
import time, datetime, re
import tobii_research as tr
import requests
import json
import sys
######################################################################
############### JSON string configurations ###########################
dataId = "test" 
dataType ="raw" 
device = "eyetracker"
apiUrl = "/eyetracker/substitution,/eyetracker/avgPupil,/eyetracker/avgPupil/perTask,/eyetracker/interpolate"
attributes = "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR,task"
######################################################################
######################################################################
task = {1.0:"demo"}

# user input of collection time period
seconds = int(sys.argv[1]) 
t_end = time.time() + seconds

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
    task = 1.0 # dummy task
    glX=gaze_data['left_gaze_point_on_display_area'][0] # in xy coordinates
    glY=gaze_data['left_gaze_point_on_display_area'][1] 
    grX=gaze_data['right_gaze_point_on_display_area'][0]
    grY=gaze_data['right_gaze_point_on_display_area'][1]
    pupilL = gaze_data['left_pupil_diameter'] # in millimeters
    pupilR = gaze_data['right_pupil_diameter'] # in millimeters

    mysample = [glX,glY,grX,grY,pupilL,pupilR,task]

    # Push sample to the LSL outlet
    outlet.push_sample(mysample)


# This function triggers the LSL into getting the LSL StreamInlet for the eyetracker
def lab_streaming_layer():

	# first resolve an tobii stream on the lab network
	print("looking for an Tobii stream...")
	streams = resolve_stream('type', 'eyetracker')

	# create a new inlet to read from the stream
	inlet = StreamInlet(streams[0])

	f = ""

	# Collect measurements until time period is finished
	while time.time() < t_end:
	    sample, timestamp = inlet.pull_sample(timeout=1)
	    if 'nan' in str(sample):
		    for m in range(6):
		    	if 'nan' in str(sample[m]):
		    		sample[m]='NaN'
	    string="{},{},{},{},{},{},{},{}\n".format(timestamp,sample[0],sample[1],sample[2],sample[3],sample[4],sample[5],task[sample[6]])
	    f +=string

	# Create the JSON string with all the processing procedurs available in the 
	jsonString= {"type":dataType, "device":device, "apiUrl": apiUrl, "id": dataId, "attributes": attributes, "data": f}
	
	# Buffer the data in a json file
	with open('buffer/eyetracker.json', 'w') as jsonEyetracker:
		json.dump(jsonString, jsonEyetracker)



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

# Subsrice to the eytracker then use the gaze_data_callback function to get the gaza data
eyetracker.subscribe_to(tr.EYETRACKER_GAZE_DATA, gaze_data_callback, as_dictionary=True)


# Invoke the labstreaming layer
json = lab_streaming_layer()

# unsubscribe to the eyetracker
eyetracker.unsubscribe_from(tr.EYETRACKER_GAZE_DATA, gaze_data_callback)

