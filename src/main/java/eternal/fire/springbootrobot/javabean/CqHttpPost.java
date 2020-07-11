package eternal.fire.springbootrobot.javabean;

public class CqHttpPost {
    private String message;
    private String group_id;
    private long user_id;
    private String message_type;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage() {
        return message;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getMessage_type() {
        return message_type;
    }

    public CqHttpPost() {

    }

    public CqHttpPost(String message, String group_id, long user_id, String message_type) {
        this.message = message;
        this.group_id = group_id;
        this.user_id = user_id;
        this.message_type = message_type;
    }
}
