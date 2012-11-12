package com.example.lowpricesapp.panel;

import com.example.lowpricesapp.MyOwnLayout;
import com.example.lowpricesapp.service.ParseSportDirectPageService;
import com.example.lowpricesapp.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Controller
public class AddProductPanel extends MyOwnLayout {
    private JButton cancel;
    private JButton save;
    private JButton load;
    private JTextArea webAdress;
    private static Logger logger = Logger.getLogger(AddProductPanel.class);

    @Autowired
    private MainPanel mainPanel;
    @Autowired
    private ProductPanel productPanel;
    @Autowired
    private ProductService productService;
    @Autowired
    private ParseSportDirectPageService parseWebPageService;

    public AddProductPanel() {
    }

    @PostConstruct
    public void init() {
        logger.info("AddProductPanel constructor");
        setLayout(null);

//        mock
        webAdress = new JTextArea("http://www.sportsdirect.com/campri-thermal-top-mens-402067", 3, 30);
//        webAdress = new JTextArea("", 3, 30);


        webAdress.setLineWrap(true);
        webAdress.setBounds(setCenterXPositionInWindow(30,
                webAdress.getPreferredSize()));
        add(webAdress);

        load = new JButton("Load");
        load.setBounds(setLeftUpperCorner(780, 14, load.getPreferredSize()));
        add(load);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ( new Thread() {
                    public void run() {
                        getDataAboutProduct();
                    }
                }
                ).start();
            }
        });


        save = new JButton("Save");
        save.setBounds(setCenterXPositionInWindow(740, save.getPreferredSize()));
        add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ( new Thread() {

                    public void run() {
                        saveAction();
                    }
                }
                ).start();

            }
        });

        cancel = new JButton("Back");
        cancel.setBounds(setLeftUpperCorner(300, 727, cancel.getPreferredSize()));
        add(cancel);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToMainPanel();
            }
        });
        logger.info("End of AddProductPanel constructor");
    }

    private void saveAction() {
        String result;
        try {
            result = productService.saveNewProduct(
                    productPanel.getAllTextField(),
                    productPanel.getImage());

            JOptionPane.showConfirmDialog(this, result,
                    result, JOptionPane.CLOSED_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            goToMainPanel();
        } catch (Exception e1) {
            e1.printStackTrace();

            logger.warn("some exceptions:" + e1.getMessage());
            JOptionPane
                    .showConfirmDialog(
                            this,
                            "oh yoy, some exceptions, look to warning log file",
                            "something goes wrong: ",
                            JOptionPane.CLOSED_OPTION,
                            JOptionPane.ERROR_MESSAGE);
        }
    }

    public void goToMainPanel() {

        clearPanel();

        JFrame root = (JFrame) SwingUtilities.getRoot(AddProductPanel.this);
        root.remove(AddProductPanel.this);
        root.add(mainPanel);
        root.validate();
        root.repaint();
    }

    private void clearPanel() {

        webAdress.setText("");
        productPanel.clearAll();
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

    }

    public void setProductParseWebPage(ParseSportDirectPageService parseWebPageService) {
        this.parseWebPageService = parseWebPageService;
    }

    private void getDataAboutProduct() {
        try {
            parseWebPageService.setURL(webAdress.getText());
            productPanel.setAllTextField(parseWebPageService.getAllProductData());
        } catch (Exception e1) {
            e1.printStackTrace();
            logger.warn("ex from parsing www page, some exceptions: "
                    + e1.getMessage());

            JOptionPane
                    .showConfirmDialog(
                            this,
                            e1.getMessage(),
                            "something goes wrong: ",
                            JOptionPane.CLOSED_OPTION,
                            JOptionPane.ERROR_MESSAGE);
        }
        productPanel.setBounds(300, 100, 600, 600);
        add(productPanel);
        validate();
        repaint();
    }
}
