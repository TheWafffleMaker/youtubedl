package tk.ritonquilol.youtubedl.core;

import tk.ritonquilol.youtubedl.util.*;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

class Window {

    private JFrame frame;
    private String defaultpath = System.getProperty("user.home") + "\\Downloads\\";

    Window() {
        frame = WindowFactory.createWindow(600, 400, "Youtube Downloader");

        JTextField urlArea = WindowFactory.createTextField(300, 20, 75, 55, "");
        JTextField pathArea = WindowFactory.createTextField(300, 20, 75, 80, getPathFromConfig());
        JCheckBox audioOnly = WindowFactory.createCheckBox(50, 115, "Audio only");
        JCheckBox playlist = WindowFactory.createCheckBox(150, 115, "Playlist");

        frame.add(WindowFactory.createLabel(30, 57, "URL  :"));
        frame.add(urlArea); // URL
        frame.add(WindowFactory.createLabel(30, 82, "Path :"));
        frame.add(pathArea); // PATH
        frame.add(WindowFactory.createButton(18, 18, 377, 81, "...", new PathPicker.PickerListener(frame)));
        frame.add(audioOnly);
        frame.add(playlist);
        ProgressManager progress = new ProgressManager(frame);
        frame.add(WindowFactory.createButton(120, 30, 400, 55, "Download", new Downloader.DownloadListener(progress)));
        frame.add(WindowFactory.createButton(120, 30, 40, 315, "Update", new Updater.UpdateListener(progress.getProcessBox())));
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                writeLastPath(pathArea.getText());
                System.exit(0);
            }
        });

    }

    private void writeLastPath(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\Program Files (x86)\\Youtube DL\\conf.txt"))) {
            writer.write("last : \"" + path + "\";");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPathFromConfig() {
        String path = "";
        try(BufferedReader br = new BufferedReader(new FileReader("E:\\Program Files (x86)\\Youtube DL\\conf.txt"))) {
            String line = br.readLine();
            while (line != null) {
                if (line.contains("last") && line.charAt(line.length()-1) == ';')
                    path = line.substring(8, line.length()-2);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (path.length() == 0 ? defaultpath : path);
    }
}
