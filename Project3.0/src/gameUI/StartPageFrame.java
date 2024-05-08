package gameUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartPageFrame extends JFrame {
    private JLabel _imageLabel; 
    private BufferedImage  _bg;
    private JLabel _textLabel;
    private JButton _startButton;
    private JButton _rulesButton;
    private JButton _settingsButton;
    private Settings _settings;

    public StartPageFrame(Settings settings) {
        super("Start Page Gomoku");
        _settings = settings;
        try {
        	_bg = ImageIO.read(new File("C:\\Users\\liroy\\Desktop\\Project3.0\\img\\pic2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Set the content pane to be a JPanel with BorderLayout
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10000, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (_bg != null) {
                    // Draw the background image if it's not null
                	g.drawImage(_bg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        setContentPane(buttonsPanel);
        
        
        _textLabel = new JLabel("\tWelcome to Gomoku!");
        _textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        _textLabel.setForeground(Color.black);
        _textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        _textLabel.setBounds(200, 50, 400, 50); 

        JLabel name = new JLabel("Hello " + _settings.getPlayerName());
        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        name.setFont(font1);
        name.setOpaque(false); 
        name.setForeground(Color.black);
        name.setBounds(300, 100, 200, 50);
        buttonsPanel.add(name);
        
        _rulesButton = new JButton("Rules");
        styleButton(_rulesButton);
        _rulesButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		RulesFrame rFrame = new RulesFrame(_settings);
        		dispose();
        	}
        });
        
        
        _startButton = new JButton("Start Game");
        styleButton(_startButton);
        _startButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		LoadingScreen loading = new LoadingScreen(_settings);
        		dispose();
        	}
        });
        
        
        _settingsButton = new JButton("Settings");
        styleButton(_settingsButton);
        _settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	openSettingsPage();
            }
        });
        
        
        add(_textLabel, BorderLayout.NORTH);
        buttonsPanel.add(_rulesButton);
        buttonsPanel.add(_startButton);
        buttonsPanel.add(_settingsButton);
        
        
        // Set frame properties
        setSize(800, 400); // Adjusted width to accommodate the image
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(300,50));
        button.setBackground(new Color(0, 153, 51));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setUI(new StyledButtonUI());
    }
    private void openSettingsPage() {
        SettingsPage settingsPage = new SettingsPage(new Settings());
        settingsPage.setVisible(true);
        dispose();
    }

	public Settings get_settings() {
		return _settings;
	}

	public void set_settings(Settings _settings) {
		this._settings = _settings;
	}
    

}


class StyledButtonUI extends BasicButtonUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }

    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        paintBackground(g, b, 2);
    }

    @Override
    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        if (model.isPressed()) {
            textRect.y += 2;
        }
        super.paintText(g, c, textRect, text);
    }

    @Override
    protected void installListeners(AbstractButton b) {
        super.installListeners(b);
        b.addMouseListener(new HoverListener());
    }

    private static class HoverListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            AbstractButton button = (AbstractButton) e.getSource();
            button.setBackground(Color.red); // Change background color on hover
        }

        @Override
        public void mouseExited(MouseEvent e) {
            AbstractButton button = (AbstractButton) e.getSource();
            button.setBackground(new Color(0, 153, 51)); // Reset background color on exit
        }
    }
}