package controller;

import DB.ConfigurazioneDAO;
import DB.ConnectConfigurazioneDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

public class SettingNumeroCaselleSpecialiController {
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
    private int i;
    private boolean salvato;
    private ConfigurazioneDAO config;

    public void setSistema(Sistema sistema){this.sistema=sistema;}

    public void init() throws IOException{
        gridVarianti.getColumnConstraints().clear();
        gridVarianti.getRowConstraints().clear();
        ColumnConstraints c = new ColumnConstraints();
        c.setPercentWidth(30);
        gridVarianti.getColumnConstraints().add(c);
        c.setPercentWidth(70);
        gridVarianti.getColumnConstraints().add(c);

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
        i++;

        return textField;
    }

    public void inviaNumeroCaselleSpeciali(ActionEvent e) throws IOException {
        if(!setCaselle())
            return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Info.fxml"));
        root = loader.load();
        InfoController infoController = loader.getController();
        infoController.init(this.sistema);

        Util.changeScene(e,"Informazioni",root,stage,scene);
    }

    private boolean setCaselle() {
        boolean ret = true;
        final int n = 3;
        int i = 1;
        if (sistema == null)
            throw new IllegalArgumentException("SettingController: sistema ancora non istanziato");
        if (sistema.isCaselleSosta())
            if (caselleSosta.getText().isEmpty()) {
                ret = false;
                gridVarianti.getChildren().get((n * i) - 1).setVisible(true);
            } else {
                gridVarianti.getChildren().get((n * i) - 1).setVisible(false);
                sistema.setNumberCaselleSosta(Integer.parseInt(caselleSosta.getText()));
            }
        i++;
        if (sistema.isCasellePremio())
            if (casellePremio.getText().isEmpty()) {
                ret = false;
                gridVarianti.getChildren().get((n * i) - 1).setVisible(true);
            } else {
                gridVarianti.getChildren().get((n * i) - 1).setVisible(false);
                sistema.setNumberCasellePremio(Integer.parseInt(casellePremio.getText()));
            }
        i++;
        if (sistema.isPescaCarta())
            if (casellePescaCarta.getText().isEmpty()) {
                ret = false;
                gridVarianti.getChildren().get((n * i) - 1).setVisible(true);
            } else {
                gridVarianti.getChildren().get((n * i) - 1).setVisible(false);
                sistema.setNumberCasellePescaCarta(Integer.parseInt(casellePescaCarta.getText()));
            }
        return ret;
    }


    public void salva(ActionEvent e) throws IOException{

    }

}
