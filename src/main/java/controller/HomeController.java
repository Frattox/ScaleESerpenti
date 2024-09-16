package controller;

import DB.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    private ConnectDB connectDB;

    public void nuovaPartita(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Setting.fxml"));
        Parent root = loader.load();
        SettingController controllerSetting = loader.getController();
        controllerSetting.setting();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.show();
    }

    public void caricaPartita(ActionEvent e)  throws IOException {
        //TODO
    }

}
