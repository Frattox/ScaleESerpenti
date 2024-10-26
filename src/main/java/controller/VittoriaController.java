package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.sistema.Sistema;
import util.Util;

public class VittoriaController  implements Controller{


    @FXML
    private Sistema sistema;
    @FXML
    private VBox vbox;
    @FXML
    private Text vittoria, haVinto;

    public void init(Sistema sistema, Stage stage){
        this.sistema=sistema;
    }

    public void initVittoria(){
        vbox.setStyle("-fx-background-color: "+ Util.toHexString(Color.BLUEVIOLET));
        haVinto.setText("Ha vinto il giocatore: "+sistema.getVincitore());
        haVinto.setFill(Color.GOLD);
        haVinto.setStroke(Color.BLACK);
        haVinto.setStrokeWidth(2);
        vittoria.setFill(Color.GOLD);
        vittoria.setStroke(Color.BLACK);
        vittoria.setStrokeWidth(2);

    }

    @Override
    public Sistema getSistema() {
        return sistema;
    }

    @Override
    public Stage getStage() {
        return null;
    }

    @Override
    public Scene getScene() {
        return null;
    }
}
