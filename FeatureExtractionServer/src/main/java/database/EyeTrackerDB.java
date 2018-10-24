package database;

import jms.JNDIPaths;
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.ActiveMQConnectionFactory;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EyeTrackerDB {
    private static final Logger logger = Logger.getLogger(database.EyeTrackerDB.class.getName());

//    @Resource(lookup = JNDIPaths.INCOMING_DATA_CONNECTION_FACTORY)
//    private ConnectionFactory connectionFactory;
//    private ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://104.248.35.208:61616");
//    private ConnectionFactory connectionFactory;
//    private String CONNECTION_FACTORY = "java:/DatabaseConnectionFactory";
//    private String DESTINATION = "eyetrackerRawQueue";
//    @Inject
//    @JMSConnectionFactory("jms/remoteCF")
//    private JMSContext context;
//
//    @Resource(lookup = "java:jboss/exported/jms/queue/ExampleQueue")
//    private Queue queue;
//
////    public EyeTrackerDB(){
////
////    }
//
//    public void SaveToDatabase(String rawMsg){
//
//
//    logger.info("WOKRING");
////    JMSProducer messageProducer = context.createProducer();
//    try {
//        context.createProducer().send(queue, rawMsg).setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//    }finally {
//        if(context !=null){
//            logger.info("SOMETHING");
//        }
//    }
////
////
////
//// final Properties env = new Properties();
////            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
////            env.put(Context.URL_PKG_PREFIXES, "org.jnp.interfaces");
////            env.put(Context.PROVIDER_URL, "jnp://104.248.35.208:61613");
////            env.put(Context.SECURITY_PRINCIPAL, "user");
////            env.put(Context.SECURITY_CREDENTIALS,"user");
////            namingContext = new InitialContext(env);
////            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup("java:jboss/exported/jms/RemoteConnectionFactory");
////            logger.info("Found connection factory"+ connectionFactory);
////            Destination destination = (Destination) namingContext.lookup(JNDIPaths.EYETRACKER_RAW_QUEUE);
////            context = connectionFactory.createContext("user","user");
////            context.createProducer().send(destination,rawMsg);
//
////            final Connection conn = connectionFactory.createConnection("user", "user");
////
////            final Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
////
////            final Destination dest = sess.createQueue(JNDIPaths.EYETRACKER_RAW_QUEUE);
////
////            final MessageProducer prod = sess.createProducer(dest);
////
////            final Message msg = sess.createTextMessage("Simples Assim");
////
////            prod.send(msg);
////
////            conn.close();
//
//
////            QueueSession session = setUpSession();
////            Destination destination = session.createQueue(JNDIPaths.EYETRACKER_RAW_QUEUE);
////            MessageProducer producer = session.createProducer(destination);
////            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
////            TextMessage txtMsg = session.createTextMessage(rawMsg);
////            producer.send(txtMsg);
////            session.close();
//
//
//
//    }

//    private QueueSession setUpSession() throws JMSException {
//        QueueConnection qc = (QueueConnection) connectionFactory.createConnection("user","user");
//        return qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//    }
}
