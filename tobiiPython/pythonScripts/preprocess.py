import numpy as np
import pandas as pd
# Eyetracker data
# timestamp, leftX, leftY, rightX, rightY

# Read in a file
df = pd.read_csv("testEyeNanBig.csv")

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

l =''
for line in df:
	# l.append(line)
	print(line)




