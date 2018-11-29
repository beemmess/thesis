package api.impl;

import api.PathConstants;

public class ShimmerApiServiceImpl extends ApiServiceImpl {
    public ShimmerApiServiceImpl(){
        super(PathConstants.SHIMMER_RAW_QUEUE, PathConstants.INCOMING_DATA_CONNECTION_FACTORY);
    }

}
