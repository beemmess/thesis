package api.impl;

import api.JNDIPaths;

public class ShimmerApiServiceImpl extends ApiServiceImpl {
    public ShimmerApiServiceImpl(){
        super(JNDIPaths.SHIMMER_QUEUE,JNDIPaths.INCOMING_DATA_CONNECTION_FACTORY);
    }

}
