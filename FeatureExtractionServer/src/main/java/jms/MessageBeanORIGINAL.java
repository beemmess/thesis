//package jms;
//
//import org.jboss.logging.Logger;
//
//import javax.annotation.Resource;
//import javax.ejb.ActivationConfigProperty;
//import javax.ejb.MessageDriven;
//import javax.jms.*;
//
////import java.util.logging.Logger;
//
//@MessageDriven(activationConfig = {
//        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/test"),
//        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
//})
//public abstract class MessageBeanORIGINAL implements MessageListener {
//
//    @Resource(lookup = JNDIPaths.DTU_PAY_CONNECTION_FACTORY)
//    private ConnectionFactory connectionFactory;
//
//    @Resource(lookup = JNDIPaths.EYETRACKER_QUEUE)
//    private static Queue queue;
//
//    @Resource(lookup = JNDIPaths.EYETRACKER_QUEUE)
//    private static Destination destination;
//
//
//    private static final Logger logger = Logger.getLogger(MessageBeanORIGINAL.class.getName());
//    private String msgText;
//
////    @Inject
////    public EyeTrackerService eyeTrackerService;
//
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
//        TextMessage textMsg;
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
////                    Destination destination = new Des
//                    QueueSession session = setUpSession();
////                    Queue queue = session.createQueue(JNDIPaths.EYETRACKER_QUEUE);
////                    Queue queue = session.create
////                    Destination destination = session.create(JNDIPaths.EYETRACKER_QUEUE);
//                    MessageProducer producer = session.createProducer(destination);
//                    message = session.createTextMessage("is this working");
//                    producer.send(message);
////                    logger.info(data);
////                    eyeTrackerService.setMessage(data);
//
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
//    protected abstract void messageReceived(String message);
//
//}
//
