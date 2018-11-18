package reply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


    public void addToReplyList(String message) {
        reply.add(message);
    }

    public void clearList(){
        reply.clear();
    }

    public int getListSize(){
        return reply.size();
    }

    public Object[] getArrayList(){
        return reply.toArray();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}