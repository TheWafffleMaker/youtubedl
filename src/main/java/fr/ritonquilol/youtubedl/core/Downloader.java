package fr.ritonquilol.youtubedl.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Downloader {

    public static class DownloadListener implements ActionListener {

        private JTextArea progress;

        public DownloadListener(JTextArea progress) {
            this.progress = progress;
        }

        public void actionPerformed(ActionEvent e) {
            Container cont = ( (JButton)e.getSource() ).getParent();

            JTextArea txt = (JTextArea) cont.getComponent(1);
            String url = txt.getText();

            txt = (JTextArea) cont.getComponent(3);
            String path = txt.getText();

            if ((path.charAt(path.length()-1)) == '\\') {
                path += "%(title)s.%(ext)s";
            } else {
                path += "\\%(title)s.%(ext)s";
            }

            JCheckBox chk = (JCheckBox) cont.getComponent(4);
            boolean audio = chk.isSelected();


            download(url, path, audio, progress);
        }
    }

    public static void download(String url, String path, boolean audio, JTextArea progress) {

        String command;

        command = "youtube-dl.exe" + (audio ? " -x --audio-format \"mp3\" --audio-quality 0" : "") + (url.contains("&list") ? " --yes-playlist" : "") + " -o \"" + path + "\" \"" + url + "\"";

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
