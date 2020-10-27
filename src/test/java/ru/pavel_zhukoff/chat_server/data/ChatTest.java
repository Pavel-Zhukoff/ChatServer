package ru.pavel_zhukoff.chat_server.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ChatTest {

    Message m1, m2, m3;
    Chat chat1, chat2, chat3;

    @Before
    public void init() {
        m1 = new Message();
        m1.setDate(Calendar.getInstance());
        m1.setText("message");
        m1.setUserId("id");

        m2 = new Message();
        m2.setDate(Calendar.getInstance());
        m2.setText("message1");
        m2.setUserId("id");

        m3 = new Message();
        m3.setDate(Calendar.getInstance());
        m3.setText("message1");
        m3.setUserId("id");

        Set<Message> messageSet1 = new TreeSet<>(List.of(m1, m2));
        Set<Message> messageSet2 = new TreeSet<>(List.of(m1, m2));
        Set<Message> messageSet3 = new TreeSet<>(List.of(m1));

        String uuid1 = "id1";
        String uuid2 = "id1";
        String uuid3 = "id1";

        Set<String> userSet1 = new HashSet<>(List.of("Bob", "Carl"));
        Set<String> userSet2 = new HashSet<>(List.of("Bob", "Carl"));
        Set<String> userSet3 = new HashSet<>(List.of("Bob", "Joe"));

        chat1 = new Chat();
        chat2 = new Chat();
        chat3 = new Chat();

        chat1.setUuid(uuid1);
        chat1.setMessages(messageSet1);
        chat1.setUsers(userSet1);

        chat2.setUuid(uuid2);
        chat2.setMessages(messageSet2);
        chat2.setUsers(userSet2);

        chat3.setUuid(uuid3);
        chat3.setMessages(messageSet3);
        chat3.setUsers(userSet3);
    }

    @Test
    public void testEquals() {
        Assert.assertNotEquals(null, chat1);
        Assert.assertEquals(chat1, chat2);
        Assert.assertNotEquals(chat1, chat3);
    }

    @Test
    public void testToString() {
        Assert.assertEquals("Chat [users=[Bob, Carl]; messages-count=2]", chat1.toString());

    }
}