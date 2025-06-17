/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package st10460453.poe.part1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RC_Student_lab
 */
public class MessageTest {
    
       @Test
    public void testMessageLengthValid() {
        String message = "This is a short message.";
        assertTrue(message.length() <= 250, "Message should be valid");
    }

    @Test
    public void testMessageLengthTooLong() {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            message.append("x");
        }
        int excess = message.length() - 250;
        assertTrue(message.length() > 250, "Message exceeds 250 characters by " + excess);
    }

    @Test
    public void testRecipientFormat_Success() {
        String number = "+27718693002";
        assertTrue(number.matches("\\+27[0-9]{9}"), "Cell phone number successfully captured.");
    }

    @Test
    public void testRecipientFormat_Failure() {
        String number = "08575975889";
        assertFalse(number.matches("\\+27[0-9]{9}"),
                "Cell phone number is incorrectly formatted or does not contain an international code.");
    }
}