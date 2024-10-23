package controller;

import DB.ConfigurazioneDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private ConfigurazioneDAO configurazioneDAO;

    public void setStage(Stage stage){this.stage=stage;}

    public void nuovaPartita(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Setting.fxml"));
        Parent root = loader.load();
        SettingController controllerSetting = loader.getController();
        controllerSetting.setting(stage);
        Util.changeScene("Setting",root,stage,null,null,scene);
    }

    public void caricaPartita(ActionEvent e)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CaricaPartita.fxml"));
        Parent root = loader.load();
        CaricaPartitaController controllerCarica = loader.getController();
        controllerCarica.init();
        Util.changeScene("Carica Partita",root,stage,400.0,600.0,scene);

    }

}
