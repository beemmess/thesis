package jms.services;

import org.jboss.logging.Logger;
import sun.rmi.runtime.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class EyeTrackerService {
    private static final Logger logger = Logger.getLogger(jms.services.EyeTrackerService.class.getName());

    private String message;

    public EyeTrackerService(){

    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String msg){
        this.message = msg;
        logger.info("DIIIIIIIIIID THIS WORK   "+msg);
    }
}