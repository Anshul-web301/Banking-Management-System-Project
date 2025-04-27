package App;
import Gui.LoginFrame;
import javax.swing.*;
public class MainGui {
    public static void main(String[] args) {
        // Set system look and feel for native GUI appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Look and feel could not be set.");
        }

        // Launch the Login GUI
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();  // GUI starts here
        });
    }
}


