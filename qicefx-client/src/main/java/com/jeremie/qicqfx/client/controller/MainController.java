package com.jeremie.qicqfx.client.controller;

import com.jeremie.qicqfx.client.constants.Constants;
import com.jeremie.qicqfx.client.gui.ScreenManager;
import com.jeremie.qicqfx.dto.DisconnectDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jeremie on 15/5/19.
 */
@Controller("MainController")
public class MainController implements Initializable {

    private static Logger logger = LogManager.getLogger(MainController.class);
    @FXML
    private Label groupBtn, userBtn, backBtn;
    @FXML
    private Text username;

    private BackgroundImage userImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/user.png"), 48, 48, true, true)
            , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private BackgroundImage userActiveImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/userActive.png"), 48, 48, true, true)
            , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private BackgroundImage groupImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/group.png"), 48, 48, true, true)
            , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private BackgroundImage groupActiveImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/groupActive.png"), 48, 48, true, true)
            , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private BackgroundImage backImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/back.png"), 32, 32, true, true)
            , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);


    @FXML
    private void close() {
        DisconnectDTO disconnectDTO = new DisconnectDTO(true);
        disconnectDTO.setUsername(Constants.username);
        disconnectDTO.setReason("断开连接");
        Constants.qicqClient.sendData(disconnectDTO);
        ScreenManager.screenManager.closeStage();
    }

    @FXML
    private void minimize() {
        ScreenManager.screenManager.minimize();
    }

    @FXML
    private void backToLogin() {
        DisconnectDTO disconnectDTO = new DisconnectDTO(true);
        disconnectDTO.setUsername(Constants.username);
        disconnectDTO.setReason("断开连接");
        Constants.qicqClient.sendData(disconnectDTO);
        while(Constants.qicqThread.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        Constants.qicqClient = null;
        Constants.qicqThread = null;
        ScreenManager.screenManager.loadLoginPane();
    }

    public void changUsername(String username){
        this.username.setText("   " + username);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupBtn.setBackground(new Background(groupImage));
        userBtn.setBackground(new Background(userActiveImage));
        backBtn.setBackground(new Background(backImage));
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
