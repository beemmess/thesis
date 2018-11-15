package client.domain;

public class Shimmer {
    private String id;
    private double timestamp;
    private double gsr;
    private double ppg;


    public Shimmer(String id, double timestamp, double gsr, double ppg){
        this.id = id;
        this.timestamp = timestamp;
        this.gsr = gsr;
        this.ppg = ppg;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public double getGsr() {
        return gsr;
    }

    public void setGsr(double gsr) {
        this.gsr = gsr;
    }

    public double getPpg() {
        return ppg;
    }

    public void setPpg(double ppg) {
        this.ppg = ppg;
    }
}
