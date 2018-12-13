package api;


public final class PathConstants {

//    JNDI values

    public static final String INCOMING_DATA_QUEUE = "java:jboss/exported/jms/queue/incomingData";
    public static final String DATABASE_QUEUE = "java:jboss/exported/jms/queue/database";

    public static final String REPLY_QUEUE = "java:jboss/exported/jms/queue/reply";
    public static final String REST_REPLY_QUEUE = "java:jboss/exported/jms/queue/restReply";

    public static final String PROCESSING_SERVER_CONNECTION_FACTORY = "java:/ProcessingConnectionFactory";
    public static final String DATABASE_SERVER_CONNECTION_FACTORY = "java:/DatabaseConnectionFactory";



//    IPs and ports

//    Remote server
    public static final String REMOTE_SERVER_IP ="104.248.27.213";
    public static final String REMOTE_SERVER_JMS_PORT ="5445";

//    Local Server
    public static final String LOCAL_SERVER_IP ="207.154.211.58";
    public static final String DOCKER_LOCAL_NETWORK ="172.17.0.1";
    public static final String PYTHON_WEB_CLIENT_PORT= "5000";

}
