package gameUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import gameLogic.Soldier;

public class BoardPanel extends JPanel {
    private Square[][] panels;
    public static final int NUMBER_OF_ROWS =15;
    private OuterFrame _oFrame;
    
    public BoardPanel(OuterFrame oFrame) {
        super();
        _oFrame = oFrame;
        panels = new Square[NUMBER_OF_ROWS][NUMBER_OF_ROWS];
        setLayout(new GridLayout(NUMBER_OF_ROWS, NUMBER_OF_ROWS));
        setBackground(new Color(161, 121, 51));
        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < panels[i].length; j++) {
                panels[i][j] = new Square(i,j);
                panels[i][j].setoFrame(oFrame);
                add(panels[i][j]);
            }
        }
        setPreferredSize(new Dimension(600, 600));
    }
    public void clearBoard() {
        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < panels[i].length; j++) {
                panels[i][j].clearSquare();
            }
        }
    }
	public Square[][] getPanels() {
		return panels;
	}
	public Square getSquare(int i,int j) {
		return panels[i][j];
	}

    public boolean undoLastMove() {
        // Find the last move and clear the square if a soldier is present
        if (_oFrame.get_currSquare() != null) {
            int i = _oFrame.get_currSquare().get_i();
            int j = _oFrame.get_currSquare().get_j();
            if (i >= 0 && i < NUMBER_OF_ROWS && j >= 0 && j < NUMBER_OF_ROWS) {
                if (panels[i][j].isHasSoldier()) {
                    panels[i][j].clearSquare();
                    return true;
                }
            }
        }
		return false;
    }
	public void placeMove(int row, int col, Soldier black) {
		panels[row][col].botPlacePlayer(row,col);
		
	}



    
}
