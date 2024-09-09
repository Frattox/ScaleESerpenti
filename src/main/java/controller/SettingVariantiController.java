package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import model.Sistema;

public class SettingVariantiController {
    @FXML
    private Sistema sistema;
    @FXML
    private CheckBox dadoSingolo,dadoSingoloFinale,doppioSei,caselleSosta,casellePremio,casellePescaCarta,ulterioriCarte;


    public void setSistema(Sistema sistema){
        this.sistema=sistema;
    }
    public void inviaSettingVarianti(ActionEvent e){
        if(sistema==null)
            throw new IllegalArgumentException("SettingController: sistema ancora non istanziato");
        sistema.setDadoSingolo(dadoSingolo.isSelected());
        sistema.setDadoSingoloFinale(dadoSingoloFinale.isSelected());
        sistema.setDoppioSei(doppioSei.isSelected());
        sistema.setCaselleSosta(caselleSosta.isSelected());
        sistema.setCasellePremio(casellePremio.isSelected());
        sistema.setPescaCarta(casellePescaCarta.isSelected());
        sistema.setUlterioriCarte(ulterioriCarte.isSelected());
    }
}
