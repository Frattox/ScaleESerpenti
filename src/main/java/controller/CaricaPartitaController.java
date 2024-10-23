package controller;

import DB.ConfigurazioneDAO;
import DB.ConfigurazioneGioco;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Sistema;
import model.SistemaImpl1;
import model.elementi.Mezzi.TipoMezzo;
import util.Util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CaricaPartitaController {

    @FXML
    private Scene scene;
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

    public void elimina(ActionEvent e){
        if(tableView.getSelectionModel().isEmpty())
            return;
        int id = tableView.getSelectionModel().getSelectedIndex();
        ConfigurazioneGioco configurazioneGioco = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(id);
        configurazioneDAO.deleteById(Integer.parseInt(configurazioneGioco.getId()));

    }

    public void carica(ActionEvent e) throws IOException {
        ConfigurazioneGioco configurazioneGioco = tableView.getSelectionModel().getSelectedItem();
        sistema.setTabellone(
                Integer.parseInt(configurazioneGioco.getNumeroRighe()),
                Integer.parseInt(configurazioneGioco.getNumeroColonne())
        );
        sistema.setNPedine(
                Integer.parseInt(configurazioneGioco.getNumeroGiocatori())
        );
        sistema.setNumberMezzi(
                TipoMezzo.SCALA,
                Integer.parseInt(configurazioneGioco.getNumeroScale())
        );
        sistema.setNumberMezzi(
                TipoMezzo.SERPENTE,
                Integer.parseInt(configurazioneGioco.getNumeroSerpenti())
        );
        sistema.setDadoSingolo(
                Boolean.parseBoolean(configurazioneGioco.getVarianteDadoSingolo())
        );
        sistema.setDadoSingoloFinale(
                Boolean.parseBoolean(configurazioneGioco.getVarianteDadoSingoloFinale())
        );
        sistema.setDoppioSei(
                Boolean.parseBoolean(configurazioneGioco.getVarianteDoppioSei())
        );
        sistema.setCaselleSosta(
                Boolean.parseBoolean(configurazioneGioco.getVarianteCaselleSosta())
        );
        sistema.setCasellePremio(
                Boolean.parseBoolean(configurazioneGioco.getVarianteCasellePremio())
        );
        sistema.setPescaCarta(
                Boolean.parseBoolean(configurazioneGioco.getVariantePescaCarta())
        );
        sistema.setUlterioriCarte(
                Boolean.parseBoolean(configurazioneGioco.getVarianteUlterioriCarte())
        );
        sistema.setNumberCaselleSpeciali(
                Util.CaselleSpeciali.SOSTA,
                Integer.parseInt(configurazioneGioco.getNumeroCaselleSosta())
        );
        sistema.setNumberCaselleSpeciali(
                Util.CaselleSpeciali.PREMIO,
                Integer.parseInt(configurazioneGioco.getNumeroCasellePremio())
        );
        sistema.setNumberCaselleSpeciali(
                Util.CaselleSpeciali.PESCA,
                Integer.parseInt(configurazioneGioco.getNumeroCasellePescaCarta())
        );
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Info.fxml"));
        Parent root = loader.load();
        InfoController infoController = loader.getController();
        infoController.init(this.sistema,this.stage);
        Util.changeScene("Informazioni",root,stage,null,null,scene);
    }
}
