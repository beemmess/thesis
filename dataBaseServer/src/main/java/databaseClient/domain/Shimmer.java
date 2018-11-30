package databaseClient.domain;

public class Shimmer {
    private String id;
    private double timestamp;
    private double gsr;
    private double ppg;
    private String task;


    public Shimmer(String id, double timestamp, double gsr, double ppg, String task){
        this.id = id;
        this.timestamp = timestamp;
        this.gsr = gsr;
        this.ppg = ppg;
        this.task = task;

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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
