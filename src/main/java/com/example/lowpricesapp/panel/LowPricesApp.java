package com.example.lowpricesapp.panel;

import com.example.lowpricesapp.DAO.CreateDBDAO;
import com.example.lowpricesapp.DAO.ProductDAO;
import com.example.lowpricesapp.DBConnection;
import com.example.lowpricesapp.service.CreateDBService;
import com.example.lowpricesapp.service.ParseSportDirectPageService;
import com.example.lowpricesapp.service.ProductService;
import org.apache.log4j.Logger;

import java.awt.*;
import java.sql.Connection;

public class LowPricesApp {

    private static Logger logger = Logger.getLogger(LowPricesApp.class);

    public static void main(String[] args) throws Exception {
        logger.info("=========================================== start");
        logger.warn("=========================================== start");

        DBConnection dbConnection = new DBConnection();

        logger.info("Connection.");
        Connection connection = dbConnection.getConnection();

        logger.info("CreateDBDAO.");
        CreateDBDAO createDBDAO = new CreateDBDAO();
        createDBDAO.setConnection(connection);

        logger.info("CreateDBService.");
        CreateDBService createDBService = new CreateDBService();
        logger.info("createDBService.setCreateDBDAO.");
        createDBService.setCreateDBDAO(createDBDAO);

        try {
            logger.info("createDBService.createNewDB.");
            createDBService.createNewDB();
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("wyjatek w create db service.");
        }

        ParseSportDirectPageService parseWebPageService = new ParseSportDirectPageService();

        ProductDAO productDAO = new ProductDAO();

        ProductService productService = new ProductService();
        productService.setProductDAO(productDAO);

        // main window
        MainWindow mainWindow = new MainWindow();

        MainPanel mainPanel = new MainPanel();

        Container mainContent = mainWindow.getContentPane();

        mainContent.add(mainPanel);

        AddProductPanel addPanel = new AddProductPanel();
        mainPanel.setAddPanel(addPanel);
        addPanel.setPanel(mainPanel);
        addPanel.setProductService(productService);
        addPanel.setProductParseWebPage(parseWebPageService);

        ViewAllProductsPanel viewAllProductsPanel = new ViewAllProductsPanel();
        viewAllProductsPanel.setPanel(mainPanel);
        viewAllProductsPanel.setProductService(productService);
        mainPanel.setViewAllProductsPanel(viewAllProductsPanel);
        mainWindow.setVisible(true);
    }
}
