import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPanel extends JPanel {
    public LoginPanel(JFrame frame) {
        JLabel titleLabel = new JLabel("Welcome to the Voting System");
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        JLabel userLabel = new JLabel("Enter User ID:");
        JTextField userField = new JTextField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String userId = userField.getText().trim();
            if (authenticateUser(userId)) {
                frame.setContentPane(new VotingPanel(frame, userId));
                frame.revalidate();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(titleLabel)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(userLabel)
                    .addComponent(userField))
                .addComponent(loginButton)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(userLabel)
                    .addComponent(userField))
                .addComponent(loginButton)
        );
    }

    private boolean authenticateUser(String userId) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
