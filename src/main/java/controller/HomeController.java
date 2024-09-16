package controller;

import DB.ConnectConfigurazioneDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Util;

import java.io.IOException;

public class HomeController {

    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    private ConnectConfigurazioneDB connectDB;

    public void nuovaPartita(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Setting.fxml"));
        Parent root = loader.load();
        SettingController controllerSetting = loader.getController();
        controllerSetting.setting();

        Util.changeScene(e,"Home",root,stage,scene);
    }

    public void caricaPartita(ActionEvent e)  throws IOException {
        //TODO
    }

}
