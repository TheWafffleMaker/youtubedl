package fr.ritonquilol.youtubedl.core;

import javax.swing.*;
import java.awt.*;
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

    public static JButton createButton(int width, int height, int xPos, int yPos, String text, ActionListener listener)
    {
        final JButton button = new JButton(text);
        button.setBounds(xPos, yPos, width, height);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.addActionListener(listener);
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
        chk.setBounds(xPos, yPos, 100, 15);
        chk.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return chk;
    }

    public static JLabel createLabel(int xPos, int yPos, String text) {
        JLabel label = new JLabel(text);
        label.setBounds(xPos, yPos, 35, 15);
        return label;
    }

}
