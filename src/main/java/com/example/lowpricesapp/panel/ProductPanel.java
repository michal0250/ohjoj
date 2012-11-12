package com.example.lowpricesapp.panel;

import com.example.lowpricesapp.MyOwnLayout;
import com.example.lowpricesapp.Product;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ProductPanel extends MyOwnLayout {
    private static Logger logger = Logger.getLogger(MainPanel.class);

    int rozporka = 20;
    private BufferedImage bImage;

    JLabel id = new JLabel("id");
    JTextField idData = new JTextField("", 4);

    JLabel url = new JLabel("url");
    JTextField urlData = new JTextField("", 42);

    JLabel price = new JLabel("price");
    JTextField priceData = new JTextField("", 5);

    JLabel date = new JLabel("date");
    JTextField dateData = new JTextField("", 10);

    JLabel actualPrice = new JLabel("actualPrice");
    JTextField actualPriceData = new JTextField("", 5);

    JLabel actualDate = new JLabel("actualDate");
    JTextField actualDateData = new JTextField("", 10);

    JLabel productName = new JLabel("productName");
    JTextField productNameData = new JTextField("", 44);

    JLabel description = new JLabel("desc");
    JTextArea descriptionData = new JTextArea("", 10, 53);

    JScrollPane jscroll = new JScrollPane();

    public ProductPanel() {
    }

    @PostConstruct
    public void init() {
        logger.info("class: productPanel, constructor start");
        // setSize(600, 600);
        setLayout(null);

        id.setBounds(setLeftUpperCorner(5, 5, id.getPreferredSize()));
        add(id);
        idData.setEditable(false);
        idData.setBounds(setLeftUpperCorner(25, 5, idData.getPreferredSize()));
        add(idData);
        url.setBounds(setLeftUpperCorner(100, 5, url.getPreferredSize()));
        add(url);
        urlData.setBounds(setLeftUpperCorner(120, 5, urlData.getPreferredSize()));
        add(urlData);

        price.setBounds(setLeftUpperCorner(5, 35, price.getPreferredSize()));
        add(price);
        priceData.setBounds(setLeftUpperCorner(95, 35,
                priceData.getPreferredSize()));
        add(priceData);
        date.setBounds(setLeftUpperCorner(300, 35, date.getPreferredSize()));
        add(date);
        dateData.setBounds(setLeftUpperCorner(370, 35,
                dateData.getPreferredSize()));
        add(dateData);

        actualPrice.setBounds(setLeftUpperCorner(5, 65,
                actualPrice.getPreferredSize()));
        add(actualPrice);
        actualPriceData.setBounds(setLeftUpperCorner(95, 65,
                actualPriceData.getPreferredSize()));
        add(actualPriceData);
        actualDate.setBounds(setLeftUpperCorner(300, 65,
                actualDate.getPreferredSize()));
        add(actualDate);
        actualDateData.setBounds(setLeftUpperCorner(370, 65,
                actualDateData.getPreferredSize()));
        add(actualDateData);

        productName.setBounds(setLeftUpperCorner(5, 95,
                productName.getPreferredSize()));
        add(productName);
        productNameData.setBounds(setLeftUpperCorner(95, 95,
                productNameData.getPreferredSize()));
        add(productNameData);

        description.setBounds(setLeftUpperCorner(5, 125,
                description.getPreferredSize()));
        add(description);

        descriptionData.setLineWrap(true);
        descriptionData.setWrapStyleWord(true);
        descriptionData.setBounds(setLeftUpperCorner(5, 145,
                descriptionData.getPreferredSize()));
        add(descriptionData);

        logger.info("class: productPanel, constructor end");
    }

    public void setPrice(Double price) {
        this.priceData.setText("" + price);
    }

    public void setActualPrice(double actualPrice) {
        this.actualPriceData.setText("" + actualPrice);
    }

    public void setProductDescribe(String productDescribe) {
        this.descriptionData.setText("" + productDescribe);
    }

    public void setDate(String date) {
        this.dateData.setText("" + date);
    }

    public void setActualDate(String actualDate) {
        this.actualDateData.setText("" + actualDate);
    }

    public void setProductName(String productName) {
        this.productNameData.setText("" + productName);
    }

    public void setUrl(String url) {
        this.urlData.setText("" + url);
    }

    public String getPrice() {

        return priceData.getText();
    }

    public String getId() {

        return idData.getText();
    }

    public String getActualPrice() {
        return actualPriceData.getText();
    }

    public String getUrl() {
        return urlData.getText();
    }

    public String getDate() {
        return dateData.getText();
    }

    public String getActualDate() {
        return actualDateData.getText();
    }

    public String getProductName() {
        return productNameData.getText();
    }

    public String getProductDescribe() {
        return descriptionData.getText();
    }

    public void clearAll() {

        idData.setText("");
        urlData.setText("");
        priceData.setText("");
        dateData.setText("");
        actualPriceData.setText("");
        actualDateData.setText("");
        productNameData.setText("");
        descriptionData.setText("");
        bImage = null;
    }

    public void setAllTextField(String[] parsedData) {

        idData.setText("");
        dateData.setText("");
        urlData.setText(parsedData[0]);
        priceData.setText(parsedData[1]);
        actualPriceData.setText(parsedData[2]);
        actualDateData.setText(parsedData[3]);
        productNameData.setText(parsedData[4]);
        descriptionData.setText(parsedData[5]);

    }

    public String[] getAllTextField() {

        String[] data = new String[8];
        data[0] = getId();
        data[1] = getUrl();
        data[2] = getPrice();
        data[3] = getDate();
        data[4] = getActualPrice();
        data[5] = getActualDate();
        data[6] = getProductName();
        data[7] = getProductDescribe();

        return data;
    }

    public void setAllTextField(Product product) {
        idData.setText("" + product.getId());
        urlData.setText("" + product.getUrl());
        priceData.setText("" + product.getPrice());
        dateData.setText("" + product.getDate());
        actualPriceData.setText("" + product.getActualPrice());
        actualDateData.setText("" + product.getActualDate());
        productNameData.setText("" + product.getProductName());
        descriptionData.setText("" + product.getDescription());
        bImage = product.getbImage();
    }

    public void setImage(BufferedImage img) {
        this.bImage = img;
        repaint();
    }

    public BufferedImage getImage() {
        return bImage;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (bImage != null) {
            setLeftUpperCorner(5, 310,
                    new Dimension(bImage.getWidth(), bImage.getHeight()));
            g.drawImage(bImage, 5, 310, null);
        }
    }
}
