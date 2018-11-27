package client.domain;

public class EyeTracker {

    private String id;
    private double timestamp;

    private String leftxRaw;

    private String leftyRaw;

    private String rightxRaw;

    private String rightyRaw;

    private String pupilLRaw;

    private String pupilRRaw;

    private String task;
    private double leftx;
    private double lefty;
    private double rightx;
    private double righty;
    private double pupilL;
    private double pupilR;


// For Raw data
    public EyeTracker(String id, double timestamp, String leftxRaw, String leftyRaw, String rightxRaw, String rightyRaw, String pupilLRaw, String pupilRRaw) {
        this.id = id;
        this.timestamp = timestamp;
        this.leftxRaw = leftxRaw;
        this.leftyRaw = leftyRaw;
        this.rightxRaw = rightxRaw;
        this.rightyRaw = rightyRaw;
        this.pupilLRaw = pupilLRaw;
        this.pupilRRaw = pupilRRaw;
        this.task = task;
    }

//    For full eyetracking data
    public EyeTracker(String id, double timestamp, double leftx, double lefty, double rightx, double righty, double pupilL, double pupilR, String task) {
        this.id = id;
        this.timestamp = timestamp;
        this.leftx =leftx;
        this.lefty = lefty;
        this.rightx = rightx;
        this.righty = righty;
        this.pupilR = pupilR;
        this.pupilL= pupilL;
        this.task = task;
    }

//    For avg pupilDiameter
    public EyeTracker(String id, double pupilL, double pupilR){
        this.id = id;
        this.pupilL = pupilL;
        this.pupilR = pupilR;
    }

    //    For avg pupilDiameter per Task
    public EyeTracker(String id, double pupilL, double pupilR, String task){
        this.id = id;
        this.pupilL = pupilL;
        this.pupilR = pupilR;
        this.task = task;
    }



    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public String getLeftxRaw() {
        return leftxRaw;
    }

    public void setLeftxRaw(String leftxRaw) {
        this.leftxRaw = leftxRaw;
    }

    public String getLeftyRaw() {
        return leftyRaw;
    }

    public void setLeftyRaw(String leftyRaw) {
        this.leftyRaw = leftyRaw;
    }

    public String getRightxRaw() {
        return rightxRaw;
    }

    public void setRightxRaw(String rightxRaw) {
        this.rightxRaw = rightxRaw;
    }

    public String getRightyRaw() {
        return rightyRaw;
    }

    public void setRightyRaw(String rightyRaw) {
        this.rightyRaw = rightyRaw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPupilRRaw() {
        return pupilRRaw;
    }

    public void setPupilRRaw(String pupilRRaw) {
        this.pupilRRaw = pupilRRaw;
    }

    public String getPupilLRaw() {
        return pupilLRaw;
    }

    public void setPupilLRaw(String pupilLRaw) {
        this.pupilLRaw = pupilLRaw;
    }

    public double getLeftx() {
        return leftx;
    }

    public void setLeftx(double leftx) {
        this.leftx = leftx;
    }

    public double getLefty() {
        return lefty;
    }

    public void setLefty(double lefty) {
        this.lefty = lefty;
    }

    public double getRightx() {
        return rightx;
    }

    public void setRightx(double rightx) {
        this.rightx = rightx;
    }

    public double getRighty() {
        return righty;
    }

    public void setRighty(double righty) {
        this.righty = righty;
    }

    public double getPupilL() {
        return pupilL;
    }

    public void setPupilL(double pupilL) {
        this.pupilL = pupilL;
    }

    public double getPupilR() {
        return pupilR;
    }

    public void setPupilR(double pupilR) {
        this.pupilR = pupilR;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}

