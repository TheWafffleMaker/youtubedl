package fr.ritonquilol.youtubedl.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.slf4j.*;

import javax.swing.*;

/**
 * youtube-dl.exe simplifier
 *
 * H.Bouvet 17-02-2018
 */

public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void download(String url, String path, boolean audio) {

        String command;

        command = "youtube-dl.exe" + (audio ? " -x --audio-format \"mp3\" --audio-quality 0" : "") + " -o " + path + " " + url;

        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command); // Process creation
        builder.redirectErrorStream(true);

        try {
            Process process = builder.start(); // Process execution
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); // Reads the process outputs
            String line;
            while (true) { // Displays the process outputs
                line = reader.readLine();
                if (line == null) {
                    log.info("End of process.");
                    break;
                }
                log.info(line);
            }

        } catch (IOException e) {
            log.trace("Process couldn't be loaded.", e);
        }
    }

    public static void main(String[] args) {
        JFrame window = WindowFactory.createWindow(600, 400, "Youtube Downloader");
        window.add(WindowFactory.createLabel(window.getX()+30, window.getY()+57, "URL  :"));
        window.add(WindowFactory.createTextArea(300, 20, window.getX()+75, window.getY()+55, "")); // URL
        window.add(WindowFactory.createLabel(window.getX()+30, window.getY()+82, "Path :"));
        window.add(WindowFactory.createTextArea(300, 20, window.getX()+75, window.getY()+80, "")); // PATH
        window.add(WindowFactory.createCheckBox(50, 115, "Audio"));
        window.add(WindowFactory.createButton(120, 30, window.getX()+400, window.getY()+55, "Download"));
        window.setVisible(true);

    }
}
