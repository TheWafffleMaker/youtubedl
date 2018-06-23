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

    public static void download(String url, boolean audio) {

        String command;

        command = "youtube-dl.exe" + (audio ? " -x --audio-format \"mp3\" --audio-quality 0" : "") + " -o \"c:\\Users\\Henri\\Downloads\\%(title)s.%(ext)s\" " + url;

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
        window.add(WindowFactory.createButton(120, 30, window.getX()+400, window.getY()+50, "Download"));
        window.add(WindowFactory.createTextArea(300, 20, window.getX()+50, window.getY()+55));
        window.add(WindowFactory.createCheckBox(50, 90, "Audio"));
        window.add(WindowFactory.createLabel(70, 90, "audio"));
        window.setVisible(true);

    }
}
