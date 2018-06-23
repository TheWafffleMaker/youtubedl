package fr.ritonquilol.youtubedl.core;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowFactory {

    private static final Color BG_COLOR = Color.decode("#dbdbdb");

    public static JFrame createWindow(int width, int height, String name) {
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(width, height);
        frame.getContentPane().setBackground(BG_COLOR);
        return frame;
    }

    public static JButton createButton(int width, int height, int xPos, int yPos, String text)
    {
        final JButton button = new JButton(text);
        button.setBounds(xPos, yPos, width, height);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.addActionListener(new Downloader());
        return button;
    }

    public static JTextArea createTextArea(int width, int height, int xPos, int yPos, String text)
    {
        JTextArea txt = new JTextArea(text);
        txt.setBounds(xPos, yPos, width, height);
        txt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return txt;
    }

    public static JCheckBox createCheckBox(int xPos, int yPos, String text) {
        JCheckBox chk = new JCheckBox(text);
        chk.setBackground(BG_COLOR);
        chk.setBounds(xPos, yPos, 75, 15);
        chk.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return chk;
    }

    public static JLabel createLabel(int xPos, int yPos, String text) {
        JLabel label = new JLabel(text);
        label.setBounds(xPos, yPos, 35, 15);
        return label;
    }

    private static class Downloader implements ActionListener {

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
