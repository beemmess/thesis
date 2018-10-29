package api.impl;

import api.JNDIPaths;

public class EyetrackerApiServiceImpl extends ApiServiceImpl {
    public EyetrackerApiServiceImpl(){
        super(JNDIPaths.EYETRACKER_QUEUE,JNDIPaths.INCOMING_DATA_CONNECTION_FACTORY);
    }

}
