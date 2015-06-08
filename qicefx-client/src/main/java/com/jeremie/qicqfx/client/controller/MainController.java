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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
    private List<String> onlineUsers = new ArrayList<>();
    private Map<String, List<PrivateMessageDTO>> privateMsgsMap = new HashMap<>();
    private List<GroupMessageDTO> groupMsgs = new ArrayList<>();

    private Pair<String, Label> privateUser;

    private boolean isGroupChat = false;

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
        onlineUser.getChildren().clear();
        chatBox.getChildren().clear();
        privateMsgsMap.clear();
        groupMsgs.clear();
        privateUser = null;
        Constants.qicqClient = null;
        Constants.qicqThread = null;
        ScreenManager.screenManager.loadLoginPane();
    }

    @FXML
    private void sendPic() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("png图片 (*.*.png)", "*.png"),
                new FileChooser.ExtensionFilter("jpg图片 (*.*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("gif图片 (*.*.gif)", "*.gif"),
                new FileChooser.ExtensionFilter("jpeg图片 (*.*.jpeg)", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if(file!=null) {
            ByteBuffer byteBuffer = null;
            FileInputStream fs = null;
            FileChannel channel = null;
            try {
                fs = new FileInputStream(file);
                channel = fs.getChannel();
                byteBuffer = ByteBuffer.allocate((int) channel.size());
                while ((channel.read(byteBuffer)) > 0) {
                }
            } catch (IOException e) {
                logger.error(e);
                return;
            } finally {
                try {
                    if (channel != null)
                        channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (fs != null)
                        fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteBuffer != null) {
                if (isGroupChat) {
                    GroupMessageDTO groupMessageDTO = new GroupMessageDTO();
                    groupMessageDTO.setIsImage(true);
                    groupMessageDTO.setSender(Constants.username);
                    groupMessageDTO.setImage(byteBuffer.array());
                    Constants.qicqClient.sendData(groupMessageDTO);
                } else {
                    if (privateUser == null)
                        System.out.println("未选择聊天用户");
                    else {
                        String user = privateUser.getKey();
                        PrivateMessageDTO privateMessageDTO = new PrivateMessageDTO();
                        privateMessageDTO.setIsImage(true);
                        privateMessageDTO.setSender(Constants.username);
                        privateMessageDTO.setReceiver(user);
                        privateMessageDTO.setCreateTime(System.currentTimeMillis());
                        privateMessageDTO.setImage(byteBuffer.array());
                        privateMessageDTO.setCreateTime(System.currentTimeMillis());
                        Constants.qicqClient.sendData(privateMessageDTO);
                        privateMsgsMap.get(user).add(privateMessageDTO);
                        updateChatBox(false, user);
                    }
                }
            }
        }

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
                if (privateUser == null)
                    System.out.println("未选择聊天用户");
                else {
                    String user = privateUser.getKey();
                    PrivateMessageDTO privateMessageDTO = new PrivateMessageDTO();
                    privateMessageDTO.setSender(Constants.username);
                    privateMessageDTO.setReceiver(user);
                    privateMessageDTO.setMessage(msg);
                    privateMessageDTO.setCreateTime(System.currentTimeMillis());
                    Constants.qicqClient.sendData(privateMessageDTO);
                    messageArea.setText("");
                    privateMsgsMap.get(user).add(privateMessageDTO);
                    updateChatBox(false, user);
                }
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
            label.setId(user);
            label.setOnMouseReleased(event -> {
                for (javafx.scene.Node node : onlineUser.getChildren()) {
                    node.setStyle("-fx-background-color: transparent;");
                }
                if (label.getText().endsWith("(未接消息)"))
                    label.setText(label.getId());
                label.setStyle("-fx-background-color: #D5D5D5;");
                privateUser = new Pair<>(label.getText(), label);
                isGroupChat = false;
                userBtn.setBackground(new Background(userActiveImage));
                groupBtn.setBackground(new Background(groupImage));
                updateChatBox(false, user);
            });
            if (privateUser != null && user.equals(privateUser.getKey()))
                label.setStyle("-fx-background-color: #D5D5D5;");
            onlineUser.getChildren().add(label);
        }
    }

    private void updateChatBox(boolean isgroup, String user) {
        chatBox.getChildren().clear();
        if (isgroup) {
            for (GroupMessageDTO groupMessage : groupMsgs) {
                if (groupMessage.isImage()) {
                    Image image = new Image(new ByteArrayInputStream(groupMessage.getImage()));
                    int zoom = 1;
                    if(image.getWidth()>562 || image.getHeight()>330){
                        int wzoom = ((Double) Math.ceil(image.getWidth() / 562)).intValue();
                        int hzoom = ((Double) Math.ceil(image.getHeight() / 330)).intValue();
                        zoom = Math.max(wzoom,hzoom);
                    }
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(image.getWidth()/zoom);
                    imageView.setFitHeight(image.getHeight()/zoom);
                    Label info = new Label();
                    info.setText(groupMessage.getSender() + "   " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date(groupMessage.getCreateTime()))));
                    chatBox.getChildren().add(info);
                    chatBox.getChildren().add(imageView);
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
            List<PrivateMessageDTO> privateMessages = privateMsgsMap.get(user);
            for (PrivateMessageDTO privateMessage : privateMessages) {
                if (privateMessage.isImage()) {
                    Image image = new Image(new ByteArrayInputStream(privateMessage.getImage()));
                    int zoom = 1;
                    if(image.getWidth()>562 || image.getHeight()>330){
                        int wzoom = ((Double) Math.ceil(image.getWidth() / 562)).intValue();
                        int hzoom = ((Double) Math.ceil(image.getHeight() / 330)).intValue();
                        zoom = Math.max(wzoom,hzoom);
                    }
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(image.getWidth()/zoom);
                    imageView.setFitHeight(image.getHeight()/zoom);
                    Label info = new Label();
                    info.setText(privateMessage.getSender() + "   " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date(privateMessage.getCreateTime()))));
                    chatBox.getChildren().add(info);
                    chatBox.getChildren().add(imageView);
                } else {
                    Label info = new Label();
                    info.setText(privateMessage.getSender() + "   " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date(privateMessage.getCreateTime()))));
                    Label message = new Label();
                    double hight = 20.0 + privateMessage.getMessage().split("\n").length * 15.0;
                    message.setPrefHeight(hight);
                    message.setText(privateMessage.getMessage());
                    chatBox.getChildren().add(info);
                    chatBox.getChildren().add(message);
                }
            }
        }
        chatBoxScroll.setVvalue(chatBoxScroll.getVmax() - 0.2);
    }

    private void updateGroupChatBox() {
        this.updateChatBox(true, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupBtn.setBackground(new Background(groupActiveImage));
        userBtn.setBackground(new Background(userImage));
        backBtn.setBackground(new Background(backImage));
        isGroupChat = true;
        updateGroupChatBox();
        groupBtn.setOnMouseReleased(event -> {
            userBtn.setBackground(new Background(userImage));
            groupBtn.setBackground(new Background(groupActiveImage));
            isGroupChat = true;
            if (privateUser != null) {
                privateUser.getValue().setStyle("-fx-background-color: transparent;");
                privateUser = null;
            }
            updateGroupChatBox();
        });
        userBtn.setOnMouseReleased(event -> {
            if (!onlineUser.getChildren().isEmpty()) {
                for (Node node : onlineUser.getChildren()) {
                    if (node instanceof Label) {
                        privateUser = new Pair<>(node.getId(), (Label) node);
                        if (privateUser.getValue().getText().endsWith("(未接消息)"))
                            privateUser.getValue().setText(privateUser.getKey());
                        privateUser.getValue().setStyle("-fx-background-color: #D5D5D5;");
                        updateChatBox(false, privateUser.getKey());
                        break;
                    }
                }
            }
            userBtn.setBackground(new Background(userActiveImage));
            groupBtn.setBackground(new Background(groupImage));
            isGroupChat = false;
        });
        Constants.dataHandler.addCallback(new CallBack<OnlineUsersMessageDTO>() {
            @Override
            public void call(OnlineUsersMessageDTO data) {
                onlineUsers.removeAll(data.getOnlineUsers());
                for (String outlineUser : onlineUsers)
                    if (outlineUser.equals(privateUser.getKey())) {
                        privateUser = null;
                        break;
                    }
                for (String olUser : data.getOnlineUsers())
                    if (!privateMsgsMap.containsKey(olUser)) privateMsgsMap.put(olUser, new ArrayList<>());
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
        Constants.dataHandler.addCallback(new CallBack<PrivateMessageDTO>() {

            @Override
            public void call(PrivateMessageDTO data) {
                privateMsgsMap.get(data.getSender()).add(data);
                String sender = data.getSender();
                Platform.runLater(() -> {
                    if (privateUser != null && privateUser.getKey().equals(sender)) {
                        updateChatBox(false, privateUser.getKey());
                    } else {
                        onlineUser.getChildren().stream()
                                .filter(node -> node instanceof Label)
                                .filter(node -> node.getId().equals(sender))
                                .forEach(node -> ((Label) node).setText(sender + "(未接消息)"));
                    }
                });
            }
        });
    }
}
