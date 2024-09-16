package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Sistema;
import util.Util;

import java.io.IOException;

public class SettingVariantiController {
    private Sistema sistema;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private CheckBox dadoSingolo,dadoSingoloFinale,doppioSei,caselleSosta,casellePremio,casellePescaCarta,ulterioriCarte;
    @FXML
    private VBox vboxVarianti, vboxAvvisi;
    @FXML
    private Label deviDisattivareDiSingoloFinale, deviDisattivareDiDoppioSei, deviAttivare;

    public void setSistema(Sistema sistema){
        this.sistema=sistema;
        setVbox(vboxVarianti);
        setVbox(vboxAvvisi);
    }
    private void setVbox(VBox vbox){
        double x = vbox.getPrefHeight()/vboxVarianti.getChildren().size();
        vbox.setSpacing(x);
        vbox.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        for(Node child: vbox.getChildren()) {
            ((Control) child).setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            VBox.setVgrow(child, Priority.ALWAYS);
        }
    }
    public void inviaSettingVarianti(ActionEvent e) throws IOException {
        if(sistema==null)
            throw new IllegalArgumentException("SettingController: sistema ancora non istanziato");

        if(!controlloVariantiOk())
            return;

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

        Util.changeScene(e,"Numero di Caselle Speciali",root,stage,scene);
    }

    private boolean controlloVariantiOk() {
        boolean ret = true;
        if((dadoSingoloFinale.isSelected()) && dadoSingolo.isSelected()) {
            deviDisattivareDiSingoloFinale.setVisible(true);
            ret = false;
        }else deviDisattivareDiSingoloFinale.setVisible(false);
        if((doppioSei.isSelected()) && dadoSingolo.isSelected()) {
            deviDisattivareDiDoppioSei.setVisible(true);
            ret = false;
        }else deviDisattivareDiDoppioSei.setVisible(false);
        if(ulterioriCarte.isSelected() && !casellePescaCarta.isSelected()){
            deviAttivare.setVisible(true);
            ret = false;
        }else deviAttivare.setVisible(false);
        return ret;
    }
}
