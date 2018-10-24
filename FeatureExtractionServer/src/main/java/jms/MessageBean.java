//package jms;
//
//import javax.annotation.Resource;
//import javax.ejb.ActivationConfigProperty;
//import javax.ejb.MessageDriven;
////import javax.ejb.M
//import javax.jms.*;
////import java.util.logging.Logger;
//import org.jboss.logging.Logger;
//@MessageDriven(activationConfig = {
//        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/test")
////        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
//})
//public class MessageBean implements MessageListener {
//
//
//    private static final Logger logger = Logger.getLogger(jms.MessageBean.class.getName());
//    private String msgText;
//
////    @Inject
////    public EyeTrackerService eyeTrackerService;
//
//    @Resource(lookup = JNDIPaths.DATABASE_CONNECTION_FACTORY)
//    private ConnectionFactory connectionFactory;
//
////    @Resource(lookup = JNDIPaths.EYETRACKER_QUEUE)
////    private Queue queueEye;
//
//
//    /**
//     * Handling messages from the queue and responding back to the temporary queue with the result
//     *
//     * @param message message received from queue
//     */
//    @Override
//    public void onMessage(Message message){
//        logger.info("onMessage");
//        try {
//            if (message instanceof BytesMessage) {
//                byte[] body = new byte[(int) ((BytesMessage) message).getBodyLength()];
//                ((BytesMessage) message).readBytes(body);
//                msgText = new String(body);
//                String id = msgText.substring(0,msgText.indexOf("|"));
//                String data = msgText.replaceFirst(".*|","");
//                logger.info(id);
//                logger.info(data);
//                if(id.equals("eyetracker")) {
//                    QueueSession session = setUpSession();
//                    Destination destination = session.createQueue(JNDIPaths.EYETRACKER_QUEUE);
//                    MessageProducer producer = session.createProducer(destination);
//                    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//                    TextMessage txtMsg = session.createTextMessage(data);
//                    producer.send(txtMsg);
//                    session.close();
////                    connection.close();
//
//                }
//                else if(id.equals("shimmer")){
//                    QueueSession session = setUpSession();
//                    Destination destination = session.createQueue(JNDIPaths.SHIMMER_QUEUE);
//                    MessageProducer producer = session.createProducer(destination);
//                    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//                    TextMessage txtMsg = session.createTextMessage(data);
//                    producer.send(txtMsg);
//                    session.close();
////                    connection.close();
//                }
//
//
//            }
//            else {
//                logger.warn("Message af wrong type: " + message.getClass().getName());
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
//    private QueueSession setUpSession() throws JMSException {
//        QueueConnection qc = (QueueConnection) connectionFactory.createConnection();
//        return qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//    }
//    /**
//     * Handling the text message received from the queue.
//     *
//     * @param message Text message received from queue.
//     * @return a SuccessResponse to reply back with.
//     */
////    protected abstract void messageReceived(String message);
//
//}
//
