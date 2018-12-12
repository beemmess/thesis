package api.impl;

import api.PathConstants;

public class RawDataApiServiceImpl extends ApiServiceImpl {
    public RawDataApiServiceImpl(){
        super(PathConstants.INCOMING_DATA_QUEUE, PathConstants.PROCESSING_SERVER_CONNECTION_FACTORY);
    }

}
