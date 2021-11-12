package fr.ritonquilol.youtubedl.core;

import fr.ritonquilol.youtubedl.util.ProgressManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Downloader {

    private static boolean isDownloading = false;
    private static Process process;
    private static final Logger LOG = LoggerFactory.getLogger(Downloader.class);

    public static class DownloadListener implements ActionListener {

        final private ProgressManager progressMgr;
        private DownloadThread downloadThread;
        private final Window window;

        DownloadListener(Window window, ProgressManager progressMgr) {
            this.window = window;
            this.progressMgr = progressMgr;

        }

        public void actionPerformed(ActionEvent e) {

            JTextField urlArea = ((JTextField) window.getComponentByName(Window.URL_AREA));
            String url = urlArea.getText();

            if (url.length() > 0) {
                String path = ((JTextField) window.getComponentByName(Window.PATH_AREA)).getText();

                if ((path.charAt(path.length() - 1)) == '\\') {
                    path += "%(title)s.%(ext)s";
                } else {
                    path += "\\%(title)s.%(ext)s";
                }

                boolean audio = ((JCheckBox) window.getComponentByName(Window.AUDIO_CHECKBOX)).isSelected();
                boolean playlist = ((JCheckBox) window.getComponentByName(Window.PLAYLIST_CHECKBOX)).isSelected() && url.contains("&list");

                JButton downloadButton = (JButton) window.getComponentByName(Window.DOWNLOAD);

                downloadThread = new DownloadThread(url, path, audio, playlist, progressMgr, downloadButton);
                downloadThread.start();
                isDownloading = !isDownloading;
                if (isDownloading) {
                    downloadButton.setText("Pause");
                } else {
                    downloadButton.setText("Download");
                }
            }
        }
    }

    private static class DownloadThread extends Thread {

        private final ProgressManager progressMgr;
        private final String url, path;
        private final boolean audio, playlist;
        boolean downloadFinished = false;
        private final JButton downloadButton;

        DownloadThread(String url, String path, boolean audio, boolean playlist, ProgressManager progress, JButton downloadButton) {
            this.url = url;
            this.audio = audio;
            this.path = path;
            this.playlist = playlist;
            this.progressMgr = progress;
            this.downloadButton = downloadButton;
        }

        @Override
        public void run() {
            String command;

                command = "youtube-dl.exe"
                        + (audio ? " -x --audio-format \"mp3\" --audio-quality 0" : "")
                        + (playlist ? " --yes-playlist" : " --no-playlist")
                        + " -i -o \"" + path + "\" \"" + url + "\"";


            LOG.debug(command);


            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command); // Process creation
            builder.redirectErrorStream(true);
            try {
                process = builder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); // Reads the process outputs
                String line, speed, speedUnit;
                while (true) { // Displays the process outputs
                    if (isDownloading) {
                        line = reader.readLine();
                        if (line == null) {
                            System.out.println("End of process.");
                            downloadFinished = true;
                            progressMgr.getProcessBox().setText(progressMgr.getProcessBox().getText() + "\n" + "End of process.");
                            downloadButton.setText("Download");
                            isDownloading = false;
                            printDownloadStatus();
                            break;
                        }
                        if (line.contains("/s")) {
                            downloadFinished = false;
                            speed = line.substring(line.indexOf("at") + 4, line.indexOf("/s") - 3);
                            progressMgr.getSpeed().setText(speed);

                            String substring = line.substring(line.indexOf("/s") - 3, line.indexOf("/s") + 2);
                            switch (line.charAt(line.indexOf("/s") - 3)) {
                                case 'K':
                                case 'G':
                                case 'M':
                                    speedUnit = substring;
                                    break;
                                default:
                                    speedUnit = line.substring(line.indexOf("/s") + 2);
                            }
                            progressMgr.getSpeedUnit().setText(speedUnit);

                            progressMgr.getRemainingTime().setText("~ " + line.substring(line.indexOf("ETA") + 4));

                        } else {
                            progressMgr.getSpeed().setText("");
                            progressMgr.getSpeedUnit().setText("");
                            progressMgr.getRemainingTime().setText("");
                        }
                        if (line.contains("100%"))
                            downloadFinished = true;
                        printDownloadStatus();
                        LOG.debug(line);
                        progressMgr.getProcessBox().setText(progressMgr.getProcessBox().getText() + "\n" + line);
                    } else if (!downloadFinished) {
                        progressMgr.getSpeed().setText("");
                        progressMgr.getSpeedUnit().setText("");
                        progressMgr.getRemainingTime().setText("Paused");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void printDownloadStatus() {
            progressMgr.getDownloadFinished().setVisible(downloadFinished);
            
        }
    }


}
