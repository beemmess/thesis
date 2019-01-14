package beans;


public final class PathConstants {

//    JNDIpaths
    public static final String DATABASE_QUEUE = "java:jboss/exported/jms/queue/database";
    public static final String REPLY_QUEUE = "java:jboss/exported/jms/queue/reply";
    public static final String PROCESSING_SERVER_CONNECTION_FACTORY = "java:/ProcessingConnectionFactory";
    public static final String DATABASE_SERVER_CONNECTION_FACTORY = "java:/DatabaseConnectionFactory";



//    IPs and ports

//    Remote server
    public static final String REMOTE_SERVER_IP ="hbl-wildfly.compute.dtu.dk";
    public static final String REMOTE_SERVER_JMS_PORT ="5445";

//    Local server
    public static final String LOCAL_SERVER_IP ="165.227.146.131";
    public static final String DOCKER_LOCAL_NETWORK ="172.17.0.1";


}
