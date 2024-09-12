package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Sistema;
import model.elementi.Casella.Tipo;
import model.elementi.Casella;
import model.elementi.Tabellone;

import java.io.IOException;
import java.util.HashMap;

public class GameController {

    private Timeline timeline;
    private HashMap<Tipo,String> coloriCaselle;
    private StackPane[] giocatori;
    private int operazione, r, c;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private GridPane gridComandi, tabellone;
    @FXML
    private Sistema sistema;
    @FXML
    private VBox vbox;
    @FXML
    private Label labelTurno, labelLancioDadi, labelCasellaCorrente;

//--------------------------------------------SETTING--------------------------------------------


    public void setSistema(Sistema sistema){this.sistema=sistema;}

    public void initGame() {
        operazione=-1;

        giocatori = new StackPane[sistema.getNPedine()];

        Tabellone t = sistema.getTabellone();
        r=t.getR() ;
        c=t.getC();
        coloriCaselle = new HashMap<>();
        initColoriCaselle(coloriCaselle);

        tabellone.getColumnConstraints().clear();
        tabellone.getRowConstraints().clear();

        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        tabellone.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        gridComandi.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        VBox.setVgrow(tabellone,Priority.ALWAYS);
        VBox.setVgrow(gridComandi,Priority.ALWAYS);

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

        for (int i = 0; i < sistema.getNPedine(); i++) {
            addPedina(i, 0, 0); // Posiziona tutte le pedine sulla casella di partenza (0,0)
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
            try {
                avanti();
            } catch (IOException e) {
                System.out.println("Forward problem.");
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void initColoriCaselle(HashMap<Casella.Tipo,String> coloriCaselle) {
        coloriCaselle.put(Tipo.NORMALE,toHexString(Color.LIGHTGRAY));
        coloriCaselle.put(Tipo.MEZZO_FROM,toHexString(Color.YELLOW));
        coloriCaselle.put(Tipo.MEZZO_TO,toHexString(Color.ORANGE));
        coloriCaselle.put(Tipo.LOCANDA,toHexString(Color.BROWN));
        coloriCaselle.put(Tipo.PANCHINA,toHexString(Color.SANDYBROWN));
        coloriCaselle.put(Tipo.DADI,toHexString(Color.GREEN));
        coloriCaselle.put(Tipo.MOLLA,toHexString(Color.BLUE));
        coloriCaselle.put(Tipo.PESCA,toHexString(Color.CYAN));
        coloriCaselle.put(Tipo.INIZIO,toHexString(Color.LIGHTGREEN));
        coloriCaselle.put(Tipo.FINE,toHexString(Color.LIGHTGREEN));
    }

    public static String toHexString(Color color) {
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

        giocatori[nPedina]=pedina;
        tabellone.add(pedina,jPos(j),iPos(i));
    }

    //tabellone invertito
    private int iPos(int i){return r-i-1;}
    private int jPos(int j){return c-j-1;}

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
        Label label = new Label(), labelPrimo=new Label(),labelUltimo=new Label();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Tipo tipo = t.getCasella(i,j).getTipo();
                addLabel(label,tipo,i,j);
                if(i==0 && j==0)
                    labelPrimo = label;
                if(i==r-1 && j==c-1)
                    labelUltimo = label;
            }
        }
        tabellone.getChildren().remove(labelPrimo);
        addLabel(labelPrimo,Tipo.INIZIO,0,0);
        tabellone.getChildren().remove(labelUltimo);
        addLabel(labelUltimo,Tipo.FINE,r-1,c-1);
    }

    private void addLabel(Label label, Tipo tipo, int i, int j){
        label = new Label(tipo.toString());
        label.setStyle("-fx-background-color:" + coloriCaselle.get(tipo) + ";-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 5;");
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Dimensione massima per adattarsi
        label.setAlignment(Pos.CENTER);
        GridPane.setRowIndex(label, i);
        GridPane.setColumnIndex(label, j);
        tabellone.add(label,jPos(j),iPos(i));
    }

//--------------------------------------------UTIL--------------------------------------------

    private void eseguiOperazione() throws IOException{
        if(sistema.redo())
            return;
        operazione++;
        switch (operazione % 4) {
            case 0:
                sistema.prossimoTurno();
                //da rimuovere
                System.out.println("prox turno");
                break;
            case 1:
                sistema.lancia();
                //da rimuovere
                System.out.println("lancia");
                break;
            case 2:
                sistema.avanza();
                //da rimuovere
                System.out.println("avanza");
                break;
            case 3:
                if(sistema.azionaCasella()){
                    vittoria(new ActionEvent());
                    stop(new ActionEvent());
                }
                //da rimuovere
                System.out.println("aziona");
                break;
            default:break;
        }
    }

    private void vittoria(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Vittoria.fxml"));
        root = loader.load();
        VittoriaController vittoriaController = loader.getController();
        vittoriaController.setSistema(sistema);
        vittoriaController.initVittoria();
        stage = (Stage) vbox.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void repaint(){
        int turno = sistema.getTurno();
        labelTurno.setText("Turno:"+(turno+1));
        labelLancioDadi.setText("Lancio Dadi:"+sistema.getLancio());
        labelCasellaCorrente.setText("Casella Corrente:"+sistema.getCasellaCorrente().getTipo().toString());

        Casella casellaTo = sistema.getCasellaCorrente();
        tabellone.getChildren().remove(giocatori[turno]);
        int i = sistema.getTabellone().getPosCasella(casellaTo).getX();
        int j = sistema.getTabellone().getPosCasella(casellaTo).getY();
        tabellone.add(giocatori[turno],jPos(j),iPos(i));
    }

    private void avanti() throws IOException{
        eseguiOperazione();
        repaint();
    }

//--------------------------------------------GAME--------------------------------------------

    public void forward(ActionEvent e) throws IOException{
        stop(e);
        avanti();
    }

    public void backward(ActionEvent e) throws IOException{
        stop(e);
        if(operazione<=0)
            return;
        if(sistema.undo())
            repaint();
    }

    public void play(ActionEvent e) throws IOException{
        timeline.play();
    }

    public void stop(ActionEvent e) throws IOException{
        timeline.stop();
    }



}
