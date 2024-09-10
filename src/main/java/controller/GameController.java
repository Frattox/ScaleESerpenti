package controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Sistema;
import model.elementi.Casella.Tipo;
import model.elementi.Casella;
import model.elementi.Tabellone;

import java.util.HashMap;

public class GameController {

    private HashMap<Tipo,String> coloriCaselle;

    @FXML
    private Sistema sistema;
    @FXML
    private GridPane tabellone;

//--------------------------------------------SETTING--------------------------------------------


    public void setSistema(Sistema sistema){this.sistema=sistema;}

    public void initGame() {
        Tabellone t = sistema.getTabellone();
        int r = t.getR(), c = t.getC();
        coloriCaselle = new HashMap<>();
        initColoriCaselle(coloriCaselle);

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

        setTabellone(t,r,c);

        tabellone.setPrefSize(Double.MAX_VALUE,Double.MAX_VALUE);
        for (int i = 0; i < sistema.getNPedine(); i++) {
            addPedina(i, 0, 0); // Posiziona tutte le pedine sulla casella di partenza (0,0)
        }
    }

    private void initColoriCaselle(HashMap<Casella.Tipo,String> coloriCaselle) {
        coloriCaselle.put(Tipo.NORMALE,toHexString(Color.GRAY));
        coloriCaselle.put(Tipo.MEZZO_FROM,toHexString(Color.YELLOW));
        coloriCaselle.put(Tipo.MEZZO_TO,toHexString(Color.ORANGE));
        coloriCaselle.put(Tipo.LOCANDA,toHexString(Color.BROWN));
        coloriCaselle.put(Tipo.PANCHINA,toHexString(Color.SANDYBROWN));
        coloriCaselle.put(Tipo.DADI,toHexString(Color.GREEN));
        coloriCaselle.put(Tipo.MOLLA,toHexString(Color.BLUE));
        coloriCaselle.put(Tipo.PESCA,toHexString(Color.CYAN));
    }

    private static String toHexString(Color color) {
        return String.format( "#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)
        );
    }

    private void addPedina(int nPedina, int i, int j){
        StackPane pedina = new StackPane();

        Circle circle = new Circle(15);
        circle.setFill(getPlayerColor(nPedina));
        circle.setStroke(Color.BLACK);

        Label numeroGiocatore = new Label(String.valueOf(nPedina + 1));
        numeroGiocatore.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        pedina.getChildren().addAll(circle, numeroGiocatore);
        pedina.setAlignment(Pos.CENTER);

        tabellone.add(pedina,i,j);
    }

    private Color getPlayerColor(int nPedina) {
        return switch (nPedina % 4) {
            case 0 -> Color.RED;
            case 1 -> Color.BLUE;
            case 2 -> Color.GREEN;
            case 3 -> Color.YELLOW;
            default -> Color.BLACK;
        };
    }

    private void setTabellone(Tabellone t, int r, int c){
        Label label;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Tipo tipo = t.getCasella(i,j).getTipo();
                label = new Label(tipo.toString());
                label.setStyle("-fx-background-color:" + coloriCaselle.get(tipo) + ";");
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Dimensione massima per adattarsi
                label.setAlignment(Pos.CENTER);
                GridPane.setRowIndex(label, i);
                GridPane.setColumnIndex(label, j);
                tabellone.add(label,j,i);
            }
        }
    }

//--------------------------------------------GAME--------------------------------------------



}
