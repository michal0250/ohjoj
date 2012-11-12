package com.example.lowpricesapp.panel;

import com.example.lowpricesapp.MyOwnLayout;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Service
public class MainPanel extends MyOwnLayout {

    private JLabel mainMessage;
    private JButton addNewProduct;
    private JButton viewAllProducts;
    private JButton settings;
    private JButton closeApp;
    private static Logger logger = Logger.getLogger(MainPanel.class);

    @Autowired
    private AddProductPanel addPanel;

    @Autowired
    private ViewAllProductsPanel viewAllProductsPanel;

    public MainPanel() {
    }

    @PostConstruct
    public void init() {
        this.setLayout(null);
        this.setSize(MainWindow.WITH, MainWindow.HEIGHT);
        mainMessage = new JLabel(
                "Witaj w super app .... klikaj i wiedz że coś się dzieje.");

        mainMessage.setBounds(setCenterXPositionInWindow(20,
                mainMessage.getPreferredSize()));
        add(mainMessage);

        addNewProduct = new JButton("Add new product");
        addNewProduct.setBounds(setCenterXPositionInWindow(200,
                addNewProduct.getPreferredSize()));
        add(addNewProduct);
        addNewProduct.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame root = (JFrame) SwingUtilities.getRoot(MainPanel.this);
                root.remove(MainPanel.this);
                root.add(addPanel);
                root.validate();
                root.repaint();
            }

        });

        viewAllProducts = new JButton("View all products");
        viewAllProducts.setBounds(setCenterXPositionInWindow(250,
                viewAllProducts.getPreferredSize()));

        add(viewAllProducts);
        viewAllProducts.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame root = (JFrame) SwingUtilities.getRoot(MainPanel.this);
                root.remove(MainPanel.this);
                root.add(viewAllProductsPanel);
                root.validate();
                root.repaint();

            }

        });

        settings = new JButton("Settings");
        settings.setEnabled(false);
        settings.setBounds(setCenterXPositionInWindow(400,
                settings.getPreferredSize()));
        add(settings);

        closeApp = new JButton("Close app");
        closeApp.setBounds(setCenterXPositionInWindow(450,
                closeApp.getPreferredSize()));
        add(closeApp);
        closeApp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("=========================================== END");
                logger.warn("=========================================== END");
                System.exit(0);
            }
        });
    }

    public void setAddPanel(AddProductPanel addPanel) {
        this.addPanel = addPanel;
    }

    public void setViewAllProductsPanel(
            ViewAllProductsPanel viewAllProductsPanel) {
        this.viewAllProductsPanel = viewAllProductsPanel;
    }

}
