package ru.pavel_zhukoff.chat_server.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Chat implements Serializable {

    private static final long SerialVersionUID = 1L;

    private String uuid;

    private Set<String> users;

    private Set<Message> messages;

    public Chat() {
        this.users = new HashSet<>();
        this.messages = new TreeSet<>();
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int hashCode() {
        return ~(this.uuid.hashCode() >> 1)
                & (this.users.hashCode() << 2)
                | (this.messages.hashCode() >> 3);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass().equals(obj.getClass())) {
            Chat m = (Chat) obj;
            return this == obj
                    || this.uuid.equals(m.uuid) && this.users.equals(m.users) && this.messages.equals(m.messages);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Chat [users=[%s]; messages-count=%d]",
                this.users.stream().reduce((s1,s2) -> String.format("%s, %s", s1, s2)).orElse(""),
                this.messages.size());
    }

}
