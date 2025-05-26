/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package st10460453.poe.part1;

import javax.swing.SwingUtilities;

/**
 *
 * @author RC_Student_lab
 */
public class ST10460453POEPart1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create the GUI instance and run the application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UserRegistrationAndLoginGUI();
            }
        });
    }
    
}
