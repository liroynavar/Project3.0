package gameUI;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SettingsPage extends JFrame {
    private Settings settings;
    private BufferedImage  _bg;

	public SettingsPage(Settings settings) {
        super("Game Settings");
        this.settings = settings != null ? settings : new Settings();

        JPanel background = new JPanel(new FlowLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (_bg != null) {
                    // Draw the background image if it's not null
                	g.drawImage(_bg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        setContentPane(background);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        // Game Mode
        JLabel gameModeLabel = new JLabel("Game Mode:");
        gameModeLabel.setFont(labelFont);
        panel.add(gameModeLabel, gbc);

        gbc.gridy++;
        ButtonGroup gameMode = new ButtonGroup();
        JRadioButton pvpButton = createRadioButton("Player vs Player", "PvP");
        JRadioButton pvcButton = createRadioButton("Player vs Computer", "PvC");
        pvcButton.setSelected(true);
        JPanel gameModePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        gameModePanel.add(pvpButton);
        gameModePanel.add(pvcButton);
        panel.add(gameModePanel, gbc);
        gameMode.add(pvcButton);
        gameMode.add(pvpButton);
        // Music Volume
        gbc.gridy++;
        JLabel musicVolumeLabel = new JLabel("Music Volume:");
        musicVolumeLabel.setFont(labelFont);
        panel.add(musicVolumeLabel, gbc);

        gbc.gridy++;
        JSlider musicVolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, settings.getMusicVolume());
        musicVolumeSlider.setMajorTickSpacing(10);
        musicVolumeSlider.setPaintTicks(true);
        musicVolumeSlider.setPaintLabels(true);
        musicVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                settings.setMusicVolume(musicVolumeSlider.getValue());
            }
        });
        panel.add(musicVolumeSlider, gbc);
        
        // Player Color
        gbc.gridy++;
        
        JLabel playerColorLabel = new JLabel("Player Color:");
        playerColorLabel.setFont(labelFont);
        panel.add(playerColorLabel, gbc);
        ButtonGroup color = new ButtonGroup();
        gbc.gridy++;
        JRadioButton blackButton = createRadioButton("Black", "Black");
        JRadioButton whiteButton = createRadioButton("White", "White");
        JPanel playerColorPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        playerColorPanel.add(blackButton);
        playerColorPanel.add(whiteButton);
        panel.add(playerColorPanel, gbc);
        color.add(blackButton);
        color.add(whiteButton);
        blackButton.setSelected(true);
        // Difficulty
        gbc.gridy++;
        JLabel difficultyLabel = new JLabel("Difficulty:");
        difficultyLabel.setFont(labelFont);
        panel.add(difficultyLabel, gbc);

        gbc.gridy++;
        JComboBox<String> difficultyComboBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        difficultyComboBox.setSelectedItem(settings.getDifficulty());
        difficultyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setDifficulty((String) difficultyComboBox.getSelectedItem());
            }
        });
        panel.add(difficultyComboBox, gbc);

        // Player Name
        gbc.gridy++;
        JLabel nameLabel = new JLabel("Player Name:");
        nameLabel.setFont(labelFont);
        panel.add(nameLabel, gbc);

        gbc.gridy++;
        JTextField nameTextField = new JTextField(settings.getPlayerName());
        nameTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setPlayerName(nameTextField.getText());
            }
        });
        panel.add(nameTextField, gbc);

        // Save Button
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveButton = new JButton("Save");
        saveButton.setFont(buttonFont);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	settings.setGameMode(gameMode.getSelection().getActionCommand());
            	settings.setPlayerColor(color.getSelection().getActionCommand());
            	settings.setPlayerName(nameTextField.getText());
                saveSettings();
            }
        });
        panel.add(saveButton, gbc);
        background.add(panel);
        //add(background);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JRadioButton createRadioButton(String text, String actionCommand) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
        radioButton.setForeground(Color.BLACK);
        radioButton.setActionCommand(actionCommand);
        radioButton.setSelected(actionCommand.equals(settings.getGameMode()));

        // Customize radio button appearance
        radioButton.setOpaque(false);
        radioButton.setBorderPainted(false);
        radioButton.setFocusPainted(false);

        return radioButton;
    }

    private void saveSettings() {
    	dispose();
    	System.out.println(this.settings.toString());
    	StartPageFrame spf = new StartPageFrame(settings);
    }
}
