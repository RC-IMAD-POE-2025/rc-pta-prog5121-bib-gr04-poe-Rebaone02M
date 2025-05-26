/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package st10460453.poe.part1;

import javax.swing.*;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MessageHandler {
    private static List<Message> sentMessages = new ArrayList<>();

    public static void startMessaging() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

        while (true) {
            String input = JOptionPane.showInputDialog(null,
                    "Choose an option:\n1. Send Message\n2. Show recently sent messages\n3. Quit");

            if (input == null) break;

            switch (input) {
                case "1":
                    sendMessages();
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;
                case "3":
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
}