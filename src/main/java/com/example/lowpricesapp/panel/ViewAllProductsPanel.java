package com.example.lowpricesapp.panel;

import com.example.lowpricesapp.MyOwnLayout;
import com.example.lowpricesapp.Product;
import com.example.lowpricesapp.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Controller
public class ViewAllProductsPanel extends MyOwnLayout {
    private JButton back;
    private JButton read;
    private JButton update;
    private JButton btnDelete;
    private JButton btnCheckForUpdateAllProducts;
    private JTable allProductsTable;
    private JScrollPane scrollPane;

    @Autowired
    private MainPanel mainPanel;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductPanel productPanel;

    private static Logger logger = Logger.getLogger(ViewAllProductsPanel.class);
    private List<Product> allProducts = null;


    public ViewAllProductsPanel() {
    }

    @PostConstruct
    public void init() {
        setLayout(null);

        read = new JButton("read all products from dataBase");
        read.setBounds(setLeftUpperCorner(20, 40, read.getPreferredSize()));
        add(read);
        read.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("read button has been clicked");
                (new Thread() {

                    public void run() {
                        readAllProducts();
                    }
                }
                ).start();
            }
        });

        update = new JButton("update product");
        update.setBounds(setLeftUpperCorner(250, 40, update.getPreferredSize()));
        add(update);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("update button has been clicked");
                Product product = new Product(productPanel.getAllTextField(), productPanel.getImage());

                try {
                    productService.updateProduct(product);
                    readAllProducts();
                } catch (Exception e1) {
                    logger.warn("exception when updating some product from data base, see warning logs: "
                            + e1);
                    e1.printStackTrace();
                }
            }
        });

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(setLeftUpperCorner(383, 40,
                btnDelete.getPreferredSize()));
        add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("delete button has been clicked");
                Product product = allProducts.get(getNrOfSelectedRow());

                try {
                    (new Thread() {

                        public void run() {

                            try {
                                productService.deleteProduct(allProducts.get(getNrOfSelectedRow()));
                                readAllProducts();
                            } catch (Exception e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                logger.warn("some exception has been thrown " + e);
                            }
                        }
                    }
                    ).start();

                } catch (Exception e1) {
                    logger.warn("exception when deleting some product from data base, see warning logs");
                    e1.printStackTrace();
                }
            }
        });

        btnCheckForUpdateAllProducts = new JButton("update ALL");
        btnCheckForUpdateAllProducts.setBounds(setLeftUpperCorner(500, 40,
                btnCheckForUpdateAllProducts.getPreferredSize()));
        add(btnCheckForUpdateAllProducts);
        btnCheckForUpdateAllProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("check for updates button has been clicked");


                    (new Thread() {

                        public void run() {
                            if (allProducts.isEmpty()) {
                                readAllProducts();
                            }
                            try {
                                productService.checkForProductsChangePrice(allProducts);
                                readAllProducts();
                            } catch (Exception e1) {
                                logger.warn("exception when trying update all product from data base, see warning logs");
                                e1.printStackTrace();
                            }

                        }
                    }
                    ).start();

            }

        });

        back = new JButton("Back");
        back.setBounds(setCenterXPositionInWindow(730, back.getPreferredSize()));
        add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("back button has been clicked");
                productPanel.clearAll();
                goToMainPanel();
            }
        });


    }

    private void readAllProducts() {

        allProducts = null;
        try {
            allProducts = productService.findAll();
        } catch (Exception e1) {
            logger.warn("can't read from data base");
            e1.printStackTrace();
        }

        if (allProductsTable != null) {
            remove(allProductsTable);
            remove(scrollPane);

            if (productPanel != null) {
                remove(productPanel);
            }
            validate();
            repaint();
        }

        String dataToDisplay[][] = productService
                .prepareDataToDisplay(allProducts);
        createProductsTable(dataToDisplay);
    }

    private void createProductsTable(String[][] dataToDisplay) {

        DefaultTableModel model = createDefaultTableModel(dataToDisplay);

        allProductsTable = new JTable(model);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        allProductsTable.getColumnModel().getColumn(0)
                .setCellRenderer(rightRenderer);
        allProductsTable.getColumnModel().getColumn(3)
                .setCellRenderer(rightRenderer);
        allProductsTable.getColumnModel().getColumn(4)
                .setCellRenderer(rightRenderer);
        allProductsTable.getColumnModel().getColumn(5)
                .setCellRenderer(rightRenderer);
        allProductsTable.getColumnModel().getColumn(6)
                .setCellRenderer(rightRenderer);

        allProductsTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        allProductsTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        allProductsTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        allProductsTable.getColumnModel().getColumn(3).setPreferredWidth(55);
        allProductsTable.getColumnModel().getColumn(4).setPreferredWidth(55);
        allProductsTable.getColumnModel().getColumn(5).setPreferredWidth(30);
        allProductsTable.getColumnModel().getColumn(6).setPreferredWidth(50);

        allProductsTable.setRowSelectionAllowed(true);
        allProductsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        scrollPane = new JScrollPane(allProductsTable);
        scrollPane.setBounds(10, 100, 550, 600);
        add(scrollPane);

        allProductsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                showProductPanel();
            }
        });
    }

    private DefaultTableModel createDefaultTableModel(String[][] dataToDisplay) {
        String col[] = {"ID", "Name", "Date", "basic £", "now £", "%", "diff £"};

        DefaultTableModel model = new DefaultTableModel(dataToDisplay, col) {

            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };
        return model;
    }

    private void showProductPanel() {


        productPanel.setBounds(600, 100, 600, 600);


        productPanel.setAllTextField(allProducts.get(getNrOfSelectedRow()));
        add(productPanel);

        productPanel.validate();
        productPanel.repaint();
    }

    private int getNrOfSelectedRow() {
        int selectedRow = allProductsTable.getSelectedRow();
        logger.info("selected row is= " + selectedRow);
        return selectedRow;
    }

    public void goToMainPanel() {

        if (allProductsTable != null) {
            remove(allProductsTable);
            remove(productPanel);
            remove(scrollPane);
        }

        JFrame root = (JFrame) SwingUtilities
                .getRoot(ViewAllProductsPanel.this);
        root.remove(ViewAllProductsPanel.this);
        root.add(mainPanel);
        root.validate();
        root.repaint();
    }

    public void setPanel(MainPanel mainPanel) {

        this.mainPanel = mainPanel;
    }

    public void setProductService(ProductService productService) {

        this.productService = productService;
    }

}
