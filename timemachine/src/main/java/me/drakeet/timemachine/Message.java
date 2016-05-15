package me.drakeet.timemachine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Date;

/**
 * @author drakeet
 */
public class Message {

    public String content;
    public String fromUserId;
    public String toUserId;
    public Date createdAt;


    public Message(String content) {
        this(content, /*toUserId = */null);
    }


    public Message(@NonNull String content, @Nullable String toUserId) {
        this(content, TimeKey.userId, toUserId, new Now());
    }


    public Message(String content, String fromUserId, String toUserId, Date createdAt) {
        this.content = content;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.createdAt = createdAt;
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
}
