import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VotingPanel extends JPanel {
    public VotingPanel(JFrame frame, String userId) {
        JLabel label = new JLabel("Select a Candidate:");
        ButtonGroup group = new ButtonGroup();
        JPanel candidatesPanel = new JPanel();

        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM candidates";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String candidateName = rs.getString("name");
                JRadioButton button = new JRadioButton(candidateName);
                group.add(button);
                candidatesPanel.add(button);
                button.addActionListener(e -> voteForCandidate(userId, candidateName));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JButton voteButton = new JButton("Submit Vote");
        voteButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Vote Submitted. Thank you!");
            frame.setContentPane(new ResultsPanel(frame));
            frame.revalidate();
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(label);
        add(candidatesPanel);
        add(voteButton);
    }

    private void voteForCandidate(String userId, String candidateName) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String updateVotes = "UPDATE candidates SET votes = votes + 1 WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(updateVotes);
            stmt.setString(1, candidateName);
            stmt.executeUpdate();

            String markVoted = "UPDATE users SET voted = 1 WHERE user_id = ?";
            PreparedStatement userStmt = conn.prepareStatement(markVoted);
            userStmt.setString(1, userId);
            userStmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
