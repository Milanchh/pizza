module com.example.test3pizza {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.test3pizza to javafx.fxml;
    exports com.example.test3pizza;
}