package api.impl;

import api.PathConstants;

/**
 * An extension to the ApiServiceImpl that initilise with the given queues and connection factory
 */
public class RawDataApiServiceImpl extends ApiServiceImpl {
    public RawDataApiServiceImpl(){
        super(PathConstants.INCOMING_DATA_QUEUE, PathConstants.PROCESSING_SERVER_CONNECTION_FACTORY);
    }

}
