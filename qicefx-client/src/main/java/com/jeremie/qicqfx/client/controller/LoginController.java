package com.jeremie.qicqfx.client.controller;

import com.jeremie.qicqfx.client.constants.SpringFxmlLoader;
import com.jeremie.qicqfx.client.gui.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jeremie on 2015/5/19.
 */
@Controller
public class LoginController implements Initializable {


    @FXML
    private void close(){
        ScreenManager.screenManager.closeStage();
    }
    @FXML
    private void minimize(){
        ScreenManager.screenManager.minimize();
    }

    @FXML
    private void login(){
        ScreenManager.screenManager.loadMainPane();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
