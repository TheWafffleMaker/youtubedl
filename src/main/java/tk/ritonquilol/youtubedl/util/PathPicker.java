package tk.ritonquilol.youtubedl.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PathPicker {

    public static class PickerListener implements ActionListener {

        private Component parent;
        private String path;

        public PickerListener() {
            this(null);
        }

        public PickerListener(Component parent) {
            this.parent = parent;
        }

        public void actionPerformed(ActionEvent e) {
            Container cont = ( (JButton)e.getSource() ).getParent();
            JTextField jpath = (JTextField) cont.getComponent(3);

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File(jpath.getText()));
            chooser.setDialogTitle("");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //
            // disable the "All files" option.
            //
            chooser.setAcceptAllFileFilterUsed(false);
            //
            if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
                jpath.setText(chooser.getSelectedFile().toString());

                System.out.println("getCurrentDirectory(): "
                        + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : "
                        + chooser.getSelectedFile());
            } else {
                System.out.println("No Selection ");
            }
        }
    }
}