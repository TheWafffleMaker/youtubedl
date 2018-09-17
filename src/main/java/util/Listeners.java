package util;

import fr.ritonquilol.youtubedl.core.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listeners {

    public static class Downloader implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Container cont = ( (JButton)e.getSource() ).getParent();

            JTextArea txt = (JTextArea) cont.getComponent(1);
            String url = txt.getText();

            txt = (JTextArea) cont.getComponent(3);
            String path = txt.getText();

            if ((path.charAt(path.length()-1)) == '\\') {
                path += "%(title)s.%(ext)s";
            } else {
                path += "\\%(title)s.%(ext)s";
            }

            JCheckBox chk = (JCheckBox) cont.getComponent(4);
            boolean audio = chk.isSelected();


            Application.download(url, path, audio);
        }
    }
}
