package controller;

import DB.ConfigurazioneDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.sistema.Sistema;
import util.Util;

import java.io.IOException;

public class HomeController implements Controller{

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
        controllerSetting.init(null,stage);
        Util.changeScene("Setting",root,stage,null,null,scene);
        System.out.println();
    }

    public void caricaPartita(ActionEvent e)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CaricaPartita.fxml"));
        Parent root = loader.load();
        CaricaPartitaController controllerCarica = loader.getController();
        controllerCarica.init(null,stage);
        Util.changeScene("Carica Partita",root,stage,400.0,600.0,scene);
    }

    @Override
    public void init(Sistema sistema, Stage stage) {
        this.stage = stage;
    }

    @Override
    public Sistema getSistema() {
        return null;
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}
