import javax.swing.*;

public class VotingSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Voting System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setContentPane(new MainLoginPanel(frame)); // Show main login panel
            frame.setVisible(true);
        });
    }
}
