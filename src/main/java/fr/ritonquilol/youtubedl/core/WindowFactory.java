package fr.ritonquilol.youtubedl.core;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowFactory {

    public static JFrame createWindow(int width, int height, String name) {
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(width, height);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
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

    public static JTextArea createTextArea(int width, int height, int xPos, int yPos)
    {
        JTextArea txt = new JTextArea("Enter URL here...");
        txt.setBounds(xPos, yPos, width, height);
        txt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return txt;
    }

    public static JCheckBox createCheckBox(int xPos, int yPos, String text) {
        JCheckBox chk = new JCheckBox(text);
        chk.setBounds(xPos, yPos, 15, 15);
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

            boolean audio;

            JCheckBox chk = (JCheckBox) cont.getComponent(2);
            audio = chk.isSelected();


            Application.download(txt.getText(), audio);
        }
    }

}
