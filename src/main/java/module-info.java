module com.personalmoneymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens com.personalmoneymanagement to javafx.fxml;
    exports com.personalmoneymanagement;
}
