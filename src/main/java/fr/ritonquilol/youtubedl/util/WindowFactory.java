package fr.ritonquilol.youtubedl.util;

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
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        return frame;
    }

    public static JButton createButton(int width, int height, int xPos, int yPos, String text, ActionListener listener, String name)
    {
        final JButton button = new JButton(text);
        button.setBounds(xPos, yPos, width, height);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.addActionListener(listener);
        button.setFocusPainted(false);
        button.setName(name);
        return button;
    }

    public static JTextField createTextField(int width, int height, int xPos, int yPos, String text) {
        JTextField txt = new JTextField(text);
        txt.setBounds(xPos, yPos, width, height);
        txt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return txt;
    }

    public static JScrollPane createTextAreaWithScroll(int width, int height, int xPos, int yPos, String text)
    {
        JScrollPane scroll = new JScrollPane(new JTextArea(text));
        scroll.setBounds(xPos, yPos, width, height);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scroll;
    }

    public static JCheckBox createCheckBox(int xPos, int yPos, String text) {
        JCheckBox chk = new JCheckBox(text);
        chk.setBackground(BG_COLOR);
        chk.setBounds(xPos, yPos, 100, 15);
        chk.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        chk.setFocusPainted(false);
        return chk;
    }

    public static JLabel createLabel(int xPos, int yPos, String text) {
        JLabel label = new JLabel(text);
        label.setBounds(xPos, yPos, 105, 15);
        return label;
    }

    public static JLabel createLabel(int xPos, int yPos, String text, String name) {
        JLabel label = createLabel(xPos, yPos, text);
        label.setName(name);
        return label;
    }

}
