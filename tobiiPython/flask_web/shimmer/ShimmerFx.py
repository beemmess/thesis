import pandas as pd
import numpy as np
import json

def convertToDataFrame(message):
    # Get the data string from the JSON
    data = message["data"]
    # Get the features string and split it up into list
    features = message["features"].split(",")
    # Read the data as csv in buffer and put name of columns as features
    df = pd.read_csv(pd.compat.StringIO(data), names=features)

    return df, features

def avgGSRandPPG(message):
    # get the data as a dataframe
    df, features = convertToDataFrame(message)

    # calculate the mean of pupil diameters
    avgGSR = df['GSR'].mean()
    avgPPG = df['PPG'].mean()
    # change the type to "avgPupil"
    message["type"] = "avgGSRandPPG"
    # change features to avgPupilL and avgPupilR
    message["features"] = "avgGSR,avgPPG"
    # save the average data in the "data" value in the JSON
    message["data"] = "{},{}".format(avgGSR,avgPPG)
    return message
