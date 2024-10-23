package controller;

import DB.ConfigurazioneDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import model.Sistema;
import util.Util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SettingNumeroCaselleSpecialiController  implements Controller{
    @FXML
    private Sistema sistema;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private GridPane gridVarianti;
    @FXML
    private TextField caselleSosta, casellePremio, casellePescaCarta;
    @FXML
    private Button invia;
    @FXML
    private Label avvisoNumeroNonIdoneo;
    private int i;
    private boolean salvato;
    private ConfigurazioneDAO config;
    private List<Label> avvisiEVuota;

    @Override
    public void init(Sistema sistema,Stage stage){
        this.sistema=sistema;
        this.stage = stage;
    }

    public void init() throws IOException{
        gridVarianti.getColumnConstraints().clear();
        gridVarianti.getRowConstraints().clear();
        ColumnConstraints c = new ColumnConstraints();
        c.setPercentWidth(30);
        gridVarianti.getColumnConstraints().add(c);
        c.setPercentWidth(70);
        gridVarianti.getColumnConstraints().add(c);

        avvisiEVuota = new LinkedList<>();
        avvisoNumeroNonIdoneo.setVisible(false);

        i=0;
        caselleSosta = addVariante(sistema.isCaselleSosta(),"Caselle sosta");
        casellePremio = addVariante(sistema.isCasellePremio(),"Caselle premio");
        casellePescaCarta = addVariante(sistema.isPescaCarta(),"Casella pesca carta");

        if(caselleSosta!=null)Util.setTextFormatter(caselleSosta);
        if(casellePremio!=null)Util.setTextFormatter(casellePremio);
        if(casellePescaCarta!=null)Util.setTextFormatter(casellePescaCarta);

        gridVarianti.setPrefSize(Double.MAX_VALUE,Double.MAX_VALUE);

        salvato = false;
        config = new ConfigurazioneDAO(this.sistema);
    }

    private TextField addVariante(boolean flag, String s) {
        if(!flag)
            return null;
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(30);
        gridVarianti.getRowConstraints().add(rowConstraints);

        Label label = new Label("Numero di "+s);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.setAlignment(Pos.BASELINE_LEFT);
        gridVarianti.add(label,0,i);
        TextField textField = new TextField();
        textField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        textField.setAlignment(Pos.BASELINE_CENTER);
        textField.setMaxSize(100,30);
        gridVarianti.add(textField,1,i);
        label = new Label("E' vuota!");
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.setAlignment(Pos.BASELINE_LEFT);
        label.setVisible(false);
        gridVarianti.add(label,2,i);
        avvisiEVuota.add(label);
        i++;

        return textField;
    }

    public void inviaNumeroCaselleSpeciali(ActionEvent e) throws IOException {
        if(!setCaselle())
            return;
        sistema.beginSettingCaselleSpeciali();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Info.fxml"));
        root = loader.load();
        InfoController infoController = loader.getController();
        infoController.init(this.sistema,this.stage);
        Util.changeScene("Informazioni",root,stage,null,null,scene);
    }

    private boolean setCaselle() {
        boolean ret = true;
        int i = -1;
        sistema.initCaselleSpeciali();
        if (sistema == null)
            throw new IllegalArgumentException("SettingNumeroCaselleSpecialiController: sistema ancora non istanziato");
        if (sistema.isCaselleSosta()) {
            i++;
            if (caselleSosta.getText().trim().isEmpty()) {
                ret = false;
                avvisiEVuota.get(i).setVisible(true);
            } else {
                avvisiEVuota.get(i).setVisible(false);
                sistema.setNumberCaselleSpeciali(Util.CaselleSpeciali.SOSTA, Integer.parseInt(caselleSosta.getText()));
            }
        }
        if (sistema.isCasellePremio()) {
            i++;
            if (casellePremio.getText().trim().isEmpty()) {
                ret = false;
                avvisiEVuota.get(i).setVisible(true);
            } else {
                avvisiEVuota.get(i).setVisible(false);
                sistema.setNumberCaselleSpeciali(Util.CaselleSpeciali.PREMIO,Integer.parseInt(casellePremio.getText()));
            }
        }
        if (sistema.isPescaCarta()) {
            i++;
            if (casellePescaCarta.getText().trim().isEmpty()) {
                ret = false;
                avvisiEVuota.get(i).setVisible(true);
            } else {
                avvisiEVuota.get(i).setVisible(false);
                sistema.setNumberCaselleSpeciali(Util.CaselleSpeciali.PESCA,Integer.parseInt(casellePescaCarta.getText()));
            }
        }
        try{
            sistema.controlNumberCaselleSpeciali();
            avvisoNumeroNonIdoneo.setVisible(false);
        }catch(IllegalArgumentException e){
            avvisoNumeroNonIdoneo.setVisible(true);
            ret=false;
        }
        return ret;
    }


    public void salva(ActionEvent e) throws IOException{
        if(!setCaselle())
            return;
        config = new ConfigurazioneDAO(this.sistema);
        config.save();
    }

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
    public void indietro(ActionEvent e) throws IOException {
        Util.indietro("Seleziona Varianti","/view/SettingVarianti.fxml",this);
    }
}
