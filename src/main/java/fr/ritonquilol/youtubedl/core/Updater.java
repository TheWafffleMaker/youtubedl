package fr.ritonquilol.youtubedl.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Updater {

    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    public static class UpdateListener implements ActionListener {

        private UpdateThread thread;
        private final JTextArea progress;

        UpdateListener(JTextArea progress) {
            this.thread = new UpdateThread(progress);
            this.progress = progress;
            thread.start();
        }

        public void actionPerformed(ActionEvent e) {
            thread = new UpdateThread(progress);
            thread.start();
        }


    }

    private static class UpdateThread extends Thread {

        final JTextArea progress;

        private UpdateThread(JTextArea progress) {
            this.progress = progress;
        }

        @Override
        public void run() {
            String updateCommand= "youtube-dl.exe -U --no-check-certificate";

            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", updateCommand); // Process creation
            builder.redirectErrorStream(true);

            try {

                Process process = builder.start(); // Process execution

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); // Reads the process outputs
                String line = reader.readLine();;
                while (line != null) { // Displays the process outputs
                    LOG.info(line);
                    progress.setText(progress.getText() + " " + line + "\n");
                    line = reader.readLine();
                }
                LOG.debug("End of updating process. Destroying...");
                process.destroy();
                LOG.debug("Process destroyed.");

            } catch (IOException e1) {
                LOG.error("Error while reading the update process stream.");
                e1.printStackTrace();
            }

        }
    }
}
