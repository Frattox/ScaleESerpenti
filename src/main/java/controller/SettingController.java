package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
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
    @FXML
    private GridPane gridSetting;

    public void setting(){
        double x = gridSetting.getPrefHeight()/gridSetting.getChildren().size();

        double rowHeight = gridSetting.getPrefHeight() / gridSetting.getRowCount();
        double colWidth = gridSetting.getPrefWidth() / gridSetting.getColumnCount();

        gridSetting.getRowConstraints().clear();
        gridSetting.getColumnConstraints().clear();
//  vincoli di riga
        for (int i = 0; i < gridSetting.getRowCount(); i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / gridSetting.getRowCount()); // Ogni riga occuperà una percentuale uguale
            gridSetting.getRowConstraints().add(row);
        }

//  vincoli di colonna
        for (int i = 0; i < gridSetting.getColumnCount(); i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / gridSetting.getColumnCount()); // Ogni colonna occuperà una percentuale uguale
            gridSetting.getColumnConstraints().add(column);
        }

// Imposta le proprietà dei figli
        for (Node child : gridSetting.getChildren()) {
            GridPane.setHgrow(child, Priority.ALWAYS);  // Crescita orizzontale
            GridPane.setVgrow(child, Priority.ALWAYS);  // Crescita verticale
        }

    }

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
