package com.jeremie.qicqfx.client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by jeremie on 15/5/8.
 */
public class Main extends Application {
    private static Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("Starting application");
        Platform.setImplicitExit(true);
        ScreenManager screens = new ScreenManager();
        screens.setPrimaryStage(stage);
        screens.showStage();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
