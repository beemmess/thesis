package api.impl;

import api.PathConstants;

public class RawDataApiServiceImpl extends ApiServiceImpl {
    public RawDataApiServiceImpl(){
        super(PathConstants.EYETRACKER_RAW_QUEUE, PathConstants.INCOMING_DATA_CONNECTION_FACTORY);
    }

}
