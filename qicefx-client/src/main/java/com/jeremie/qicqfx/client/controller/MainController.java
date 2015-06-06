package com.jeremie.qicqfx.client.controller;

import com.jeremie.qicqfx.client.constants.Constants;
import com.jeremie.qicqfx.client.gui.ScreenManager;
import com.jeremie.qicqfx.dto.DisconnectDTO;
import com.jeremie.qicqfx.dto.MessageDTO;
import com.jeremie.qicqfx.dto.OnlineUsersMessageDTO;
import com.jeremie.qicqfx.util.CallBack;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import javax.xml.soap.Node;
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
    private TextField searchBox;
    @FXML
    private Text username;
    @FXML
    private VBox onlineUser;

    private String privateUser;

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
        Constants.dataHandler.addCallback(new CallBack<OnlineUsersMessageDTO>() {
            @Override
            public void call(OnlineUsersMessageDTO data) {
                Platform.runLater(() -> {
                    onlineUser.getChildren().clear();
                    for(String user:data.getOnlineUsers()) {
                        if (user.equals(Constants.username))
                            continue;
                        Label label = new Label(user);
                        label.setOnMouseReleased(event -> {
                            for(javafx.scene.Node node:onlineUser.getChildren()){
                                node.setStyle("-fx-background-color: transparent;");
                            }
                            label.setStyle("-fx-background-color: #D5D5D5;");
                            privateUser = label.getText();
                        });
                        if(user.equals(privateUser))
                            label.setStyle("-fx-background-color: #D5D5D5;");
                        onlineUser.getChildren().add(label);
                    }
                });
            }
        });
    }
}
