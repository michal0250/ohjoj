package com.example.lowpricesapp.panel;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringLauncher {

    private static Logger logger = Logger.getLogger(SpringLauncher.class);

    public static void main(String[] args) {
        logger.info("===========================================start");
        logger.warn("===========================================start");

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        MainWindow mainWindow = context.getBean(MainWindow.class);

        mainWindow.setVisible(true);

    }
}
