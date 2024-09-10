package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.Sistema;
import model.elementi.Casella;
import model.elementi.Tabellone;

public class GameController {

    @FXML
    private Sistema sistema;
    @FXML
    private GridPane tabellone;

    public void setSistema(Sistema sistema){this.sistema=sistema;}

    public void initGame() {
        Tabellone t = sistema.getTabellone();
        Casella casella;
        Label label;

        int r = t.getR(), c = t.getC();

        tabellone.getColumnConstraints().clear();
        tabellone.getRowConstraints().clear();

        //vincoli di colonna
        for (int i = 0; i < c; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / c); // Colonne di larghezza percentuale
            tabellone.getColumnConstraints().add(columnConstraints);
        }

        //vincoli di riga
        for (int i = 0; i < r; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / r); // Righe di altezza percentuale
            tabellone.getRowConstraints().add(rowConstraints);
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                casella = t.getCasella(i, j);
                label = new Label("A");
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Dimensione massima per adattarsi
                GridPane.setRowIndex(label, i);
                GridPane.setColumnIndex(label, j);
                tabellone.add(label, i,j);

            }
        }
        tabellone.setPrefSize(Double.MAX_VALUE,Double.MAX_VALUE);

    }

}
