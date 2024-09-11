package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Sistema;

public class VittoriaController{


    @FXML
    private Sistema sistema;
    @FXML
    private VBox vbox;
    @FXML
    private Text vittoria, haVinto;

    public void setSistema(Sistema sistema){this.sistema=sistema;}

    public void initVittoria(){
        vbox.setStyle("-fx-background-color: "+GameController.toHexString(Color.BLUEVIOLET));
        haVinto.setText("Ha vinto il giocatore: "+sistema.getVincitore());
        haVinto.setFill(Color.GOLD);
        haVinto.setStroke(Color.BLACK);
        haVinto.setStrokeWidth(2);
        vittoria.setFill(Color.GOLD);
        vittoria.setStroke(Color.BLACK);
        vittoria.setStrokeWidth(2);

    }

}
