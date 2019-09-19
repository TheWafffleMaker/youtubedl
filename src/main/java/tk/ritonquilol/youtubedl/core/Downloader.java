package tk.ritonquilol.youtubedl.core;

import tk.ritonquilol.youtubedl.util.ProgressManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Downloader {

    private static boolean isDownloading = false;
    private static Container cont;
    private static Process process;

    public static class DownloadListener implements ActionListener {

        final private ProgressManager progressMgr;
        private DownloadThread downloadThread;

        DownloadListener(ProgressManager progressMgr) {
            this.progressMgr = progressMgr;

        }

        public void actionPerformed(ActionEvent e) {
            cont = ( (JButton)e.getSource() ).getParent();

            JTextField txt = (JTextField) cont.getComponent(1);
            String url = txt.getText();

            if (url.length() > 0) {
                txt = (JTextField) cont.getComponent(3);
                String path = txt.getText();

                if ((path.charAt(path.length() - 1)) == '\\') {
                    path += "%(title)s.%(ext)s";
                } else {
                    path += "\\%(title)s.%(ext)s";
                }

                JCheckBox chk = (JCheckBox) cont.getComponent(4);
                boolean audio = chk.isSelected();

                chk = (JCheckBox) cont.getComponent(5);
                boolean playlist = chk.isSelected() && url.contains("&list");


                downloadThread = new DownloadThread(url, path, audio, playlist, progressMgr);
                downloadThread.start();
                isDownloading = !isDownloading;
                JButton downloadButton = ((JButton) cont.getComponent(13));
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

        DownloadThread(String url, String path, boolean audio, boolean playlist, ProgressManager progress) {
            this.url = url;
            this.audio = audio;
            this.path = path;
            this.playlist = playlist;
            this.progressMgr = progress;
        }

        @Override
        public void run() {
            String command;

                command = "youtube-dl.exe"
                        + (audio ? " -x --audio-format \"mp3\" --audio-quality 0" : "")
                        + (playlist ? " --yes-playlist" : " --no-playlist")
                        + " -i -o \"" + path + "\" \"" + url + "\"";


            System.out.println(command);


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
                                    speedUnit = substring;
                                    break;
                                case 'M':
                                    speedUnit = substring;
                                    break;
                                case 'G':
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
                        System.out.println(line);
                        progressMgr.getProcessBox().setText(progressMgr.getProcessBox().getText() + "\n" + line);
                    } else if (!downloadFinished) {
                        //long pid = process.pid();
                        //Runtime.getRuntime().exec("Taskkill /PID " + pid + " /F");
                        //process.destroy();
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
