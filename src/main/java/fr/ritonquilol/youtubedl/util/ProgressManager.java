package fr.ritonquilol.youtubedl.util;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ProgressManager {
    private JTextArea processBox;
    private JScrollPane progress;
    private JLabel speed;
    private JLabel speedUnit;
    private JLabel remainingTime;
    private JLabel downloadFinished;

    public ProgressManager(JFrame window) {
        progress  = WindowFactory.createTextAreaWithScroll(window.getWidth()-100, 150, 40, 150, "");
        processBox = (JTextArea) progress.getViewport().getComponent(0);
        ((DefaultCaret)processBox.getCaret()).setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        speed = WindowFactory.createLabel(200,315, "");
        speedUnit = WindowFactory.createLabel(230,315, "");
        remainingTime = WindowFactory.createLabel(290,315, "");
        downloadFinished = new JLabel("Download finished !");
        downloadFinished.setBounds(200,315,150,15);
        downloadFinished.setVisible(false);
        window.add(progress);
        window.add(speed);
        window.add(speedUnit);
        window.add(remainingTime);
        window.add(downloadFinished);
    }

    public JLabel getDownloadFinished() {
        return downloadFinished;
    }

    public void setProcessBox(JTextArea processBox) {
        this.processBox = processBox;
    }

    public void setProgress(JScrollPane progress) {
        this.progress = progress;
    }

    public void setSpeed(JLabel speed) {
        this.speed = speed;
    }

    public void setSpeedUnit(JLabel speedUnit) {
        this.speedUnit = speedUnit;
    }

    public void setRemainingTime(JLabel remainingTime) {
        this.remainingTime = remainingTime;
    }

    public JTextArea getProcessBox() {

        return processBox;
    }

    public JScrollPane getProgress() {
        return progress;
    }

    public JLabel getSpeed() {
        return speed;
    }

    public JLabel getSpeedUnit() {
        return speedUnit;
    }

    public JLabel getRemainingTime() {
        return remainingTime;
    }
}
