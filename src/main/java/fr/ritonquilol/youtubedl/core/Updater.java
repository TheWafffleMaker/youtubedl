package fr.ritonquilol.youtubedl.core;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Updater {

    public static class UpdateListener implements ActionListener {

        JTextArea progress;

        UpdateListener(JTextArea progress) {
            this.progress = progress;
        }

        public void actionPerformed(ActionEvent e) {
            String command = "youtube-dl.exe -U";

            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command); // Process creation
            builder.redirectErrorStream(true);

            try {

                Process process = builder.start(); // Process execution

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); // Reads the process outputs
                String line;
                while (true) { // Displays the process outputs
                    line = reader.readLine();
                    if (line == null) {
                        System.out.println("End of process.");
                        break;
                    }
                    System.out.println(line);
                    progress.setText(progress.getText() + " " + line+ "\n" );
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}