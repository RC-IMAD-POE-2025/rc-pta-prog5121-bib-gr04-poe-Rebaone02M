/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QUICKCHAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RC_Student_lab
 */
public class UserAccount {
    String firstName;
    String lastName;
     String username;
    String password;
    String phoneNumber;

    // Constructor to initialize the account data
    public UserAccount(String firstName, String lastName ,String username, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Method to check if username is correctly formatted
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Method to check if password meets the required complexity
    public boolean checkPasswordComplexity() {
        // Password should be at least 8 characters long, contain a capital letter, a number, and a special character
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Method to check if the cell phone number is correctly formatted
    public boolean checkCellPhoneNumber() {
        // The phone number should start with +27 (South Africa's country code) and have a length of 13 characters (including the country code)
        String regex = "^\\+27[0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
      // Method used during login
    public void loginUser(String enteredUsername, String enteredPassword, UserAccount account) {
        if (enteredUsername.equals(account.username) && enteredPassword.equals(account.password)); 
       
    }
    //method that gives the "welcome" message
        public String returnLoginStatus(){
            return "Welcome " + firstName + ","+ lastName + " it is great to see you again.";
                  
        }

    
}
