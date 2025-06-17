/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package st10460453.poe.part1;

import QUICKCHAT.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class MessageHandlerTest {

    @Test
    public void testSentMessagesCorrectlyPopulated() {
        Message m1 = new Message("+27834557896", "Did you get the cake?");
        Message m4 = new Message("0838884567", "It is dinner time !");
        List<Message> sentMessages = new ArrayList<>();
        sentMessages.add(m1);
        sentMessages.add(m4);

        assertEquals("Did you get the cake?", sentMessages.get(0).getMsg());
        assertEquals("It is dinner time !", sentMessages.get(1).getMsg());
    }

    @Test
    public void testLongestMessage() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("+27834557896", "Did you get the cake?"));
        messages.add(new Message("+27838884567", "Where are you? You are late! I have asked you to be on time."));
        messages.add(new Message("+27834484567", "Yohoooo, I am at your gate."));

        Message longest = messages.get(0);
        for (Message msg : messages) {
            if (msg.getMsg().length() > longest.getMsg().length()) {
                longest = msg;
            }
        }

        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getMsg());
    }

    @Test
    public void testSearchByMessageId() {
        Message m4 = new Message("0838884567", "It is dinner time !");
        String id = m4.getMessageId();

        Map<String, Message> map = new HashMap<>();
        map.put(id, m4);

        assertEquals("It is dinner time !", map.get(id).getMsg());
    }

    @Test
    public void testSearchByRecipient() {
        List<Message> results = new ArrayList<>();
        results.add(new Message("+27838884567", "Where are you? You are late! I have asked you to be on time."));
        results.add(new Message("+27838884567", "Ok, I am leaving without you."));

        for (Message msg : results) {
            assertEquals("+27838884567", msg.getRecipient());
        }

        assertEquals(2, results.size());
    }

    @Test
    public void testDeleteByHash() {
        Message toDelete = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        String hash = toDelete.getMessageHash();

        List<Message> messages = new ArrayList<>();
        messages.add(toDelete);
        messages.removeIf(m -> m.getMessageHash().equals(hash));

        assertTrue(messages.isEmpty());
    }

    @Test
    public void testDisplayReport() {
        List<Message> sentMessages = new ArrayList<>();
        sentMessages.add(new Message("+27834557896", "Did you get the cake?"));
        sentMessages.add(new Message("0838884567", "It is dinner time !"));

        for (Message msg : sentMessages) {
            assertNotNull(msg.getMessageId());
            assertNotNull(msg.getMessageHash());
            assertNotNull(msg.getRecipient());
            assertNotNull(msg.getMsg());
        }
    }
}