package com.example.lowpricesapp.service;

import com.example.lowpricesapp.DAO.ProductDAO;
import com.example.lowpricesapp.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;
    private static Logger logger = Logger.getLogger(ProductService.class);

    public String saveNewProduct(String product[], BufferedImage bImage)
            throws Exception {

        product[0] = "0";
        product[3] = product[5];

        if (product[2].equals("")) {
            product[2] = product[4];
        }

        return productDAO.save(new Product(product, bImage));
    }

    public List<Product> findAll() throws Exception {
        int id;
        String url;
        double price;
        String date;
        double actualPrice;
        String actualDate;
        String productName;
        String description;

        InputStream isImg;
        BufferedImage bImage = null;
        logger.info("function findAll: ");

        List<Product> products = new ArrayList<Product>();

        ResultSet result = productDAO.findAll();

        while (result.next()) {
            id = Integer.parseInt(result.getString("ID_PRODUCTS_KEY"));
            url = result.getString("WEB_ADDRESS");
            price = Double.parseDouble(result.getString("PRICE"));
            date = result.getString("DATA");
            actualPrice = Double.parseDouble(result.getString("ACTUAL_PRICE"));
            actualDate = result.getString("ACTUAL_DATA");
            productName = result.getString("PRODUCT_NAME");
            description = result.getString("DESCRIPTION");
            isImg = result.getBinaryStream("IMG");

            logger.info("zawartosc imputstream img:" + isImg);
            if (isImg != null) {
                logger.info("zawartosc imputstream img != null: " + isImg
                        + " a jego id=" + id);
                bImage = ImageIO.read(isImg);
            }

            Product product = new Product(id, url, price, date, actualPrice,
                    actualDate, productName, description, bImage);
            products.add(product);
        }

        return products;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public String[][] prepareDataToDisplay(List<Product> allProducts) {

        String data[][] = new String[allProducts.size()][7];
        Iterator<Product> productsIterator = allProducts.iterator();
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        Product pro;
        int i = 0;

        while (productsIterator.hasNext()) {
            pro = productsIterator.next();

            data[i][0] = "" + pro.getId();
            data[i][1] = "" + pro.getProductName();
            data[i][2] = "" + pro.getDate();
            data[i][3] = numberFormat.format(pro.getPrice());
            data[i][4] = numberFormat.format(pro.getActualPrice());
            data[i][5] = ""
                    + absoluteValue(pro.getPrice(), pro.getActualPrice());

            data[i][6] = numberFormat.format(x(pro.getPrice(),
                    pro.getActualPrice()));
            i++;
        }
        return data;
    }

    private Double x(double price, double actualPrice) {
        Double x = price - actualPrice;
        return x;
    }

    private int absoluteValue(double price, double actualPrice) {
        int x = (int) ((1 - actualPrice / price) * 100);

        return x;
    }

    public void updateProduct(Product product) throws Exception {
        productDAO.update(product);

    }

    public void deleteProduct(Product product) throws Exception {

        productDAO.delete(product.getId());

    }

    public void checkForProductsChangePrice(List<Product> allProducts)
            throws Exception {

        ParseSportDirectPageService parseWebPageService = new ParseSportDirectPageService();

        Iterator<Product> productsIterator = allProducts.iterator();
        Product oldProduct;

        while (productsIterator.hasNext()) {
            oldProduct = productsIterator.next();

            Double actualPrice = null;
            try {
                logger.info("now time for product id= " +oldProduct.getId());
                actualPrice = parseWebPageService.didPriceChanged(oldProduct
                        .getUrl());

                if (oldProduct.getActualPrice() != actualPrice) {
                    oldProduct.setActualPrice(actualPrice);
                    updateProduct(oldProduct);
                    logger.info("chceck new price for id= " + oldProduct.getId() + " : new price" + oldProduct);
                } else {
                    logger.info("chceck new price for id= " + oldProduct.getId() + " : no changes ");
                }

            } catch (Exception e) {
                logger.warn("id= " + oldProduct.getId() + " exception - probobly can not find product: " + e);
                continue;
            }


        }
    }
}
