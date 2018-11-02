from pylsl import StreamInfo, StreamOutlet, resolve_stream, StreamInlet
import time, datetime, re
import tobii_research as tr
import numpy as np
import pandas as pd
import requests



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



def gaze_data_callback(gaze_data):
    
    glX=gaze_data['left_gaze_point_on_display_area'][0]
    glY=gaze_data['left_gaze_point_on_display_area'][1]
    grX=gaze_data['right_gaze_point_on_display_area'][0]
    grY=gaze_data['right_gaze_point_on_display_area'][1]
    mysample = [glX,glY,grX,grY]
    # Print the gaze data
    # print(glX,glY,grX,grY)
    outlet.push_sample(mysample)

    # OPTINAL Write data to file
    f.write("{},{},{},{}\n".format(glX,glY,grX,grY))

def lab_streaming_layer():
	# t = time.time() + seconds

	# first resolve an tobii stream on the lab network
	print("looking for an Tobii stream...")
	streams = resolve_stream('type', 'eyetracker')

	# create a new inlet to read from the stream
	inlet = StreamInlet(streams[0])
	time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")

	fileName = "EyeShimmerLSL/gazedataLSL{}.csv".format(time)
	f = open(fileName, "a")

	f.write("timestamp,leftX,leftY,rightX,rightY\n")
	n=0
	while(n<100):
	    # get a new sample (you can also omit the timestamp part if you're not
	    # interested in it)
	    sample, timestamp = inlet.pull_sample(timeout=1)
	    # print(string)
	    f.write("{},{},{},{},{}\n".format(timestamp,sample[0],sample[1],sample[2],sample[3]))
	    n +=1
	    # print(timestamp, sample)
	return fileName

def preproccess(pathToFile):

	df = pd.read_csv(pathToFile)
	print(df)

	# drop NaN values rows that contain 3 or more NaN values 
	df = df.dropna(thresh=2)

	# number of records in the dataframe
	rowCount = df.shape[0]

	def fx(x,feature):
	    if np.isnan(x[feature[0]]):
	        return x[feature[1]]
	    else:
	        return x[feature[0]]

	features = [['leftX','rightX'],['rightX','leftX'],['leftY','rightY'],['rightY','leftY']]
	for feature in features:
		# print(feature[0])
		df[feature[0]]=df.apply(lambda x : fx(x,feature),axis=1)
	# print(df)
	pathToFile+='processed'
	df.to_csv(pathToFile)
	sendRequest(pathToFile)


def sendRequest(fileName):
	data = open(fileName, 'r').read()
	url ='http://142.93.109.50:9090/FeatureExtractionServer/api/eyetracker'
	data = { "device": "eyetracker", "features": "timestamp,leftX,leftY,rightX,rightY", "data": data}
	# dataJson= json.dumps(data)
	headers = {'Content-type': 'application/json'}
	requests.post(url,json=data, headers=headers)




found_eyetrackers = tr.find_all_eyetrackers()
eyetracker = found_eyetrackers[0]

# Apply a DTU license to the python Tobbi SDK, a license is needed and it needs 
# to be in the same folder
# and the license name should be "licensekey"
apply_license()

# Configure a streaminfo
info = StreamInfo(name='Tobii', type='eyetracker', channel_count=4, channel_format='float32',source_id='myuid34234')
# next make an outlet
outlet = StreamOutlet(info)


# OPTIONAL Create a csv file to write to
time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")
f = open("gazedata/gazedata{}.csv".format(time), "a")
# Initial value set to the csv file, a column header
f.write("leftX,leftY,rightX,rightY\n")



# Subsrice to the eytracker then use the gaze_data_callback function to get the gaza data
eyetracker.subscribe_to(tr.EYETRACKER_GAZE_DATA, gaze_data_callback, as_dictionary=True)


# for iteration in range(1):
	# Invoke the labstreaming layer to pull the sample for X amount of time.
pathToFile = lab_streaming_layer()

# unsubscribe to the eyetracker
eyetracker.unsubscribe_from(tr.EYETRACKER_GAZE_DATA, gaze_data_callback)

preproccess(pathToFile)
