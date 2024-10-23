package controller;

import DB.ConfigurazioneDAO;
import DB.ConfigurazioneGioco;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import model.Sistema;

import java.util.List;

public class CaricaPartitaController {


    private ConfigurazioneDAO configurazioneDAO;
    private Sistema sistema;

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public void init(){
        configurazioneDAO = new ConfigurazioneDAO(this.sistema);
        List<ConfigurazioneGioco> configurazioni = configurazioneDAO.getAll();

    }
}
