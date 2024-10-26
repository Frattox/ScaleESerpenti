package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.sistema.Sistema;
import model.elementi.Casella;
import model.elementi.Mezzi.TipoMezzo;
import util.Util;

import java.io.IOException;
import java.util.HashMap;

public class InfoController implements Controller{

    private Sistema sistema;
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private GridPane gridPane;
    @FXML
    private Line scala,serpente;

    @Override
    public void init(Sistema sistema,Stage stage){
        this.stage=stage;
        this.sistema=sistema;
        int i=0;
        gridPane.getChildren().clear();
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HashMap<Casella.Tipo, Color> coloriCaselle = new HashMap<>();
        Util.initColoriCaselle(coloriCaselle);
        int lato = 50;
        for(Casella.Tipo tipo: coloriCaselle.keySet()){
            Rectangle quadrato = new Rectangle(lato,lato);
            quadrato.setFill(coloriCaselle.get(tipo));
            gridPane.setAlignment(Pos.CENTER);
            gridPane.add(quadrato,0,i);
            Label label = new Label("Casella "+tipo.name());
            gridPane.add(label,1,i);
            i++;
        }
        gridPane.add(scala,0,i);
        scala.setStroke(Color.BROWN);
        Label label = new Label("Casella "+ TipoMezzo.SCALA);
        gridPane.add(label,1,i);
        i++;

        gridPane.add(serpente,0,i);
        serpente.setStroke(Color.GREEN);
        label = new Label("Casella "+ TipoMezzo.SERPENTE);
        gridPane.add(label,1,i);

        Util.initGrid(gridPane);
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

    public void inizia(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
        root = loader.load();
        GameController gameController = loader.getController();
        gameController.init(sistema,stage);

        Util.changeScene("Scale e Serpenti",root,stage,null,null,scene);
    }
}
