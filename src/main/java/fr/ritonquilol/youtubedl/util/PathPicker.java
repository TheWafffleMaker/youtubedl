package fr.ritonquilol.youtubedl.util;

import fr.ritonquilol.youtubedl.core.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.*;


public class PathPicker {

    public static class PickerListener implements ActionListener {

        private Window window;
        private String path;

        private static Logger LOG = LoggerFactory.getLogger(PathPicker.class);

        public PickerListener() {
            this(null);
        }

        public PickerListener(Window window) {
            this.window = window;
        }

        public void actionPerformed(ActionEvent e) {
            JTextField jpath = (JTextField) window.getComponentByName(Window.PATH_AREA);

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File(jpath.getText()));
            chooser.setDialogTitle("");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //
            // disable the "All files" option.
            //
            chooser.setAcceptAllFileFilterUsed(false);
            //
            if (chooser.showOpenDialog(window.getFrame()) == JFileChooser.APPROVE_OPTION) {
                jpath.setText(chooser.getSelectedFile().toString());

                LOG.debug("getCurrentDirectory(): "
                        + chooser.getCurrentDirectory());
                LOG.debug("getSelectedFile() : "
                        + chooser.getSelectedFile());
            } else {
                LOG.info("No directory selected");
            }
        }
    }
}