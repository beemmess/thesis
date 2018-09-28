package api;


import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.logging.Logger;

@Singleton
@Startup
public class initialBean {
    private static final Logger logger = Logger.getLogger(initialBean.class.getName());

    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:jboss/exported/jms/queue/test")
    private Queue queue;

    @Schedule(second = "*/10")
    public void onSchedule(){
        logger.info("onSchedule");
        try
            (JMSContext jmsContext = connectionFactory.createContext()){
                jmsContext.createProducer().send(queue,jmsContext.createMessage());
        }
    }


}
