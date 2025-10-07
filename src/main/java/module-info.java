module cs151.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
	requires java.sql;

    // Open packages to JavaFX for reflection
    opens cs151.application to javafx.fxml;
    opens cs151.controller to javafx.fxml;
    opens cs151.model to javafx.base;
    
    // Export packages
    exports cs151.application;
    exports cs151.controller;
    exports cs151.model;
}