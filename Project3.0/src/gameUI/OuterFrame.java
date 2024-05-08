package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.StyledEditorKit.BoldAction;

import gameLogic.GameManager;
import gameLogic.GomokuBot;

public class OuterFrame extends JFrame implements ActionListener {
	static JButton playerButton;
	private JButton restart;
	private JButton back;
	private JButton saveButton;
	private BoardPanel boardPanel;
	private Timer timer;
	private static Timer turnTimer;
	private static Integer turnTimeCount;
	private Integer timeCount;
	private transient GameManager _Manager;
	private Square _currSquare;
	private static JFrame _frame;
	private JTextArea _log;
	private static Clip clip;
	private Settings _settings;
	private GomokuBot _bot;
	private boolean _turn; // true - bot | false - player

	public OuterFrame(Settings settings) {
		super("Gomoku");
		_turn = true;
		_settings = settings;
		_currSquare = new Square(-1, -1);
		timer = new Timer(1000, this);
		turnTimeCount = 30;
		turnTimer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (turnTimeCount == 0) {
					Main.togglePlayer();
					updateButton();
					JOptionPane.showMessageDialog(rootPane, "Your Turn Has Ended");
					turnTimeCount = 30;
				}
				turnTimeCount--;
				repaint();
			}
		});
		_frame = this;
		timer.start();
		turnTimer.start();

		_log = new JTextArea("Game Log: \n");
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JScrollPane scroll = new JScrollPane(_log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(300, 550));
		panel2.add(scroll);

		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel(new GridBagLayout());
		panel2.setPreferredSize(new Dimension(300, 550));
		if (boardPanel == null)
			boardPanel = new BoardPanel(this);
		_Manager = new GameManager(boardPanel);
		playerButton = new JButton();
		playerButton.setText((Main.currentPlayer == Player.BLACK ? "White " : "Black ") + "Is Playing");
		playerButton.setEnabled(false);
		restart = new JButton("Restart");
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartGame();
			}
		});
		back = new JButton("back to start");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clip.stop();
				clip.close();
				StartPageFrame st = new StartPageFrame(_settings);
				dispose();
				turnTimer.stop();
			}
		});
		JTextField _name = new JTextField(_settings.getPlayerName() + " You Are " + _settings.getPlayerColor());
		_name.setPreferredSize(new Dimension(200, 20));
		_name.setEditable(false);
		panel2.add(_name);
		timeCount = 0;
		panel1.setBackground(Color.BLACK);
		panel2.setBackground(Color.BLACK);
		panel3.setBackground(Color.BLACK);
		panel4.setBackground(Color.BLACK);
		panel1.add(playerButton);
		panel5.add(boardPanel);
		panel4.add(restart);
		panel4.add(back);
		JButton end = new JButton("End Game");
		end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endGame(_frame);
			}
		});
		panel4.add(end);

		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (boardPanel.undoLastMove()) {
					_log.append(Main.currentPlayer + " player has remove player from:" + _currSquare.get_i() + ","
							+ _currSquare.get_j() + "\n");
					Main.currentPlayer = (Main.currentPlayer == Player.BLACK ? Main.currentPlayer = Player.WHITE
							: Player.BLACK);
				}
			}
		});
		panel4.add(undoButton);

		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(panel1, BorderLayout.NORTH);
		getContentPane().add(panel2, BorderLayout.WEST);
		getContentPane().add(panel3, BorderLayout.EAST);
		getContentPane().add(panel4, BorderLayout.SOUTH);
		getContentPane().add(panel5, BorderLayout.CENTER);
		_Manager.startGame(_currSquare.get_i(), _currSquare.get_j());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		System.out.println(this._settings.toString());
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);

			// Get the control for volume
			FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

			// Set the desired volume level (between 0.0 and 1.0)
			float volume = (float) _settings.getMusicVolume() / 100;

			// Calculate the volume level in decibels
			float volumeLevel = volumeControl.getMinimum()
					+ (volumeControl.getMaximum() - volumeControl.getMinimum()) * volume;

			// Set the volume level
			volumeControl.setValue(volumeLevel);

			// Loop the audio continuously
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			ex.printStackTrace();
		}

		// Play the audio when the frame is created
		if (clip != null) {
			clip.start();
		}

		_bot = new GomokuBot(boardPanel);
		nextTurn();
	}

	public static void updateButton() {
		playerButton.setText((Main.currentPlayer == Player.BLACK ? "White " : "Black ") + "Is Playing");
	}

	private void restartGame() {
		boardPanel.clearBoard();
		Main.togglePlayer();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.white);
		g.drawString("Time Played: " + timeCount + " seconds", 50, 50);
		g.drawString("Your Turn End In: " + turnTimeCount + " seconds", 650, 50);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timeCount++;
		repaint();
	}

	public static void turnTimerRestart() {
		turnTimeCount = 30;
	}

	public static void endGame(JFrame frame) {
		frame.dispose();
		turnTimer.stop();
		clip.stop();
		clip.close();
		if (Main.GAME_STATUS == gameLogic.GameStatus.black_player_win) {
			ImageIcon icon = new ImageIcon(
					"C:\\Users\\97250\\OneDrive - ORT365Schools\\YodGimal\\Project\\Project\\Img\\black.png");
			Image img = icon.getImage();
			Image scaledImage = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			JOptionPane.showMessageDialog(null, "Black Player Wins!", "Game Ended", JOptionPane.INFORMATION_MESSAGE,
					scaledIcon);
		} else if (Main.GAME_STATUS == gameLogic.GameStatus.white_player_win) {
			ImageIcon icon = new ImageIcon(
					"C:\\Users\\97250\\OneDrive - ORT365Schools\\YodGimal\\Project\\Project\\Img\\white.png");
			Image img = icon.getImage();
			Image scaledImage = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			JOptionPane.showMessageDialog(null, "White Player Wins!", "Game Ended", JOptionPane.INFORMATION_MESSAGE,
					scaledIcon);
		} else if (Main.GAME_STATUS == gameLogic.GameStatus.draw) {
			ImageIcon icon = new ImageIcon(
					"C:\\Users\\97250\\OneDrive - ORT365Schools\\YodGimal\\Project\\Project\\Img\\draw.png");
			Image img = icon.getImage();
			Image scaledImage = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			JOptionPane.showMessageDialog(null, "It's A Draw!", "Game Ended", JOptionPane.INFORMATION_MESSAGE,
					scaledIcon);
		}
	}

	public static void endGameWrapper() {
		endGame(_frame);
	}

	public Square get_currSquare() {
		return _currSquare;
	}

	public void set_currSquare(Square _currSquare) {
		this._currSquare = _currSquare;
	}

	public GameManager get_Manager() {
		return _Manager;
	}

	public void set_Manager(GameManager _Manager) {
		this._Manager = _Manager;
	}

	public JTextArea get_log() {
		return _log;
	}

	public void set_log(JTextArea _log) {
		this._log = _log;
	}

	public void removeLastLogLine() {
		JTextArea logTextArea = _log;
		int lastNewLineIndex = logTextArea.getText().lastIndexOf("\n");
		if (lastNewLineIndex >= 0) {
			logTextArea.replaceRange("", lastNewLineIndex, logTextArea.getText().length());
		}
	}

	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	public void setBoardPanel(BoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	public Settings get_settings() {
		return _settings;
	}

	public void set_settings(Settings _settings) {
		this._settings = _settings;
	}

	public boolean get_turn() {
		return _turn;
	}

	public void set_turn(boolean turn) {
		_turn = turn;
	}

	public void nextTurn() {
	    //_turn = !_turn;
	    if (_turn) {
	        timer = new Timer(1000, new ActionListener() { 
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                _Manager.makeMove();
	                _turn = !_turn;
	                timer.stop(); 
	            }
	        });
	        timer.setRepeats(false); 
	        timer.start();
	    }
	    else
            _turn = !_turn;
	}

}
