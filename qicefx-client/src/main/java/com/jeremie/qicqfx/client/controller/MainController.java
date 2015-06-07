package com.jeremie.qicqfx.client.controller;

import com.jeremie.qicqfx.client.constants.Constants;
import com.jeremie.qicqfx.client.gui.ScreenManager;
import com.jeremie.qicqfx.dto.DisconnectDTO;
import com.jeremie.qicqfx.dto.GroupMessageDTO;
import com.jeremie.qicqfx.dto.OnlineUsersMessageDTO;
import com.jeremie.qicqfx.dto.PrivateMessageDTO;
import com.jeremie.qicqfx.util.CallBack;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private TextArea messageArea;
    @FXML
    private Text username;
    @FXML
    private VBox onlineUser, chatBox;
    @FXML
    private ScrollPane chatBoxScroll;
    private List<String> onlineUsers;
    private Map<String, List<PrivateMessageDTO>> privateMsgsMap = new HashMap<>();
    private List<GroupMessageDTO> groupMsgs = new ArrayList<>();


    private String privateUser;

    private boolean isGroupChat = false;

    private BackgroundImage userImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/user.png"), 48, 48, true, true)
            , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private BackgroundImage userActiveImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/userActive.png"), 48, 48, true, true)
            , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private BackgroundImage userMsgImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/userMsg.png"), 48, 48, true, true)
            , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private BackgroundImage groupImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/group.png"), 48, 48, true, true)
            , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private BackgroundImage groupActiveImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/groupActive.png"), 48, 48, true, true)
            , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private BackgroundImage groupMsgImage = new BackgroundImage(
            new Image(this.getClass().getResourceAsStream("/icon/groupMsg.png"), 48, 48, true, true)
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
        while (Constants.qicqThread.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        privateMsgsMap.clear();
        groupMsgs.clear();
        Constants.qicqClient = null;
        Constants.qicqThread = null;
        ScreenManager.screenManager.loadLoginPane();
    }

    @FXML
    private void sendMsg() {
        String msg = messageArea.getText();
        if (!StringUtils.isEmpty(msg)) {
            if (isGroupChat) {
                GroupMessageDTO groupMessageDTO = new GroupMessageDTO();
                groupMessageDTO.setSender(Constants.username);
                groupMessageDTO.setIsImage(false);
                groupMessageDTO.setMessage(msg);
                Constants.qicqClient.sendData(groupMessageDTO);
                messageArea.setText("");
            } else {

            }
        } else {
            System.out.println("消息为空");
        }
    }

    public void changUsername(String username) {
        this.username.setText("   " + username);
    }

    private void updateOnlineUserBox(List<String> users) {
        onlineUser.getChildren().clear();
        for (String user : users) {
            if (user.equals(Constants.username))
                continue;
            Label label = new Label(user);
            label.setOnMouseReleased(event -> {
                for (javafx.scene.Node node : onlineUser.getChildren()) {
                    node.setStyle("-fx-background-color: transparent;");
                }
                label.setStyle("-fx-background-color: #D5D5D5;");
                privateUser = label.getText();
            });
            if (user.equals(privateUser))
                label.setStyle("-fx-background-color: #D5D5D5;");
            onlineUser.getChildren().add(label);
        }
    }

    private void updateChatBox(boolean isgroup, String user) {
        chatBox.getChildren().clear();
        if (isgroup) {
            for (GroupMessageDTO groupMessage : groupMsgs) {
                if (groupMessage.isImage()) {

                } else {
                    Label info = new Label();
                    info.setText(groupMessage.getSender() + "   " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date(groupMessage.getCreateTime()))));
                    Label message = new Label();
                    double hight = 20.0 + groupMessage.getMessage().split("\n").length * 15.0;
                    message.setPrefHeight(hight);
                    message.setText(groupMessage.getMessage());
                    chatBox.getChildren().add(info);
                    chatBox.getChildren().add(message);
                }
            }
        } else {

        }
        chatBoxScroll.setVvalue(chatBoxScroll.getVmax() - 0.2);
    }

    private void updateGroupChatBox() {
        this.updateChatBox(true, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupBtn.setBackground(new Background(groupImage));
        userBtn.setBackground(new Background(userActiveImage));
        backBtn.setBackground(new Background(backImage));
        groupBtn.setOnMouseReleased(event -> {
            userBtn.setBackground(new Background(userImage));
            groupBtn.setBackground(new Background(groupActiveImage));
            isGroupChat = true;
            updateGroupChatBox();
        });
        userBtn.setOnMouseReleased(event -> {
            userBtn.setBackground(new Background(userActiveImage));
            groupBtn.setBackground(new Background(groupImage));
            isGroupChat = false;
        });
        Constants.dataHandler.addCallback(new CallBack<OnlineUsersMessageDTO>() {
            @Override
            public void call(OnlineUsersMessageDTO data) {
                Platform.runLater(() -> updateOnlineUserBox(data.onlineUsers));
            }
        });
        Constants.dataHandler.addCallback(new CallBack<GroupMessageDTO>() {
            @Override
            public void call(GroupMessageDTO data) {
                groupMsgs.add(data);
                if (!isGroupChat) Platform.runLater(() -> groupBtn.setBackground(new Background(groupMsgImage)));
                else Platform.runLater(MainController.this::updateGroupChatBox);
            }
        });
    }
}
