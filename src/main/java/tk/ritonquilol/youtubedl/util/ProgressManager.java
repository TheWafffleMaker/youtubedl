package tk.ritonquilol.youtubedl.util;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ProgressManager {
    private JTextArea processBox;
    private JScrollPane progress;
    private JLabel speed;
    private JLabel speedUnit;
    private JLabel remainingTime;

    public ProgressManager(JFrame window) {
        progress  = WindowFactory.createTextAreaWithScroll(500, 150, 40, 150, "");
        processBox = (JTextArea) progress.getViewport().getComponent(0);
        ((DefaultCaret)processBox.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        speed = WindowFactory.createLabel(200,315, "");
        speedUnit = WindowFactory.createLabel(250,315, "");
        remainingTime = WindowFactory.createLabel(290,315, "");
        window.add(progress);
        window.add(speed);
        window.add(speedUnit);
        window.add(remainingTime);
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
