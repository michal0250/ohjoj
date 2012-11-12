package com.example.lowpricesapp.DAO;

import com.example.lowpricesapp.DBConnection;
import com.example.lowpricesapp.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class ProductDAO {

    @Autowired
    DBConnection dbConnection;

    private static Logger logger = Logger.getLogger(ProductDAO.class);

    public String save(Product product) throws Exception {

        PreparedStatement statement = dbConnection.getConnection()
                .prepareStatement("INSERT INTO PRODUCTS VALUES (?,?,?,?,?,?,?,?,?)");
        statement.setString(1, null);
        statement.setString(2, product.getUrl());
        statement.setDouble(3, product.getPrice());
        statement.setString(4, product.getDate());
        statement.setDouble(5, product.getActualPrice());
        statement.setString(6, product.getActualDate());
        statement.setString(7, product.getProductName());
        statement.setString(8, product.getDescription());

        statement.setBytes(9, prepareImg(product.getbImage()));

        System.out.println("Wykonanie zapytania do bazy.");
        boolean result = statement.execute();
        if (!result)
            return "dodano wpis do bazy danych";
        else
            return "nie dodano wpisu do bazy danych";

    }

    private byte[] prepareImg(BufferedImage bImage) throws IOException {
        byte[] bytesOut = null;
        if (bImage != null) {
            logger.info("imageJpg != null");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", baos);
            baos.flush();
            bytesOut = baos.toByteArray();
            baos.close();

            logger.info("dugosc tab byte[]: " + bytesOut.length);
        }
        return bytesOut;
    }

    public ResultSet findAll() throws Exception {
        PreparedStatement statement = dbConnection.getConnection()
                .prepareStatement("SELECT * FROM PRODUCTS ORDER BY ID_PRODUCTS_KEY");

        ResultSet res = statement.executeQuery();
        logger.info("ResultSet res=" + res);

        return res;
    }

    public void update(Product product) throws Exception {

        PreparedStatement statement = dbConnection.getConnection()
                .prepareStatement("UPDATE PRODUCTS SET WEB_ADDRESS= ?, PRICE= ?, DATA= ?, ACTUAL_PRICE= ?, ACTUAL_DATA= ?, PRODUCT_NAME= ?, DESCRIPTION= ?, IMG= ? WHERE ID_PRODUCTS_KEY =? ");

        statement.setString(1, product.getUrl());
        statement.setDouble(2, product.getPrice());
        statement.setString(3, product.getDate());
        statement.setDouble(4, product.getActualPrice());
        statement.setString(5, product.getActualDate());
        statement.setString(6, product.getProductName());
        statement.setString(7, product.getDescription());
        statement.setBytes(8, prepareImg(product.getbImage()));
        statement.setInt(9, product.getId());

        System.out.println("update do bazy: " + statement.toString() + "\n");
        int result = statement.executeUpdate();
        logger.info("Result=" + result);

    }

    public void delete(int id) throws Exception {
        logger.info("delete product from PRODUCTS");
        PreparedStatement statement = dbConnection.getConnection()
                .prepareStatement("DELETE FROM PRODUCTS WHERE ID_PRODUCTS_KEY=?");
        statement.setInt(1, id);

        System.out.println("prepared statment: " + statement.toString() + "\n");

        int result = statement.executeUpdate();
        logger.info("Result=" + result);

    }
}
