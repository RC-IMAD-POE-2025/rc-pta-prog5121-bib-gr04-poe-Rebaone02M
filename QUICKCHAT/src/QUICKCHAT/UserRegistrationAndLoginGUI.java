/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QUICKCHAT;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author RC_Student_lab
 */
public class UserRegistrationAndLoginGUI {
    JFrame frame;
    JTextField firstNameField, lastNameField, usernameField, passwordField, phoneField;
    JTextArea resultArea;
    UserAccount currentAccount;

    public UserRegistrationAndLoginGUI() {
        frame = new JFrame("User Registration and Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(20);
        JLabel usernameLabel = new JLabel("Username (contains underscore and <= 5 chars): ");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password (at least 8 chars, a capital letter, a number, special char): ");
        passwordField = new JPasswordField(20);
        JLabel phoneLabel = new JLabel("Phone Number (+27XXXXXXXXX): ");
        phoneField = new JTextField(20);

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);

        // Register button listener
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                String phone = phoneField.getText();

                currentAccount = new UserAccount( firstName ,lastName , username, password, phone);
                String registrationMessage = registerUser(currentAccount);
                resultArea.setText(registrationMessage);
            }
        });

        // Login button listener
        loginButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        if (currentAccount != null &&
                enteredUsername.equals(currentAccount.username) &&
                enteredPassword.equals(currentAccount.password)) {

           resultArea.setText(currentAccount.returnLoginStatus());
frame.dispose(); // Close the GUI
MessageHandler.startMessaging(); // Start the messaging menu

        } else {
            resultArea.setText("Username or password incorrect, please try again.");
        }
    }
});
        frame.add(firstNameLabel);
        frame.add(firstNameField);
        frame.add(lastNameLabel);
        frame.add(lastNameField);
        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(phoneLabel);
        frame.add(phoneField);
        frame.add(registerButton);
        frame.add(loginButton);
        frame.add(new JScrollPane(resultArea));

        frame.setVisible(true);
    }

    // Method for user registration
    public String registerUser(UserAccount account) {
        if (!account.checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!account.checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!account.checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        return "Registration successful! Username, password, and cell phone number are valid.";
    }

    // Method to handle login
    public String loginUser(String enteredUsername, String enteredPassword, UserAccount account) {
        if (enteredUsername.equals(account.username) && enteredPassword.equals(account.password)) {
            return "Welcome " + account.firstName + ","+ account.lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
}
