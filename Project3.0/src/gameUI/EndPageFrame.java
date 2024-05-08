package gameUI;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndPageFrame extends JFrame {
    private JLabel backgroundLabel;
    private JLabel title;
    private JLabel resultLabel;

    public EndPageFrame() {
        super("End Page Gomoku");
        setLayout(new BorderLayout());

        // Background image
        ImageIcon backgroundImage = new ImageIcon("Project/Img/board.jpg");
        backgroundLabel = new JLabel(backgroundImage);
        add(backgroundLabel, BorderLayout.CENTER); // Set the image as the background

        // Title label
        title = new JLabel();
        setTitleLabelText();
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, BorderLayout.NORTH);

        // Result label
        resultLabel = new JLabel("Thank you for playing Gomoku!");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultLabel.setHorizontalAlignment(JLabel.CENTER);

        // Panel for center layout
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false); // Make the panel transparent
        centerPanel.add(resultLabel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Add a button for more interaction
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.addActionListener(e -> dispose()); // Close the frame when clicked

        // Panel for button layout
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.add(closeButton, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setVisible(true);
    }

    private void setTitleLabelText() {
        if (Main.GAME_STATUS == gameLogic.GameStatus.black_player_win)
            title.setText("Black Player Wins!");
        else if (Main.GAME_STATUS == gameLogic.GameStatus.white_player_win)
            title.setText("White Player Wins!");
        else if (Main.GAME_STATUS == gameLogic.GameStatus.draw)
            title.setText("It's A Draw!");
    }

}
