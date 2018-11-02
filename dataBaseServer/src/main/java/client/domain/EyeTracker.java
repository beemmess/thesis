package client.domain;

public class EyeTracker {


    private double timestamp;

    private double leftx;

    private double lefty;

    private double rightx;

    private double righty;



    public EyeTracker(double timestamp, double leftx, double lefty, double rightx, double righty) {
        this.timestamp = timestamp;
        this.leftx = leftx;
        this.lefty = lefty;
        this.rightx = rightx;
        this.righty = righty;
    }



    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
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

}

