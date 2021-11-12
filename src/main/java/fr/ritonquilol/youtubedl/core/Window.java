package fr.ritonquilol.youtubedl.core;

import fr.ritonquilol.youtubedl.util.ProgressManager;
import fr.ritonquilol.youtubedl.util.WindowFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.ritonquilol.youtubedl.util.PathPicker;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;

public class Window {

    public static final String AUDIO_ONLY_TEXT = "Audio only";
    public static final String PLAYLIST_TEXT = "Playlist";
    public static final String URL_TEXT = "URL  :";
    public static final String PATH_TEXT = "Path :";
    public static final String TITLE = "Youtube Downloader";
    public static final String PATH_PICKER_LABEL = "...";
    public static final String DOWNLOAD_LABEL = "Download";
    public static final String UPDATE_LABEL = "Update";
    public static final String URL_LABEL = "urlLabel";
    public static final String PATH_LABEL = "pathLabel";
    public static final String URL_AREA = "urlArea";
    public static final String PATH_AREA = "pathArea";
    public static final String AUDIO_CHECKBOX = "audioCheckbox";
    public static final String PLAYLIST_CHECKBOX = "playlistCheckbox";
    public static final String VERSION = "version";
    public static final String PATH_PICKER = "pathPicker";
    public static final String DOWNLOAD = "download";
    public static final String UPDATE = "update";
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 700;
    private static final Logger LOG = LoggerFactory.getLogger(Window.class);
    private final JFrame frame;
    private HashMap<String, Component> componentMap;

    Window() {
        frame = WindowFactory.createWindow(WINDOW_WIDTH, WINDOW_HEIGHT, TITLE);

        JTextField urlArea = WindowFactory.createTextField(300, 20, 75, 55, "");
        JTextField pathArea = WindowFactory.createTextField(300, 20, 75, 80, getPathFromConfig());
        JCheckBox audioOnly = WindowFactory.createCheckBox(50, 115, AUDIO_ONLY_TEXT);
        JCheckBox playlist = WindowFactory.createCheckBox(150, 115, PLAYLIST_TEXT);

        urlArea.setName(URL_AREA);
        pathArea.setName(PATH_AREA);
        audioOnly.setName(AUDIO_CHECKBOX);
        playlist.setName(PLAYLIST_CHECKBOX);

        frame.add(WindowFactory.createLabel(30, 57, URL_TEXT, URL_LABEL));
        frame.add(urlArea); // URL
        frame.add(WindowFactory.createLabel(30, 82, PATH_TEXT, PATH_LABEL));
        frame.add(pathArea); // PATH
        frame.add(audioOnly);
        frame.add(playlist);
        frame.add(WindowFactory.createLabel(WINDOW_WIDTH - 55, WINDOW_HEIGHT - 60, "v" + Application.__CURRENT_VERSION, VERSION));

        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                writeLastPath(pathArea.getText());
                System.exit(0);
            }
        });

        createComponentMap();
    }

    public void init() {
        Component pathArea = getComponentByName(PATH_AREA);
        // Folder picker button
        frame.add(
                WindowFactory.createButton(
                        18, 18,
                        pathArea.getX() + pathArea.getWidth() + 2,
                        pathArea.getY() + 1,
                        PATH_PICKER_LABEL, new PathPicker.PickerListener(this), PATH_PICKER));

        ProgressManager progress = new ProgressManager(frame);

        // Download button
        frame.add(
                WindowFactory.createButton(
                        150, 45,
                        WINDOW_WIDTH - 200, 55,
                        DOWNLOAD_LABEL.toUpperCase(), new Downloader.DownloadListener(this, progress), DOWNLOAD));
        // Update button
        frame.add(
                WindowFactory.createButton(
                        120, 30,
                        40, WINDOW_HEIGHT - 85,
                        UPDATE_LABEL, new Updater.UpdateListener(progress.getProcessBox()), UPDATE));

        // Refresh everything
        frame.repaint();
        createComponentMap();
    }

    private void writeLastPath(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("conf.txt"))) {
            writer.write("last : \"" + path + "\";");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPathFromConfig() {
        String path = "";
        try (BufferedReader br = new BufferedReader(new FileReader("conf.txt"))) {
            String line = br.readLine();
            while (line != null) {
                if (line.contains("last") && line.charAt(line.length() - 1) == ';')
                    path = line.substring(8, line.length() - 2);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (path.length() == 0 ? System.getProperty("user.home") + "\\Downloads\\" : path);
    }

    private void createComponentMap() {
        componentMap = new HashMap<>();
        Component[] components = frame.getRootPane().getContentPane().getComponents();
        for (Component component : components) {
            componentMap.put(component.getName(), component);
        }
        LOG.info("Refreshing componentMap (" + componentMap.size() + " items)");
    }

    public Component getComponentByName(String name) {
        return componentMap.getOrDefault(name, null);
    }

    public JFrame getFrame() {
        return frame;
    }
}
