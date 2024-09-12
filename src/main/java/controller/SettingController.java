package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Sistema;
import model.SistemaImpl1;
import model.elementi.Mezzi.TipoMezzo;

import java.io.IOException;

public class SettingController {
    @FXML
    private Sistema sistema;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private TextField righe,colonne,numeroGiocatori,scale,serpenti;
    @FXML
    private CheckBox dadoSingolo,dadoSingoloFinale,doppioSei,caselleSosta,casellePremio,casellePescaCarta,ulterioriCarte;


    public void inviaSettingIniziale(ActionEvent event) throws IOException
    {
        if(isEmpty(righe) || isEmpty(colonne) || isEmpty(numeroGiocatori) || isEmpty(scale) || isEmpty(serpenti))
            return;
        sistema = new SistemaImpl1();
        int r = Integer.parseInt(righe.getText());
        int c = Integer.parseInt(colonne.getText());
        int numGiocatori = Integer.parseInt(numeroGiocatori.getText());
        int numScale = Integer.parseInt(scale.getText());
        int numSerpenti = Integer.parseInt(serpenti.getText());
        sistema.setTabellone(r,c);
        sistema.setNPedine(numGiocatori);
        sistema.setNumberMezzi(TipoMezzo.SCALA,numScale);
        sistema.setNumberMezzi(TipoMezzo.SERPENTE,numSerpenti);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SettingVarianti.fxml"));
        root = loader.load();
        SettingVariantiController controllerVarianti = loader.getController();
        controllerVarianti.setSistema(sistema);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Seleziona Varianti");
        stage.show();
    }

    private boolean isEmpty(TextField t){return t.getText().trim().isEmpty();}
}
