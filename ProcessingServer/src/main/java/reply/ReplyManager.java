package reply;

import java.util.ArrayList;

/**
 * A class to keep records on messages going to the database server
 * as well as messages coming from the database server
 */
public class ReplyManager {


    private static ReplyManager instance;
    private ArrayList<String> reply;
    private int count;

    private ReplyManager() {
        reply = new ArrayList<>();
    }

    /**
     * A singleton instance
     */
    public static synchronized ReplyManager getInstance() {
        if (instance == null) {
            instance = new ReplyManager();
        }
        return instance;
    }

    /**
     * A method to add the reply message from the Database server to the array list
     * @param message
     */
    public void addReplyToList(String message) {
        reply.add(message);
    }

    /**
     * A method to clear the list
     */
    public void clearList(){
        reply.clear();
    }

    /**
     * A method to clear the count of messages
     */
    public void clearCount() {this.count = 0;}

    /**
     * A method to return the size of the list in the array
     * @return list size
     */
    public int getListSize(){
        return reply.size();
    }

    /**
     * A Method to get the whole array list
     * @return array list
     */
    public Object[] getArrayList(){
        return reply.toArray();
    }

    /**
     * A method to return the count of messages sent to the Database server
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * A method to set the count of messages sent to the Database server
     */
    public void setCount(int count) {

        this.count = count;
    }


}