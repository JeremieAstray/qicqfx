package com.jeremie.qicqfx.client.gui;

import com.jeremie.qicqfx.client.constants.SpringFxmlLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by jeremie on 15/5/8.
 */
@Configuration
public class ScreenManager implements Observer {
    private static Logger logger = LogManager.getLogger(ScreenManager.class);
    private static SpringFxmlLoader loader = new SpringFxmlLoader();
    public static ScreenManager screenManager;

    public ScreenManager() {
        screenManager = this;
    }

    private Stage stage;
    private Scene scene;
    private Parent loginPane;
    private Parent mainPane;
    private double initX;
    private double initY;
    private String loginCss = ScreenManager.class.getResource("/css/login.css").toExternalForm();
    private String mainCss = ScreenManager.class.getResource("/css/main.css").toExternalForm();

    public void setPrimaryStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void showStage() {
        stage.setTitle("qicqfx");
        loadLoginPane();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }


    public void loadLoginPane() {
        closeAllPane();
        if (loginPane == null) {
            loginPane = loader.load("/fxml/login.fxml");
            loginPane.setOnMousePressed(me -> {
                initX = me.getScreenX() - stage.getX();
                initY = me.getScreenY() - stage.getY();
                me.consume();
            });
            loginPane.setOnMouseDragged(me -> {
                stage.setX(me.getScreenX() - initX);
                stage.setY(me.getScreenY() - initY);
                me.consume();
            });
        }
        loginPane.setVisible(true);
        if (scene == null)
            scene = new Scene(loginPane, 430, 330);
        scene.setRoot(loginPane);
        stage.setHeight(330);
        stage.setWidth(430);
        stage.setResizable(false);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(loginCss);
    }

    public void loadMainPane() {
        closeAllPane();
        if (mainPane == null) {
            mainPane = loader.load("/fxml/main.fxml");
            mainPane.setOnMousePressed(me -> {
                initX = me.getScreenX() - stage.getX();
                initY = me.getScreenY() - stage.getY();
                me.consume();
            });
            mainPane.setOnMouseDragged(me -> {
                stage.setX(me.getScreenX() - initX);
                stage.setY(me.getScreenY() - initY);
                me.consume();
            });
        }
        mainPane.setVisible(true);
        scene.setRoot(mainPane);
        stage.setHeight(550);
        stage.setWidth(800);
        stage.setMinHeight(550);
        stage.setMinWidth(800);
        stage.setResizable(true);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(mainCss);
    }

    public void minimize() {
        stage.setIconified(true);
    }

    public void closeStage() {
        stage.close();
    }

    private void closeAllPane() {
        if (mainPane != null)
            mainPane.setVisible(false);
        if (loginPane != null)
            loginPane.setVisible(false);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
