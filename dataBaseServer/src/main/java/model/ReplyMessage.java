package model;


/**
 * A class that is used for deserilising and serilising the JSON string to generate a reply message
 * to send to the Processing Server
 */
public class ReplyMessage  extends Message {

    private String data;
    private String type;
    private String replyMessage;
    private Boolean sucess;

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getSucess() {
        return sucess;
    }

    public void setSucess(Boolean sucess) {
        this.sucess = sucess;
    }
}
