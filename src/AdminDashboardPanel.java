import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDashboardPanel extends JPanel {

    public AdminDashboardPanel(JFrame frame) {
        // Buttons for Admin Actions
        JButton viewUsersButton = new JButton("View Users");
        JButton viewCandidatesButton = new JButton("View Candidates");
        JButton addCandidateButton = new JButton("Add Candidate");
        JButton addUserButton = new JButton("Add User");
        JButton backButton = new JButton("Back");

        // Add Action Listeners
        addUserButton.addActionListener(e -> addUser());
        viewUsersButton.addActionListener(e -> showUsers());
        viewCandidatesButton.addActionListener(e -> showCandidates());
        addCandidateButton.addActionListener(e -> addCandidate());
        backButton.addActionListener(e -> {
            frame.setContentPane(new MainLoginPanel(frame));
            frame.revalidate();
        });

        // Layout for the Buttons
        setLayout(new GridLayout(5, 1, 10, 10));
        add(addUserButton);
        add(viewUsersButton);
        add(viewCandidatesButton);
        add(addCandidateButton);
        add(backButton);
    }

    private void addUser() {
        String userId = JOptionPane.showInputDialog(this, "Enter User ID:");
        String userName = JOptionPane.showInputDialog(this, "Enter User Name:");

        if (userId != null && userName != null && !userId.isEmpty() && !userName.isEmpty()) {
            try (Connection conn = DatabaseConfig.getConnection()) {
                String query = "INSERT INTO users (user_id, name) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, userId);
                stmt.setString(2, userName);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "User Added Successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error Adding User", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showUsers() {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM users";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder users = new StringBuilder("Users:\n");
            while (rs.next()) {
                users.append(rs.getString("user_id")).append(" - ").append(rs.getString("name")).append("\n");
            }
            JOptionPane.showMessageDialog(this, users.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showCandidates() {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM candidates";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder candidates = new StringBuilder("Candidates:\n");
            while (rs.next()) {
                candidates.append(rs.getString("name")).append(" - Votes: ").append(rs.getInt("votes")).append("\n");
            }
            JOptionPane.showMessageDialog(this, candidates.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addCandidate() {
        String candidateName = JOptionPane.showInputDialog(this, "Enter Candidate Name:");
        if (candidateName != null && !candidateName.trim().isEmpty()) {
            try (Connection conn = DatabaseConfig.getConnection()) {
                String query = "INSERT INTO candidates (name) VALUES (?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, candidateName);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Candidate Added Successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
