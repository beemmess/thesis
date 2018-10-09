package api;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Counter {

    private int count;
    private String message;

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }


    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
        System.out.print(message);
    }
}
