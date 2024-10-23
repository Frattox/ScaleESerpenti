package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Sistema;

public interface Controller {
    void init(Sistema sistema, Stage stage);
    Sistema getSistema();
    Stage getStage();
    Scene getScene();
}
