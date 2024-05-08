package gameUI;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen extends JFrame {
    private JProgressBar progressBar;
    private Settings _settings;
    
    public LoadingScreen(Settings s) {
        setTitle("Loading");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null); // Center the frame on the screen
        _settings = s;
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        add(progressBar, BorderLayout.CENTER);

        // Simulate loading process using a TimerTask
        Timer timer = new Timer();
        timer.schedule(new ProgressTask(), 0, 100); // Update progress every 100 milliseconds

        setVisible(true);
    }

    class ProgressTask extends TimerTask {
        private int progress = 0;
        private int currentMessageIndex = 0;

        public void run() {
            if (progress < 100) {
                // Increment progress
                progress += 5; // Increment progress by 5%
                progressBar.setValue(progress);

                // Change progress message every 25%
                if (progress % 25 == 0 && currentMessageIndex < 3) {
                    currentMessageIndex++;
                }
                String[] progressMessages = {"Loading " + progress + "%", "Still Loading " + progress + "%", "Almost There " + progress + "%", "Finishing Up " + progress + "%"};
                progressBar.setString(progressMessages[currentMessageIndex]);
            } else {
                // Loading complete, close the loading screen and start the main functionality
                dispose(); // Close the loading screen
                // Start your main functionality here
                startMainFunctionality();
                cancel(); // Stop the timer task
            }
        }
    }

    // Method to start the main functionality of your application
    private void startMainFunctionality() {
    	OuterFrame oFrame = new OuterFrame(_settings);
        dispose();
    }

}

