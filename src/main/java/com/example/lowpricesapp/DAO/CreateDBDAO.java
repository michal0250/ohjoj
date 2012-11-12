package com.example.lowpricesapp.DAO;

import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class CreateDBDAO {

    // @Autowired
    private Connection connection;

    public CreateDBDAO() {
    }

    public void setConnection(Connection conn) {
        this.connection = conn;
    }

    public void createDataBase() throws Exception {

        String createTableProducts = "CREATE TABLE IF NOT EXISTS PRODUCTS (ID_PRODUCTS_KEY INT IDENTITY, WEB_ADDRESS VARCHAR(255), PRICE DOUBLE, DATA VARCHAR(10), ACTUAL_PRICE DOUBLE, ACTUAL_DATA VARCHAR(10), PRODUCT_NAME VARCHAR(255), DESCRIPTION TEXT, IMG MEDIUMBLOB);";
        executeSQL(createTableProducts);

//        String createTablePricesHistory = "CREATE TABLE IF NOT EXISTS PRICES_HISTORY (ID_KEY, ACTUAL_PRICE DOUBLE, ACTUAL_DATA VARCHAR(10), DID_FIND_PRODUCT BOOLEAN );";
//        executeSQL(createTablePricesHistory);

//        String createTableShops = "CREATE TABLE IF NOT EXISTS SHOP_SITE (ID_SHOP_KEY INT IDENTITY, WEB_ADDRESS VARCHAR(255), shop_name VARCHAR(55));";
//        executeSQL(createTableShops);

    }

    public void executeSQL(String sql) throws Exception {
        connection.createStatement().execute(sql);

    }
}