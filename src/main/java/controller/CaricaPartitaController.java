package controller;

import DB.ConfigurazioneDAO;
import DB.ConfigurazioneGioco;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Sistema;
import model.SistemaImpl1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CaricaPartitaController {

    @FXML
    private Stage stage;
    @FXML
    private TableView<ConfigurazioneGioco> tableView;
    private ConfigurazioneDAO configurazioneDAO;
    private Sistema sistema;

    public void init(Stage stage) {
        this.sistema = new SistemaImpl1();
        this.stage=stage;
        configurazioneDAO = new ConfigurazioneDAO(this.sistema);

        //prendere dal DB le configurazioni e settarle
        List<ConfigurazioneGioco> configurazioni = configurazioneDAO.getAll();
        ObservableList<ConfigurazioneGioco> observableConfigurazioni = FXCollections.observableArrayList(configurazioni);
        tableView.setItems(observableConfigurazioni);

        //Creazione colonne
        initColumns();

        tableView.setEditable(false);
    }

    private void initColumns(){

        List<TableColumn<ConfigurazioneGioco,String>> tableColumnList = new LinkedList<>();

        String[] nomiColonne = {
                "NumeroRighe",
                "NumeroColonne",
                "NumeroGiocatori",
                "NumeroScale",
                "NumeroSerpenti",
                "VarianteDadoSingolo",
                "VarianteDadoSingoloFinale",
                "VarianteDoppioSei",
                "VarianteCaselleSosta",
                "VarianteCasellePremio",
                "VariantePescaCarta",
                "VarianteUlterioriCarte",
                "NumeroCaselleSosta",
                "NumeroCasellePremio",
                "NumeroCasellePescaCarta"
        };
        String[] nomiProprieta = {
                "numeroRighe",
                "numeroColonne",
                "numeroGiocatori",
                "numeroScale",
                "numeroSerpenti",
                "varianteDadoSingolo",
                "varianteDadoSingoloFinale",
                "varianteDoppioSei",
                "varianteCaselleSosta",
                "varianteCasellePremio",
                "variantePescaCarta",
                "varianteUlterioriCarte",
                "numeroCaselleSosta",
                "numeroCasellePremio",
                "numeroCasellePescaCarta"
        };
        for(int i=0;i<nomiProprieta.length;i++){
            TableColumn<ConfigurazioneGioco,String> column = new TableColumn<>(nomiColonne[i]);
            column.setCellValueFactory(new PropertyValueFactory<>(nomiProprieta[i]));
            tableColumnList.add(column);
        }
        tableView.getColumns().addAll(tableColumnList);
    }
}
