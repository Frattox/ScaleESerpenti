module scaleeserpenti {
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
    requires java.sql;
    opens controller to javafx.fxml;
    opens DB to javafx.base;
    opens view to javafx.fxml;
    opens model to javafx.fxml;
    exports controller;
    exports model;
    exports view;
}
