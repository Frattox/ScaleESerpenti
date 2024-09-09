module org.example.scaleeserpenti5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens scaleeserpenti5 to javafx.fxml;
    exports view;
    opens view to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
}