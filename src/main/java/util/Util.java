package util;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.elementi.Casella;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.UnaryOperator;

public class Util {
    public static void setTextFormatter(TextField t) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Regex per numeri
                return change;
            }
            return null; // Rifiuta il cambiamento
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        t.setTextFormatter(textFormatter);
    }

    public static String toHexString(Color color) {
        return String.format( "#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)
        );
    }

    public static void initColoriCaselle(HashMap<Casella.Tipo,Color> coloriCaselle) {
        coloriCaselle.put(Casella.Tipo.NORMALE,Color.LIGHTGRAY);
        coloriCaselle.put(Casella.Tipo.MEZZO_FROM,Color.YELLOW);
        coloriCaselle.put(Casella.Tipo.MEZZO_TO,Color.ORANGE);
        coloriCaselle.put(Casella.Tipo.LOCANDA,Color.BROWN);
        coloriCaselle.put(Casella.Tipo.PANCHINA,Color.SANDYBROWN);
        coloriCaselle.put(Casella.Tipo.DADI,Color.GREEN);
        coloriCaselle.put(Casella.Tipo.MOLLA,Color.BLUE);
        coloriCaselle.put(Casella.Tipo.PESCA,Color.CYAN);
        coloriCaselle.put(Casella.Tipo.INIZIO,Color.LIGHTGREEN);
        coloriCaselle.put(Casella.Tipo.FINE,Color.LIGHTGREEN);
    }

    public static void initGrid(GridPane gridPane){
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();
//  vincoli di riga
        for (int i = 0; i < gridPane.getRowCount(); i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / gridPane.getRowCount()); // Ogni riga occuperà una percentuale uguale
            gridPane.getRowConstraints().add(row);
        }

//  vincoli di colonna
        for (int i = 0; i < gridPane.getColumnCount(); i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / gridPane.getColumnCount()); // Ogni colonna occuperà una percentuale uguale
            gridPane.getColumnConstraints().add(column);
        }
    }

    public static void changeScene(String title, Parent root, Stage stage, Double h, Double w, Scene scene){
        if(h!=null && w!=null){
            stage.setMinWidth(w);
            stage.setMinHeight(h);
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    public static void indietro(String name, String path, Controller controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(path));
        Parent root = loader.load();
        Controller controllerIndietro = loader.getController();
        Stage stage = controller.getStage();
        Scene scene = controller.getScene();
        controllerIndietro.init(controller.getSistema(), controller.getStage());
        Util.changeScene(name,root,stage,null,null,scene);
    }

    public enum CaselleSpeciali{
        SOSTA,
        PREMIO,
        PESCA
    }
}
