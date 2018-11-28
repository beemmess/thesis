package reply;

import java.util.ArrayList;

public class ReplyManager {


    private static ReplyManager instance;
    private ArrayList<String> reply;
    private int count;

    private ReplyManager() {
        reply = new ArrayList<>();
    }

    //    getting the singleton instance
    public static synchronized ReplyManager getInstance() {
        if (instance == null) {
            instance = new ReplyManager();
        }
        return instance;
    }

    public void addReplyToList(String message) {
        reply.add(message);
    }

    public void clearList(){
        reply.clear();
    }

    public void clearCount() {this.count = 0;}

    public int getListSize(){
        return reply.size();
    }

    public Object[] getArrayList(){
        return reply.toArray();
    }

    public int getCount() {
        return count;
    }

    public void setCount() {

        this.count += 1;
    }


}