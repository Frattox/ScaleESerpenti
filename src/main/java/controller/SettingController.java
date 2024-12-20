package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.sistema.Sistema;
import model.sistema.SistemaImpl1;
import model.elementi.Mezzi.TipoMezzo;
import util.Util;

import java.io.IOException;

public class SettingController  implements Controller{
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
    private Label righeInadeguate, colonneInadeguate, giocatoriInadeguati, scaleInadeguate, serpentiInadeguati;
    @FXML
    private GridPane gridSetting;
    @FXML
    private VBox vboxAvvisi;

    @Override
    public void init(Sistema sistema,Stage stage){

        this.stage = stage;
        Util.initGrid(gridSetting);
        for (Node child : gridSetting.getChildren()) {
            TextField t;
            if(child.getClass()==TextField.class){
                t = (TextField) child;
                Util.setTextFormatter(t);
            }
            GridPane.setHgrow(child, Priority.ALWAYS);
            GridPane.setVgrow(child, Priority.ALWAYS);
        }

        vboxAvvisi.setAlignment(Pos.TOP_LEFT);
        vboxAvvisi.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        for(Node child: vboxAvvisi.getChildren()){
            ((Control)child).setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            VBox.setVgrow(child,Priority.ALWAYS);
        }

    }

    public void inviaSettingIniziale(ActionEvent e) throws IOException {
        if(isEmpty(righe) || isEmpty(colonne) || isEmpty(numeroGiocatori) || isEmpty(scale) || isEmpty(serpenti))
            return;
        sistema = new SistemaImpl1();
        boolean verifica = true;
        int r = Integer.parseInt(righe.getText());
        int c = Integer.parseInt(colonne.getText());
        try{
            sistema.setTabellone(r,c);
            colonneInadeguate.setVisible(false);
            righeInadeguate.setVisible(false);
        }catch (IllegalArgumentException event){
                colonneInadeguate.setVisible(true);
                righeInadeguate.setVisible(true);
                return;
        }

        int numGiocatori = Integer.parseInt(numeroGiocatori.getText());
        try{
            sistema.setNPedine(numGiocatori);
            giocatoriInadeguati.setVisible(false);
        }catch (IllegalArgumentException event){
            giocatoriInadeguati.setVisible(true);
            verifica = false;
        }

        int numScale = Integer.parseInt(scale.getText());
        try{
            sistema.setNumberMezzi(TipoMezzo.SCALA,numScale);
            scaleInadeguate.setVisible(false);
        }catch (IllegalArgumentException event){
            scaleInadeguate.setVisible(true);
            verifica = false;
        }

        int numSerpenti = Integer.parseInt(serpenti.getText());
        try{
            sistema.setNumberMezzi(TipoMezzo.SERPENTE,numSerpenti);
            serpentiInadeguati.setVisible(false);
        }catch (IllegalArgumentException event){
            serpentiInadeguati.setVisible(true);
            verifica = false;
        }
        if(!verifica) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SettingVarianti.fxml"));
        root = loader.load();
        SettingVariantiController controllerVarianti = loader.getController();
        controllerVarianti.init(sistema,stage);

        Util.changeScene("Seleziona Varianti",root,stage,null,null,scene);
    }

    public void indietro(ActionEvent e) throws IOException {
        Util.indietro("Home","/view/Home.fxml",this);
    }

    private boolean isEmpty(TextField t){return t.getText().trim().isEmpty();}

    @Override
    public Sistema getSistema() {
        return sistema;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
