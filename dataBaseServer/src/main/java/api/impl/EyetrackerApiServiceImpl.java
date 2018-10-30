package api.impl;

import api.JNDIPaths;

public class EyetrackerApiServiceImpl extends ApiServiceImpl {
    public EyetrackerApiServiceImpl(){
        super(JNDIPaths.EYETRACKER_RAW_QUEUE,JNDIPaths.DATABASE_CONNECTION_FACTORY);
    }

}
