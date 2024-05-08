package gameUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Square extends JPanel {
	private Player player;
	private boolean hasSoldier;
	private int _i, _j;
	private OuterFrame oFrame;
	private Square _currSquare;

	public Square(int i, int j) {
		super();
		_i = i;
		_j = j;
		_currSquare = this;
		player = Player.EMPTY;
		Color c = new Color(154, 115, 26);
		setBackground(c);
		hasSoldier = false;
		setPreferredSize(new Dimension(30, 30));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				placePlayer();

			}
		});
	}

	public void placePlayer() {
		if (!oFrame.get_turn()) {
			if (!hasSoldier) {
				System.out.println(oFrame.get_turn()?"bot":"player");
				hasSoldier = true;
				player = Main.togglePlayer();
				oFrame.set_currSquare(_currSquare);
				oFrame.get_log().append(Main.currentPlayer + " player has put player in:" + _currSquare.get_i() + ","
						+ _currSquare.get_j() + "\n");
				oFrame.get_Manager().startGame(_currSquare.get_i(), _currSquare.get_j());
				repaint();
				oFrame.nextTurn();

			}
		}
		else {
			if (!hasSoldier) {
				System.out.println(oFrame.get_turn()?"bot":"player");
				hasSoldier = true;
				player = Main.togglePlayer();
				oFrame.set_currSquare(_currSquare);
				oFrame.get_log().append(Main.currentPlayer + " Bot has put player in:" + _currSquare.get_i() + ","
						+ _currSquare.get_j() + "\n");
				oFrame.get_Manager().startGame(_currSquare.get_i(), _currSquare.get_j());
				repaint();
				oFrame.nextTurn();
			}
		}
	}

	public void botPlacePlayer(int row, int col) {
		if (!hasSoldier) {
			System.out.println(oFrame.get_turn()?"bot":"player");
			hasSoldier = true;
			player = Main.togglePlayer();
			oFrame.set_currSquare(_currSquare);
			oFrame.get_log().append(Main.currentPlayer + " Bot has put player in:" + _currSquare.get_i() + ","
					+ _currSquare.get_j() + "\n");
			oFrame.get_Manager().startGame(_currSquare.get_i(), _currSquare.get_j());
			repaint();
			oFrame.nextTurn();

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(0, 20, 40, 20);
		g.drawLine(20, 0, 20, 40);
		if (hasSoldier) {
			g.setColor(player == Player.BLACK ? Color.black : Color.white);
			g.fillOval(10, 10, 20, 20);
		}
	}

	public void clearSquare() {
		hasSoldier = false;
		player = null;
		repaint();
	}

	public int get_i() {
		return _i;
	}

	public void set_i(int _i) {
		this._i = _i;
	}

	public int get_j() {
		return _j;
	}

	public void set_j(int _j) {
		this._j = _j;
	}

	public OuterFrame getoFrame() {
		return oFrame;
	}

	public void setoFrame(OuterFrame oFrame) {
		this.oFrame = oFrame;
	}

	public boolean isHasSoldier() {
		return hasSoldier;
	}

	public void setHasSoldier(boolean hasSoldier) {
		this.hasSoldier = hasSoldier;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
