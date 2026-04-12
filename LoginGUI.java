import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    JTextField userField;
    JPasswordField passField;

    public LoginGUI() {
        setTitle("Teacher Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 44, 52));
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        userField = new JTextField();
        passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(98, 114, 164));
        loginBtn.setForeground(Color.WHITE);

        panel.add(new JLabel("Username", JLabel.CENTER));
        panel.add(userField);
        panel.add(new JLabel("Password", JLabel.CENTER));
        panel.add(passField);

        add(panel, BorderLayout.CENTER);
        add(loginBtn, BorderLayout.SOUTH);

        // LOGIN LOGIC
        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (user.equals("teacher") && pass.equals("1234")) {
                dispose(); // close login
                new StudentGUI(); // open main GUI
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login!");
            }
        });

        setVisible(true);
        setLocationRelativeTo(null);
    }
}