package model;



public class EyeTrackerMessage extends Message {

    private String id;
    private String features;
    private String data;
    private String type;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}