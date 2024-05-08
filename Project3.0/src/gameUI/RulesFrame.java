package gameUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class RulesFrame extends JFrame {
    private JButton back;
    private Settings _settings;

    public RulesFrame(Settings s) {
        super("Gomoku Rules");
        _settings = s;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Create a JEditorPane to display HTML content
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);

        // Load the HTML content
        String htmlContent = "<!DOCTYPE html>"+
                "<html lang=\"en\">"+
                "<head>"+
                "    <meta charset=\"UTF-8\">"+
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
                "    <title>Gomoku Rules</title>"+
                "    <style>"+
                "        body {"+
                "            font-family: Arial, sans-serif;"+
                "            margin: 20px;"+
                "            background-image: url('/img/image.jpg');"+
                "        }"+
                ""+
                "        h1 {"+
                "            color: #333;"+
                "        }"+
                ""+
                "        p {"+
                "            line-height: 1.6;"+
                "            color: #555;"+
                "        }"+
                "    </style>"+
                "</head>"+
                "<body>"+
                ""+
                "    <h1>Gomoku Rules</h1>"+
                ""+
                "    <p>Gomoku, also known as Five in a Row, is a classic board game played on a grid typically with a 15x15 board. The game is usually played by two players, one using black stones/pieces, and the other using white stones/pieces. The objective of Gomoku is to be the first player to achieve an unbroken row of five stones horizontally, vertically, or diagonally on the game board.</p>"+
                ""+
                "    <h2>Basic Rules:</h2>"+
                "    <ol>"+
                "        <li>Game Setup:"+
                "            <ul>"+
                "                <li>The game is played on a square board with a grid of intersections. A 15x15 board is the most common, but other sizes can be used.</li>"+
                "                <li>Two players take turns placing their stones on the intersections of the board.</li>"+
                "            </ul>"+
                "        </li>"+
                "        <li>Gameplay:"+
                "            <ul>"+
                "                <li>Players take turns placing one of their stones on an empty intersection.</li>"+
                "                <li>The player using black stones typically makes the first move.</li>"+
                "            </ul>"+
                "        </li>"+
                "        <li>Objective:"+
                "            <ul>"+
                "                <li>The primary goal is to create an unbroken row of five stones of your color either horizontally, vertically, or diagonally.</li>"+
                "            </ul>"+
                "        </li>"+
                "        <li>Winning:"+
                "            <ul>"+
                "                <li>The first player to achieve an uninterrupted row of five stones wins the game.</li>"+
                "                <li>The row of five can be vertical, horizontal, or diagonal.</li>"+
                "            </ul>"+
                "        </li>"+
                "        <li>Draw (Tie):"+
                "            <ul>"+
                "                <li>If the entire board is filled, and neither player has achieved a row of five, the game is a draw.</li>"+
                "            </ul>"+
                "        </li>"+
                "    </ol>"+
                ""+
                "</body>"+
                "</html>";



        editorPane.setText(htmlContent);

        // Add the JEditorPane to a JScrollPane for scrolling if needed
        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);

        // Create a button to go back to the start page
        back = new JButton("Back to Start");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartPageFrame st = new StartPageFrame(_settings);
                dispose();
            }
        });


        // Add scroll pane and back button to the background label
        add(scrollPane, BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);

        // Set frame properties
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
}

