package me.drakeet.timemachine;

import java.util.Date;

/**
 * @author drakeet
 */
public class Message implements Cloneable {

    public String content;
    public String fromUserId;
    public String toUserId;
    public Date createdAt;


    public Message() {
    }


    private Message(String content, String fromUserId, String toUserId, Date createdAt) {
        this.content = content;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.createdAt = createdAt;
    }


    public Message(Message message) {
        this.content = message.content;
        this.fromUserId = message.fromUserId;
        this.toUserId = message.toUserId;
        this.createdAt = message.createdAt;
    }


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        if (!content.equals(message.content)) return false;
        if (!fromUserId.equals(message.fromUserId)) return false;
        if (toUserId != null ? !toUserId.equals(message.toUserId) : message.toUserId != null) {
            return false;
        }
        return createdAt.equals(message.createdAt);
    }


    @Override public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + fromUserId.hashCode();
        result = 31 * result + (toUserId != null ? toUserId.hashCode() : 0);
        result = 31 * result + createdAt.hashCode();
        return result;
    }


    @Override public String toString() {
        return "Message{" +
            "content='" + content + '\'' +
            ", fromUserId='" + fromUserId + '\'' +
            ", toUserId='" + toUserId + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }


    @Override public Message clone() {
        Message message = null;
        try {
            message = (Message) super.clone();
            message.createdAt = new Date(message.createdAt.getTime());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return message;
    }


    public static class Builder implements me.drakeet.timemachine.Builder<Message> {
        private String content;
        private String fromUserId;
        private String toUserId;
        private Date createdAt;


        public Builder setContent(String content) {
            this.content = content;
            return this;
        }


        public Builder setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
            return this;
        }


        public Builder setToUserId(String toUserId) {
            this.toUserId = toUserId;
            return this;
        }


        public Builder setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }


        public Message thenCreateAtNow() {
            this.createdAt = new Now();
            return build();
        }


        @Override public Message build() {
            return new Message(content, fromUserId, toUserId, createdAt);
        }
    }
}
