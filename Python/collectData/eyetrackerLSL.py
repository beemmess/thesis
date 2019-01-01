from pylsl import StreamInfo, StreamOutlet, resolve_stream, StreamInlet


# This function triggers the LSL into getting the LSL StreamInlet for the eyetracker
def lab_streaming_layer():
	# t = time.time() + seconds

	# first resolve an tobii stream on the lab network
	print("looking for an Tobii stream...")
	streams = resolve_stream('type', 'eyetracker')

	tasks = {0.0:"nan",1.0:"welcome",2.0:"calculate",3.0:"read"}

	# create a new inlet to read from the stream
	inlet = StreamInlet(streams[0])
	# time = datetime.datetime.now().strftime("-%Y-%m-%d-%H%M%S")

	# fileName = "EyeShimmerLSL/gazedataLSL{}.csv".format(time)
	f = ""

	# f +="timestamp,leftX,leftY,rightX,rightY\n"
	n=0
	string =""


	# This will create 10 lines of eyetracking data and then sent to the server,
	while True:
		# print("am I here")
		# get a new sample (you can also omit the timestamp part if you're not
		# interested in it)
		sample, timestamp = inlet.pull_sample(timeout=1.0)
		# print(string)
		if(sample is None):
			break;
		string="{},{},{},{},{},{},{},{}\n".format(timestamp,sample[0],sample[1],sample[2],sample[3],sample[4],sample[5],tasks[sample[6]])
		# print(sample)
		f +=string
		# print(f)
		# print(timestamp, sample)
	jsonString= {"type":"raw", "device":"eyetracker", "apiUrl":"/eyetracker/substitution,/eyetracker/avgPupil,/eyetracker/avgPupil/perTask,/eyetracker/interpolate", "id": "pythonTest", "attributes": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR,task", "data": f}
	# finish = True
	# print(jsonString)
	sendRequest(jsonString)


# Send the request to the server, the server url needs to be defined.
def sendRequest(data):
	url ='http://142.93.109.50:9090/FeatureExtractionServer/api/eyetracker'
	headers = {'Content-type': 'application/json'}
	# requests.post(url,json=data, headers=headers)
	print(data)

lab_streaming_layer()
