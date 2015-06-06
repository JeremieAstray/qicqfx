package com.jeremie.qicqfx.client.controller;

import com.jeremie.qicqfx.client.constants.Constants;
import com.jeremie.qicqfx.client.constants.SpringFxmlLoader;
import com.jeremie.qicqfx.client.gui.ScreenManager;
import com.jeremie.qicqfx.client.socket.QicqClient;
import com.jeremie.qicqfx.dto.ConnectDTO;
import com.jeremie.qicqfx.dto.DisconnectDTO;
import com.jeremie.qicqfx.dto.ErrorMessageDTO;
import com.jeremie.qicqfx.util.CallBack;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jeremie on 2015/5/19.
 */
@Controller("LoginController")
public class LoginController implements Initializable {

    private static Logger logger = LogManager.getLogger(LoginController.class);
    @FXML
    private TextField username;
    @FXML
    private Button login;

    @FXML
    private void close() {
        ScreenManager.screenManager.closeStage();
    }

    @FXML
    private void minimize() {
        ScreenManager.screenManager.minimize();
    }

    @FXML
    private void login() {
        if (!StringUtils.isEmpty(username.getText().trim())) {
            Constants.qicqClient = new QicqClient(Constants.dataHandler);
            Constants.qicqThread = new Thread(Constants.qicqClient);
            Constants.qicqThread.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            QicqClient qicqClient = Constants.qicqClient;
            qicqClient.sendData(new ConnectDTO(username.getText().trim()));
            System.out.println("等待连接中...");
            login.setText("登 录 中！");
            login.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Constants.dataHandler.addCallback(new CallBack<ConnectDTO>() {
            @Override
            public void call(ConnectDTO data) {
                Constants.username = data.getUsername();
                Platform.runLater(() -> {
                    ScreenManager.screenManager.loadMainPane();
                    ((MainController)SpringFxmlLoader.applicationContext.getBean("MainController")).changUsername(data.getUsername());
                    login.setText("登 录");
                    login.setDisable(false);
                });
            }
        });
        Constants.dataHandler.addCallback(new CallBack<ErrorMessageDTO>() {
            @Override
            public void call(ErrorMessageDTO data) {
                System.out.println("连接错误：" + data.getErrorMessage());
                DisconnectDTO disconnectDTO = new DisconnectDTO(true);
                disconnectDTO.setUsername(Constants.username);
                disconnectDTO.setReason("断开连接");
                while(Constants.qicqThread.isAlive()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        logger.error(e);
                    }
                }
                Platform.runLater(() -> {
                    login.setText("登 录");
                    login.setDisable(false);
                });
            }
        });
    }
}
