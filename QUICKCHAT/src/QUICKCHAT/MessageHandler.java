/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QUICKCHAT;

import javax.swing.*;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MessageHandler {
    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> disregardedMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();
    private static List<String> messageHashes = new ArrayList<>();
    private static List<String> messageIDs = new ArrayList<>();
    
   
    

    public static void startMessaging() {
                readStoredMessagesFromJson(); //load stored messagesJSON file
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

        while (true) {
            String input = JOptionPane.showInputDialog(null,
                  "Choose an option:\n" +
    "1. Send Message\n" +
    "2. Show recently sent messages\n" +
    "3. Display all senders and recipients\n" +
    "4. Display longest sent message\n" +
    "5. Search for a message by ID\n" +
    "6. Search messages by recipient\n" +
    "7. Delete message by hash\n" +
    "8. Display message report\n" +
    "9. Quit"
);

switch (input) {
    case "1":
        sendMessages();
        break;
    case "2":
        JOptionPane.showMessageDialog(null, Message.printMessages());
        break;
    case "3":
        JOptionPane.showMessageDialog(null, displaySendersAndRecipients());
        break;
    case "4":
        JOptionPane.showMessageDialog(null, displayLongestSentMessage());
        break;
    case "5":
        String id = JOptionPane.showInputDialog(null, "Enter Message ID to search:");
        JOptionPane.showMessageDialog(null, searchByMessageID(id));
        break;
    case "6":
        String recipient = JOptionPane.showInputDialog(null, "Enter recipient number (+27...):");
        JOptionPane.showMessageDialog(null, searchByRecipient(recipient));
        break;
    case "7":
        String hash = JOptionPane.showInputDialog(null, "Enter message hash to delete:");
        JOptionPane.showMessageDialog(null, deleteByMessageHash(hash));
        break;
    case "8":
        JOptionPane.showMessageDialog(null, displayReport());
        break;
    case "9":
        JOptionPane.showMessageDialog(null, "Total messages sent: " + sentMessages.size());
        return;
    default:
        JOptionPane.showMessageDialog(null, "Invalid option.");
}
        }
    }

    private static void sendMessages() {
        int count;
        try {
            count = Integer.parseInt(JOptionPane.showInputDialog(null, "How many messages do you want to send?"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
            return;
        }

        for (int i = 0; i < count; i++) {
            String recipient = JOptionPane.showInputDialog(null, "Enter recipient number (+27XXXXXXXXX):");
            String msg = JOptionPane.showInputDialog(null, "Enter message (max 250 chars):");

            if (msg.length() > 250) {
                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                i--;
                continue;
            }

            Message message = new Message(recipient, msg);

            if (!message.checkRecipientCell()) {
                JOptionPane.showMessageDialog(null, "Invalid recipient number.");
                i--;
                continue;
            }

            String[] options = {"Send", "Disregard", "Store for Later"};
            int choice = JOptionPane.showOptionDialog(null, "Choose action for this message:", "Send Message",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                sentMessages.add(message);
                JOptionPane.showMessageDialog(null, "Message sent!\n\n" + message.printMessageDetails());
            } else if (choice == 2) {
                message.storeMessage();
                JOptionPane.showMessageDialog(null, "Message stored for later.");
            } else {
                JOptionPane.showMessageDialog(null, "Message disregarded.");
            }
        }
    }
          // Now read stored messages from JSON file to storedMessages array (optional if you want real file data)
   public static void readStoredMessagesFromJson() {
     JSONParser parser = new JSONParser();
        try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject jsonObject = (JSONObject) parser.parse(line);
                String recipient = (String) jsonObject.get("Recipient");
                String message = (String) jsonObject.get("Message");
                Message storedMsg = new Message(recipient, message);
                storedMessages.add(storedMsg);
                messageHashes.add(storedMsg.getMessageHash());
                messageIDs.add(storedMsg.getMessageId());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
   }
    // a. Display the sender and recipient of all sent messages
public static String displaySendersAndRecipients() {
    StringBuilder sb = new StringBuilder();
    for (Message msg : sentMessages) {
        sb.append("Sender: ").append("Developer") // Since sender info isn’t in Message, assuming "Developer"
          .append(", Recipient: ").append(msg.getRecipient())
          .append("\n");
    }
    return sb.toString();
}

// b. Display the longest sent message
public static String displayLongestSentMessage() {
    if (sentMessages.isEmpty()) return "No sent messages.";
    Message longestMsg = sentMessages.get(0);
    for (Message msg : sentMessages) {
        if (msg.getMsg().length() > longestMsg.getMsg().length()) {
            longestMsg = msg;
        }
    }
    return longestMsg.getMsg();
}

// c. Search for a message ID and display the recipient and message
public static String searchByMessageID(String id) {
    for (Message msg : sentMessages) {
        if (msg.getMessageId().equals(id)) {
            return "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getMsg();
        }
    }
    for (Message msg : storedMessages) {
        if (msg.getMessageId().equals(id)) {
            return "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getMsg();
        }
    }
    for (Message msg : disregardedMessages) {
        if (msg.getMessageId().equals(id)) {
            return "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getMsg();
        }
    }
    return "Message ID not found.";
}

// d. Search all messages sent or stored regarding a particular recipient
public static String searchByRecipient(String recipient) {
    StringBuilder sb = new StringBuilder();
    for (Message msg : sentMessages) {
        if (msg.getRecipient().equals(recipient)) {
            sb.append(msg.getMsg()).append("\n");
        }
    }
    for (Message msg : storedMessages) {
        if (msg.getRecipient().equals(recipient)) {
            sb.append(msg.getMsg()).append("\n");
        }
    }
    return sb.length() > 0 ? sb.toString() : "No messages found for recipient: " + recipient;
}

// e. Delete a message using the message hash
public static String deleteByMessageHash(String hash) {
    Iterator<Message> it = sentMessages.iterator();
    while (it.hasNext()) {
        Message msg = it.next();
        if (msg.getMessageHash().equals(hash)) {
            it.remove();
            messageHashes.remove(hash);
            messageIDs.remove(msg.getMessageId());
            return "Message \"" + msg.getMsg() + "\" successfully deleted.";
        }
    }
    return "Message hash not found.";
}

// f. Display a report listing full details of all sent messages
public static String displayReport() {
    StringBuilder sb = new StringBuilder();
    sb.append("=== Sent Messages Report ===\n");
    for (Message msg : sentMessages) {
        sb.append("Message Hash: ").append(msg.getMessageHash()).append("\n")
          .append("Recipient: ").append(msg.getRecipient()).append("\n")
          .append("Message: ").append(msg.getMsg()).append("\n\n");
    }
    return sb.toString();
}
}
