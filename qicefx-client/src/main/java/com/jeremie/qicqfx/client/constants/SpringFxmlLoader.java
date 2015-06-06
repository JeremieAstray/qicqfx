package com.jeremie.qicqfx.client.constants;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.URL;

/**
 * Created by jeremie on 15/5/8.
 */
public class SpringFxmlLoader {

    public static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    public Parent load(String url) {
        try {
            URL sourseUrl = this.getClass().getResource(url);
            FXMLLoader loader = new FXMLLoader(sourseUrl);
            //将controller 交给spring来控制
            loader.setControllerFactory(applicationContext::getBean);
            return loader.load();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}