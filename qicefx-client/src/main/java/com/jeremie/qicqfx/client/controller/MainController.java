package com.jeremie.qicqfx.client.controller;

import com.jeremie.qicqfx.client.gui.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jeremie on 15/5/19.
 */
@Controller
public class MainController implements Initializable {

    @FXML
    private Label groupBtn, userBtn;

    private BackgroundImage userImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/user.png"), 30, 30, true, true)
            , BackgroundRepeat.NO_REPEAT
            , BackgroundRepeat.NO_REPEAT
            , BackgroundPosition.CENTER
            , BackgroundSize.DEFAULT);
    private BackgroundImage userActiveImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/userActive.png"), 30, 30, true, true)
            , BackgroundRepeat.NO_REPEAT
            , BackgroundRepeat.NO_REPEAT
            , BackgroundPosition.CENTER
            , BackgroundSize.DEFAULT);
    private BackgroundImage groupImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/group.png"), 42, 42, true, true)
            , BackgroundRepeat.NO_REPEAT
            , BackgroundRepeat.NO_REPEAT
            , BackgroundPosition.CENTER
            , BackgroundSize.DEFAULT);
    private BackgroundImage groupActiveImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/groupActive.png"), 42, 42, true, true)
            , BackgroundRepeat.NO_REPEAT
            , BackgroundRepeat.NO_REPEAT
            , BackgroundPosition.CENTER
            , BackgroundSize.DEFAULT);


    @FXML
    private void close(){
        ScreenManager.screenManager.closeStage();
    }
    @FXML
    private void minimize(){
        ScreenManager.screenManager.minimize();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupBtn.setBackground(new Background(groupImage));
        userBtn.setBackground(new Background(userImage));
        groupBtn.setOnMouseReleased(event -> {
            userBtn.setBackground(new Background(userImage));
            groupBtn.setBackground(new Background(groupActiveImage));
        });
        userBtn.setOnMouseReleased(event -> {
            userBtn.setBackground(new Background(userActiveImage));
            groupBtn.setBackground(new Background(groupImage));
        });

    }
}
