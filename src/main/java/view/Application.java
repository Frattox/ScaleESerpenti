package view;

import controller.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        HomeController homeController = loader.getController();
        homeController.setStage(stage);
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }


}
