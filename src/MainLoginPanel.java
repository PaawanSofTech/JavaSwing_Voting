import javax.swing.*;

public class MainLoginPanel extends JPanel {

    public MainLoginPanel(JFrame frame) {
        JLabel titleLabel = new JLabel("Welcome to the Voting System");
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        JButton userLoginButton = new JButton("User Login");
        JButton adminLoginButton = new JButton("Admin Login");

        userLoginButton.addActionListener(e -> {
            frame.setContentPane(new LoginPanel(frame));
            frame.revalidate();
        });

        adminLoginButton.addActionListener(e -> {
            frame.setContentPane(new AdminLoginPanel(frame));
            frame.revalidate();
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        userLoginButton.setAlignmentX(CENTER_ALIGNMENT);
        adminLoginButton.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(50)); // Adds spacing
        add(titleLabel);
        add(Box.createVerticalStrut(20));
        add(userLoginButton);
        add(Box.createVerticalStrut(10));
        add(adminLoginButton);
    }
}
