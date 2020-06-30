package eternal.fire.springbootrobot.javabean;

public class InfoToBan {
    private long userId;
    private int duration;

    public long getUserId() {
        return userId;
    }

    public InfoToBan() {

    }

    public InfoToBan(long userId) {
        this.userId = userId;
    }

    public InfoToBan(long userId, int duration) {
        this.userId = userId;
        this.duration = duration;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
