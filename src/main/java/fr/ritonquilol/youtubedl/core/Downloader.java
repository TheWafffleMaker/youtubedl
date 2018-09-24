package fr.ritonquilol.youtubedl.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Downloader {

    public static class DownloadListener implements ActionListener {

        final private JTextArea progress;
        private DownloadThread thread;

        DownloadListener(JTextArea progress) {
            this.progress = progress;
        }

        public void actionPerformed(ActionEvent e) {
            Container cont = ( (JButton)e.getSource() ).getParent();

            JTextField txt = (JTextField) cont.getComponent(1);
            String url = txt.getText();

            txt = (JTextField) cont.getComponent(3);
            String path = txt.getText();

            if ((path.charAt(path.length()-1)) == '\\') {
                path += "%(title)s.%(ext)s";
            } else {
                path += "\\%(title)s.%(ext)s";
            }

            JCheckBox chk = (JCheckBox) cont.getComponent(4);
            boolean audio = chk.isSelected();

            chk = (JCheckBox) cont.getComponent(5);
            boolean playlist = chk.isSelected() && url.contains("&list");


            thread = new DownloadThread(url, path, audio, playlist, progress);
            thread.start();
        }
    }

    private static class DownloadThread extends Thread {

        private final JTextArea progress;
        private final String url, path;
        private final boolean audio, playlist;

        DownloadThread(String url, String path, boolean audio, boolean playlist, JTextArea progress) {
            this.url = url;
            this.audio = audio;
            this.path = path;
            this.playlist = playlist;
            this.progress = progress;
        }

        @Override
        public void run() {
            String command = "youtube-dl.exe" + (audio ? " -x --audio-format \"mp3\" --audio-quality 0" : "") + (playlist  ? " --yes-playlist" : "") + " -o \"" + path + "\" \"" + url + "\"";

            System.out.println(command);

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
                        progress.setText("End of process.");
                        break;
                    }
                    System.out.println(line);
                    progress.setText(progress.getText() + "\n" + line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
