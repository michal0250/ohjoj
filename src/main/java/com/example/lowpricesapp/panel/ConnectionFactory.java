package com.example.lowpricesapp.panel;


import org.springframework.beans.factory.FactoryBean;

import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 27.10.12
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionFactory implements FactoryBean {

    @Override
    public Connection getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSingleton() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
