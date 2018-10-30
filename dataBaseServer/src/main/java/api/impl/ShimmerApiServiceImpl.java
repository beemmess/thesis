package api.impl;

import api.JNDIPaths;

public class ShimmerApiServiceImpl extends ApiServiceImpl {
    public ShimmerApiServiceImpl(){
        super(JNDIPaths.SHIMMER_RAW_QUEUE,JNDIPaths.DATABASE_CONNECTION_FACTORY);
    }

}
