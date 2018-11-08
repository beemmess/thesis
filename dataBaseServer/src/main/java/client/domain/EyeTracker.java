package client.domain;

public class EyeTracker {

    private String userId;
    private double timestamp;

    private String leftx;

    private String lefty;

    private String rightx;

    private String righty;

    private String pupilL;

    private String pupilR;



    public EyeTracker(String userId,double timestamp, String leftx, String lefty, String rightx, String righty, String pupilL, String pupilR) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.leftx = leftx;
        this.lefty = lefty;
        this.rightx = rightx;
        this.righty = righty;
        this.pupilL = pupilL;
        this.pupilR = pupilR;
    }



    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public String getLeftx() {
        return leftx;
    }

    public void setLeftx(String leftx) {
        this.leftx = leftx;
    }

    public String getLefty() {
        return lefty;
    }

    public void setLefty(String lefty) {
        this.lefty = lefty;
    }

    public String getRightx() {
        return rightx;
    }

    public void setRightx(String rightx) {
        this.rightx = rightx;
    }

    public String getRighty() {
        return righty;
    }

    public void setRighty(String righty) {
        this.righty = righty;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPupilR() {
        return pupilR;
    }

    public void setPupilR(String pupilR) {
        this.pupilR = pupilR;
    }

    public String getPupilL() {
        return pupilL;
    }

    public void setPupilL(String pupilL) {
        this.pupilL = pupilL;
    }
}

