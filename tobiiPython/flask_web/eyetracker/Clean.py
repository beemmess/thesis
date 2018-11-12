import pandas as pd
import numpy as np
import json

def substitution(message):
    # Get the data string from the JSON
    data = message["data"]
    # Get the features string and split it up
    feature = message["features"].split(",")
    # Read the data as csv in buffer and put name of columns as features
    df = pd.read_csv(pd.compat.StringIO(data), names=feature)


    # Generate list of lists of features, for substitution purpose
    features = [['leftX','rightX'],['rightX','leftX'],['leftY','rightY'],['rightY','leftY'],['pupilL','pupilR'],['pupilR','pupilL']]

# Gazepoint/pupil substition link: https://arxiv.org/pdf/1703.09468.pdf
# page 5 in the article
    for feature in features:
        df[feature[0]]=df.apply(lambda x : fx(x,feature),axis=1)

    # delete rows after substition that still contains NaN
    df = df.dropna()

    # convert the dataframe into csv string, with no row (index) numbers, and no header
    dataSub=df.to_csv(index=False,header=False)
    # save the type to preprocessed
    message["type"] = "preprocessed"
    # save the preprocessed data into data value in JSON
    message["data"] = dataSub
    return message

# This function checks for NaN values and replaces NaN with corresponding values
# from the other eye value
def fx(x,feature):
    if np.isnan(x[feature[0]]):
        return x[feature[1]]
    else:
        return x[feature[0]]
