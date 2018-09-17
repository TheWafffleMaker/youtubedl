package fr.ritonquilol.youtubedl.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import util.Listeners;

import javax.swing.*;

/**
 * youtube-dl.exe simplifier
 *
 * H.Bouvet 17-02-2018
 */

public class Application {

    private static JTextArea progress = WindowFactory.createTextArea(500, 150, 40, 150, "");


    public static void download(String url, String path, boolean audio) {

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

        class Download implements Runnable {

            public void run() {

            }
        }
    }

    public static void main(String[] args) {
        JFrame window = WindowFactory.createWindow(600, 400, "Youtube Downloader");

        JTextArea urlArea = WindowFactory.createTextArea(300, 20, window.getX()+75, window.getY()+55, "");
        JTextArea pathArea = WindowFactory.createTextArea(300, 20, window.getX()+75, window.getY()+80, System.getProperty("user.home") + "\\Downloads\\");
        JCheckBox audioOnly = WindowFactory.createCheckBox(50, 115, "Audio only");

        window.add(WindowFactory.createLabel(window.getX()+30, window.getY()+57, "URL  :"));
        window.add(urlArea); // URL
        window.add(WindowFactory.createLabel(window.getX()+30, window.getY()+82, "Path :"));
        window.add(pathArea); // PATH
        window.add(audioOnly);
        window.add(WindowFactory.createButton(120, 30, window.getX()+400, window.getY()+55, "Download", new Listeners.Downloader()));
        window.add(progress);
        window.setVisible(true);

    }
}
