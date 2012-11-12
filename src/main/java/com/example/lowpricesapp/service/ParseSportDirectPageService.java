package com.example.lowpricesapp.service;

import com.example.lowpricesapp.GoToGuiException;
import com.example.lowpricesapp.Product;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ParseSportDirectPageService {
    private String url;
    private static Logger logger = Logger.getLogger(ParseSportDirectPageService.class);
    private Document doc;
    private String docToString;

    public ParseSportDirectPageService() {
        logger.info("parse web pages constructor created.");

    }

    public String getActualPrice() {
        logger.info("function: getActualPrice.");
        String actualPriceString = "";
        try {
            String firstCut = docToString.substring(docToString.lastIndexOf("var initialData = "));
            String secondCut = firstCut.substring(firstCut.indexOf("\"SellingPrice"), firstCut.indexOf("\",\"TicketPrice"));
//            logger.info("second cut contain: " + secondCut);
            actualPriceString = secondCut.substring(17);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            logger.warn("exception in get price: " + e.getMessage());
            return "";
        }
        logger.info("actual price(String) contain: " + actualPriceString);
        return actualPriceString;
    }

    public String getPrice() {
        logger.info("function: getPrice.");
        String priceString;
        try {
            String firstCut = docToString.substring(docToString.lastIndexOf("var initialData = "));
            String secondCut = firstCut.substring(firstCut.indexOf("\"TicketPrice"), firstCut.indexOf("\",\"SellingPriceNoPence"));
            priceString = secondCut.substring(16);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            logger.warn("exception in getPrice: " + e.getMessage());
            return "";
        }
        logger.info("price(String) contain: " + priceString);
        return priceString;
    }

    public String getProductDescribe() {
        logger.info("function: getProductDescribe.");
        String productDescribe = "";
        try {
            String firstCut = docToString.substring(docToString.indexOf("<meta name=\"og:description\" content=\"")).trim();
            productDescribe = firstCut.substring(firstCut.indexOf("\n") + 1, firstCut.indexOf(" />"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("exception in getProductDescribe: " + e.getMessage());
            return "";
        }

        if (productDescribe.length() > 800) {
            logger.info("productDescribe.lenght > 800 - need to be cut");
            productDescribe = productDescribe.substring(0, 800);
        }
        logger.info("productDescribe contain: " + productDescribe);
        return productDescribe;
    }

    public String getActualDate() {
        logger.info("function: getActualDate.");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String actualDate = simpleDateFormat.format(new Date());

        logger.info("(String) actualDate contain: " + actualDate);
        return actualDate;
    }

    public String getProductName() {
        logger.info("function: getProductName.");
        String productName = "";
        try {
            String firstCut = docToString.substring(docToString.indexOf("<meta name=\"og:description\" content=\""));
            productName = firstCut.substring(0, firstCut.indexOf("\n"));
            productName = productName.substring(37).trim();
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("exception in getProductName: " + e.getMessage());
            return "";
        }
        if (productName.length() > 100) {
            productName = productName.substring(0, 100);
        }
        logger.info("(String)productName contain: " + productName);
        return productName;
    }

    public BufferedImage getImage() {
        logger.info("images start.");
        BufferedImage image;
        try {
            String firstCut = docToString.substring(docToString.indexOf("\"ImageUrl\":\""));
            String imgSrc = firstCut.substring(12, firstCut.indexOf("\",\""));
            logger.info("imgSrc: " + imgSrc);
            URL imgUrl = new URL(imgSrc);
            image = ImageIO.read(imgUrl);
            logger.info("imgHeight: " + image.getHeight() + ", imgWidth: "
                    + image.getWidth());
        } catch (Exception e) {
            logger.warn("ex in getImage: " + e);
            return null;
        }
        logger.info("images end.");
        return image;
    }

    public void setURL(String url) throws IOException, GoToGuiException {
        this.url = url;

        try {
            this.doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            logger.warn("can not find www page: " + url + "\n exception= " + e);
            throw new GoToGuiException("can not find www page");
        }

        this.docToString = doc.toString();
    }

    public String getUrl() {
        return this.url;
    }

    public Product getAllProductData() {
        logger.info("start.");
        String allData[] = new String[8];
        BufferedImage bImg = null;

        allData[0] = "0";
        allData[1] = getUrl();
        allData[2] = getPrice();
        allData[3] = getActualDate();
        logger.info("after getActualDate");
        allData[4] = getActualPrice();
        allData[5] = getActualDate();
        allData[6] = getProductName();
        allData[7] = getProductDescribe();
        logger.info("after all field, now it is time for picture.");
        bImg = getImage();

        Product product = new Product(allData, bImg);
        logger.info("end.");
        return product;
    }


    public double didPriceChanged(String url) throws Exception {
        setURL(url);
        return parseActualPrice(getActualPrice());
    }

    private double parseActualPrice(String actualPrice) {
        return Double.parseDouble(actualPrice);
    }
}
