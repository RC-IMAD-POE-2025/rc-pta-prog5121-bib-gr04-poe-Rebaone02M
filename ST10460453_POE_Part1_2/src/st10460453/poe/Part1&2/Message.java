/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    
package st10460453.poe.part1;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    private static int messageCounter = 0;
    private static ArrayList<Message> sentMessages = new ArrayList<>();

    private String messageId;
    private String recipient;
    private String msg;
    private String messageHash;

    // Constructor
    public Message(String recipient, String msg) {
        this.messageId = generateMessageId();
        this.recipient = recipient;
        this.msg = msg;
        this.messageHash = createMessageHash();
        messageCounter++;
        sentMessages.add(this);
    }

    // Generate unique 10-digit message ID
    private String generateMessageId() {
        Random rand = new Random();
        long number = 1000000000L + rand.nextLong(9000000000L);
        return String.valueOf(number);
    }

    // 1. Check that message ID is no more than 10 characters
    public boolean checkMessageID() {
        return messageId.length() <= 10;
    }

    // 2. Check recipient number
    public boolean checkRecipientCell() {
        return recipient.startsWith("+27") && recipient.length() == 12 && recipient.substring(3).matches("\\d{9}");
    }

    // 3. Create message hash
    public String createMessageHash() {
        String[] words = msg.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        return messageId.substring(0, 2) + ":" + messageCounter + ":" + firstWord.toUpperCase() + lastWord.toUpperCase();
    }

    // 4. User chooses what to do with message
    public String SentMessage() {
        String[] options = {"Send", "Disregard", "Store for Later"};
        int choice = JOptionPane.showOptionDialog(null, "Choose action for this message:", "Send Message",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            return "Message sent.";
        } else if (choice == 1) {
            return "Message disregarded.";
        } else if (choice == 2) {
            storeMessage();
            return "Message stored for later.";
        } else {
            return "Invalid option.";
        }
    }

    // 5. Print message details
    public String printMessageDetails() {
        return "Message ID: " + messageId +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + msg;
    }

    // 6. Print all messages sent so far
    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append(m.printMessageDetails()).append("\n\n");
        }
        return sb.toString();
    }

    // 7. Return total messages sent
    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    // 8. Store message in JSON file
    public void storeMessage() {
        try {
            JSONObject json = new JSONObject();
            json.put("MessageID", messageId);
            json.put("MessageHash", messageHash);
            json.put("Recipient", recipient);
            json.put("Message", msg);

            FileWriter file = new FileWriter("messages.json", true);
            file.write(json.toString() + System.lineSeparator());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}