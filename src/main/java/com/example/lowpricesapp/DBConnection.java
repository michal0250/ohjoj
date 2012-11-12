package com.example.lowpricesapp;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DBConnection {

    private Connection connection;

    private final String url = "jdbc:h2:~/test8";
    // "jdbc:h2:C:\\H2DB\\database\\DBNAME"
    private final String user = "sa";
    private final String password = "";
    private final String className = "org.h2.Driver";
    private static Logger logger = Logger.getLogger(DBConnection.class);

    public Connection getConnection() throws ClassNotFoundException,
            SQLException {
        if (connection == null) {
            logger.info("connection == null, create new connection. ");
            Class.forName(className);
            connection = DriverManager.getConnection(url, user, password);
        }
        logger.info("return DBConnection.");
        return connection;
    }
}
