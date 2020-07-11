package eternal.fire.springbootrobot.javabean;

public class CqHttpReply {
    private String reply;
    private boolean block;

    public CqHttpReply(String reply) {
        this.reply = reply;
        this.block = true;
    }

    @Override
    public String toString() {
        return String.format("{\"reply\": \"%s\", \"block\": %b}", reply, block);
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public CqHttpReply(String reply, boolean block) {
        this.reply = reply;
        this.block = block;
    }
}
