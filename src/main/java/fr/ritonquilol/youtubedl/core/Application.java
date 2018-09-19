package fr.ritonquilol.youtubedl.core;

import javax.swing.*;

/**
 * youtube-dl.exe simplifier
 *
 * H.Bouvet 17-02-2018
 */

public class Application {


    public static void main(String[] args) {
        JFrame window = WindowFactory.createWindow(600, 400, "Youtube Downloader");

        JTextArea urlArea = WindowFactory.createTextArea(300, 20, window.getX()+75, window.getY()+55, "");
        JTextArea pathArea = WindowFactory.createTextArea(300, 20, window.getX()+75, window.getY()+80, System.getProperty("user.home") + "\\Downloads\\");
        JCheckBox audioOnly = WindowFactory.createCheckBox(50, 115, "Audio only");
        JTextArea progress = WindowFactory.createTextArea(500, 150, 40, 150, "");
        progress.setLineWrap(true);
        progress.setEditable(false);
        progress.setVisible(true);

        JScrollPane scroll = new JScrollPane(progress);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        window.add(WindowFactory.createLabel(window.getX()+30, window.getY()+57, "URL  :"));
        window.add(urlArea); // URL
        window.add(WindowFactory.createLabel(window.getX()+30, window.getY()+82, "Path :"));
        window.add(pathArea); // PATH
        window.add(audioOnly);
        //window.add(progress);
        window.add(scroll);
        window.add(WindowFactory.createButton(120, 30, window.getX()+400, window.getY()+55, "Download", new Downloader.DownloadListener(progress)));
        window.setVisible(true);

    }
}
