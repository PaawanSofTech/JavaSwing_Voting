import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLoginPanel extends JPanel {
    public AdminLoginPanel(JFrame frame) {
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            if (authenticateAdmin(username, password)) {
                frame.setContentPane(new AdminDashboardPanel(frame));
                frame.revalidate();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(loginButton);
    }

    private boolean authenticateAdmin(String username, String password) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
