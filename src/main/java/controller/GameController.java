package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Sistema;
import model.elementi.Casella.Tipo;
import model.elementi.Casella;
import model.elementi.Mezzi.Mezzo;
import model.elementi.Mezzi.TipoMezzo;
import model.elementi.Tabellone;
import util.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GameController {

    private Timeline timeline;
    private HashMap<Tipo,Color> coloriCaselle;
    private StackPane[] giocatori;
    private int operazione, r, c;
    private Pane overlayPane;
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
    private TextField textFieldTurno, textFieldLancioDadi, textFieldCasellaCorrente,textFieldPremio, textFieldSosta,textFieldCartaPescata;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ColumnConstraints column;

//--------------------------------------------SETTING--------------------------------------------


    public void setSistema(Sistema sistema){this.sistema=sistema;}

    public void initGame() {
        operazione=-1;
        giocatori = new StackPane[sistema.getNPedine()];

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Tabellone t = sistema.getTabellone();
        r=t.getR();
        c=t.getC();
        coloriCaselle = new HashMap<>();
        Util.initColoriCaselle(coloriCaselle);

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

        overlayPane = new Pane();
        overlayPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        overlayPane.setMouseTransparent(true);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(tabellone,overlayPane);

        vbox.getChildren().clear();
        vbox.getChildren().addAll(stackPane,gridComandi);
        VBox.setVgrow(gridComandi,Priority.NEVER);
        VBox.setVgrow(stackPane,Priority.ALWAYS);
        tabellone.widthProperty().addListener((obs, oldVal, newVal) -> disegnaScaleESerpenti());
        tabellone.heightProperty().addListener((obs, oldVal, newVal) -> disegnaScaleESerpenti());

        disegnaScaleESerpenti();

        timeline.setCycleCount(Timeline.INDEFINITE);
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
        label = new Label();
        label.setStyle("-fx-background-color:" + Util.toHexString(coloriCaselle.get(tipo)) + ";-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 5;");
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Dimensione massima per adattarsi
        label.setAlignment(Pos.CENTER);
        GridPane.setRowIndex(label, i);
        GridPane.setColumnIndex(label, j);
        tabellone.add(label,jPos(j),iPos(i));
    }

    private void disegnaScaleESerpenti() {
        Tabellone t = sistema.getTabellone();
        List<Mezzo> mezzi = sistema.getMezzi();

        double cellWidth = tabellone.getWidth() / c;
        double cellHeight = tabellone.getHeight() / r;

        overlayPane.getChildren().clear();

        for (Mezzo mezzo : mezzi) {
            int xFrom = t.getPosCasella(mezzo.getFrom()).getX();
            int yFrom = t.getPosCasella(mezzo.getFrom()).getY();
            int xTo = t.getPosCasella(mezzo.getTo()).getX();
            int yTo = t.getPosCasella(mezzo.getTo()).getY();

            Line line = creaLinea(
                    jPos(yFrom) * cellWidth + cellWidth / 2,
                    iPos(xFrom) * cellHeight + cellHeight / 2,
                    jPos(yTo) * cellWidth + cellWidth / 2,
                    iPos(xTo) * cellHeight + cellHeight / 2,
                    mezzo.getTipo()
            );
            overlayPane.getChildren().add(line); // Usa overlayPane per aggiungere le linee
        }
    }


    public Line creaLinea(double x1, double y1, double x2, double y2, TipoMezzo tipoMezzo) {
        Line line = new Line();
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
        line.setStrokeWidth(2);
        line.setStroke(tipoMezzo==TipoMezzo.SERPENTE?Color.GREEN:Color.BROWN);
        return line;
    }


//--------------------------------------------UTIL--------------------------------------------

    private void eseguiOperazione() throws IOException{
        if(sistema.redo())
            return;
        operazione++;
        switch (operazione % 4) {
            case 0:
                sistema.prossimoTurno();
                break;
            case 1:
                sistema.lancia();
                break;
            case 2:
                sistema.avanza();
                break;
            case 3:
                if(sistema.azionaCasella()){
                    vittoria(new ActionEvent());
                    stop(new ActionEvent());
                }
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
        if(sistema.isPescaCarta())
            textFieldCartaPescata.setText(sistema.getCartaPescata()==null?"":sistema.getCartaPescata().name());
        if(sistema.isCaselleSosta())
            textFieldSosta.setText(sistema.getUltimaSosta()==null?"":sistema.getUltimaSosta());
        if(sistema.isCasellePremio())
            textFieldPremio.setText(sistema.getUltimoPremio()==null?"":sistema.getUltimoPremio());
        textFieldTurno.setText(""+(turno+1));
        textFieldLancioDadi.setText(""+sistema.getLancio());
        textFieldCasellaCorrente.setText(""+sistema.getCasellaCorrente().getTipo().toString());

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
