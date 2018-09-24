package fr.ritonquilol.youtubedl.core;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * youtube-dl.exe simplifier
 *
 * H.Bouvet 17-02-2018
 */

public class Application {


    public static void main(String[] args) {
        JFrame window = WindowFactory.createWindow(600, 400, "Youtube Downloader");

        JTextField urlArea = WindowFactory.createTextField(300, 20, 75, 55, "");
        JTextField pathArea = WindowFactory.createTextField(300, 20, 75, 80, System.getProperty("user.home") + "\\Downloads\\");
        JCheckBox audioOnly = WindowFactory.createCheckBox(50, 115, "Audio only");
        JCheckBox playlist = WindowFactory.createCheckBox(150, 115, "Playlist");
        JScrollPane progressScroll = WindowFactory.createTextAreaWithScroll(500, 150, 40, 150, "");
        JTextArea progress = (JTextArea) progressScroll.getViewport().getComponent(0);
        ((DefaultCaret)progress.getCaret()).setUpdatePolicy(DefaultCaret.OUT_BOTTOM);

        window.add(WindowFactory.createLabel(30, 57, "URL  :"));
        window.add(urlArea); // URL
        window.add(WindowFactory.createLabel(30, 82, "Path :"));
        window.add(pathArea); // PATH
        window.add(audioOnly);
        window.add(playlist);
        window.add(progressScroll);
        window.add(WindowFactory.createButton(120, 30, 400, 55, "Download", new Downloader.DownloadListener((JTextArea) progress)));
        window.add(WindowFactory.createButton(120, 30, 40, 315, "Update", new Updater.UpdateListener(progress)));
        window.setVisible(true);

    }
}
