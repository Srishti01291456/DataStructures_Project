import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        // Run GUI properly on Swing thread
        SwingUtilities.invokeLater(() -> {
            new LoginGUI();
        });

    }
}