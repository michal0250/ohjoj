package com.example.lowpricesapp;

import com.example.lowpricesapp.panel.MainWindow;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service
public class MyOwnLayout extends JPanel {

    private static Logger logger = Logger.getLogger(MyOwnLayout.class);

    protected Rectangle setCenterXPositionInWindow(int y,
                                                   Dimension preferredSize) {
        logger.info("setCenterXPositionInWindow, y=" + y + ", x.width="
                + (int) preferredSize.getWidth() + ", y.height="
                + (int) preferredSize.getHeight());
        Rectangle rectangle = new Rectangle(0, 0,
                (int) preferredSize.getWidth(), (int) preferredSize.getHeight());

        rectangle.x = (MainWindow.WITH / 2) - (int) (rectangle.getWidth() / 2);
        rectangle.y = y - (int) (rectangle.getHeight() / 2);

        logger.info("setCenterXPositionInWindow return rectangle=" + rectangle
                + ", end shape [x,y]= [" + (rectangle.x + rectangle.getWidth())
                + " , " + (rectangle.y + rectangle.getHeight()) + "]");

        return rectangle;
    }

    protected Rectangle setLeftUpperCorner(int x, int y, Dimension preferredSize) {

        logger.info("setLeftUpperCorner, x=" + x + ", y=" + y + ", dimension="
                + preferredSize);

        Rectangle rectangle = new Rectangle(x, y,
                (int) preferredSize.getWidth(), (int) preferredSize.getHeight());

        logger.info("setLeftUpperCorner return rectangle=" + rectangle
                + ", end shape [x,y]= [" + (rectangle.x + rectangle.getWidth())
                + " , " + (rectangle.y + rectangle.getHeight()) + "]");

        return rectangle;
    }
}
