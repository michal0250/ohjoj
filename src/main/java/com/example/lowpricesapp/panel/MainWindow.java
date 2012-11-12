package com.example.lowpricesapp.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * Javadoc
 *
 * @author Mic
 */
@Service
public class MainWindow extends JFrame {
    public static final int WITH = 1200; // 1067 4 1280- 16
    public static final int HEIGHT = 800; // 800 3 800 10

    @Autowired
    MainPanel mainPanel;

    public MainWindow() {

    }

    @PostConstruct
    public void init() {
        setTitle("  Engineering it's like math, but LOUDER ");
        setSize(WITH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainPanel.hashCode();
        getContentPane().add(mainPanel);
    }
}
