package ru.pavel_zhukoff.chat_server.data;

import java.io.Serializable;
import java.util.Calendar;

public class Message implements Comparable<Message>, Serializable {

    private static final long SerialVersionUID = 1L;

    private String userId;
    private String text;
    private Calendar date;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public int compareTo(Message message) {
        return this.date.compareTo(message.date);
    }

    @Override
    public int hashCode() {
        return (this.userId.hashCode() << 2) & (this.text.hashCode() >> 3) | (this.date.hashCode() << 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass().equals(obj.getClass())) {
            Message m = (Message) obj;
            return this == obj || this.userId.equals(m.userId) && this.text.equals(m.text) && this.date.equals(m.date);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Message [userId=%s; text=%s; date=%s]", this.userId, this.text, this.date.toString());
    }
}
