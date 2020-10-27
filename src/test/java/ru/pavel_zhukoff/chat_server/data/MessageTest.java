package ru.pavel_zhukoff.chat_server.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class MessageTest {

    Message m1, m2, m3;
    Calendar c;

    @Before
    public void init() {
        c = Calendar.getInstance();
        m1 = new Message();
        m1.setDate(c);
        m1.setText("message");
        m1.setUserId("id");

        m2 = new Message();
        m2.setDate(c);
        m2.setText("message");
        m2.setUserId("id");

        m3 = new Message();
        m3.setDate(Calendar.getInstance());
        m3.setText("message1");
        m3.setUserId("id");
    }

    @Test
    public void testEquals() {

        Assert.assertNotEquals(null, m1);
        Assert.assertEquals(m1, m2);
        Assert.assertNotEquals(m1, m3);

    }

    @Test
    public void testToString() {
        Assert.assertEquals(String.format("Message [userId=id; text=message; date=%s]", c), m1.toString());

    }
}