package api.impl;

import api.PathConstants;

public class EyetrackerApiServiceImpl extends ApiServiceImpl {
    public EyetrackerApiServiceImpl(){
        super(PathConstants.EYETRACKER_RAW_QUEUE, PathConstants.INCOMING_DATA_CONNECTION_FACTORY);
    }

}
