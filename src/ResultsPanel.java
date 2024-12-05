import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResultsPanel extends JPanel {
    public ResultsPanel(JFrame frame) {
        JLabel title = new JLabel("Voting Results");

        JPanel resultsPanel = new JPanel();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM candidates";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String candidateName = rs.getString("name");
                int votes = rs.getInt("votes");
                resultsPanel.add(new JLabel(candidateName + ": " + votes + " votes"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            frame.setContentPane(new MainLoginPanel(frame));
            frame.revalidate();
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(title);
        add(resultsPanel);
        add(backButton);
    }
}
