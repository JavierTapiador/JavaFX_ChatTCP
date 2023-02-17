module com.mycompany.JavaFX_Chat {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.JavaFX_Chat to javafx.fxml;
    exports com.mycompany.JavaFX_Chat;
}
