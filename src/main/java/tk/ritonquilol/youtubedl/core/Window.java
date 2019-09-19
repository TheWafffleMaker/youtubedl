package tk.ritonquilol.youtubedl.core;

import tk.ritonquilol.youtubedl.util.*;

import javax.swing.*;

class Window {

    private JFrame frame;

    Window() {
        frame = WindowFactory.createWindow(600, 400, "Youtube Downloader");

        String defaultpath = System.getProperty("user.home") + "\\Downloads\\";

        JTextField urlArea = WindowFactory.createTextField(300, 20, 75, 55, "");
        JTextField pathArea = WindowFactory.createTextField(300, 20, 75, 80, defaultpath);
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

    }
}
