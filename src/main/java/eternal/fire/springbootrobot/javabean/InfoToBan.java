package eternal.fire.springbootrobot.javabean;

public class InfoToBan {
    private Long groupId;
    private Long userId;
    private Integer duration;

    public InfoToBan() {

    }

    public InfoToBan(Long groupId, Long userId, Integer duration) {
        this.groupId = groupId;
        this.userId = userId;
        this.duration = duration;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
