package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Sistema;

import java.io.IOException;

public class SettingVariantiController {
    @FXML
    private Sistema sistema;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private CheckBox dadoSingolo,dadoSingoloFinale,doppioSei,caselleSosta,casellePremio,casellePescaCarta,ulterioriCarte;


    public void setSistema(Sistema sistema){
        this.sistema=sistema;
    }
    public void inviaSettingVarianti(ActionEvent e) throws IOException {
        if(sistema==null)
            throw new IllegalArgumentException("SettingController: sistema ancora non istanziato");
        sistema.setDadoSingolo(dadoSingolo.isSelected());
        sistema.setDadoSingoloFinale(dadoSingoloFinale.isSelected());
        sistema.setDoppioSei(doppioSei.isSelected());
        sistema.setCaselleSosta(caselleSosta.isSelected());
        sistema.setCasellePremio(casellePremio.isSelected());
        sistema.setPescaCarta(casellePescaCarta.isSelected());
        sistema.setUlterioriCarte(ulterioriCarte.isSelected());

        sistema.setDadi();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SettingNumeroCaselleSpeciali.fxml"));
        root = loader.load();

        SettingNumeroCaselleSpecialiController settingNumeroCaselleSpecialiController = loader.getController();
        settingNumeroCaselleSpecialiController.setSistema(sistema);
        settingNumeroCaselleSpecialiController.init();

        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
