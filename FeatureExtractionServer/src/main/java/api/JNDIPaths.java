package api;


public final class JNDIPaths {

    public static final String EYETRACKER_QUEUE = "java:jboss/exported/jms/queue/eyetracker";
    public static final String EYETRACKER_RAW_QUEUE = "java:jboss/exported/jms/queue/eyetrackerRaw";


    public static final String SHIMMER_RAW_QUEUE = "java:jboss/exported/jms/queue/shimmerRaw";
    public static final String SHIMMER_QUEUE = "java:jboss/exported/jms/queue/shimmer";
    public static final String REPLY_QUEUE = "java:jboss/exported/jms/queue/reply";
    public static final String REST_REPLY_QUEUE = "java:jboss/exported/jms/queue/restreply";



    public static final String INCOMING_DATA_CONNECTION_FACTORY = "java:/IncomingDataConnectionFactory";

    public static final String POOLED_CONNECTION_FACTORY = "java:/jms/remoteCF";


}
