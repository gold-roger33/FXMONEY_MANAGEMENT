module com.personalmoneymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.personalmoneymanagement to javafx.fxml;
    exports com.personalmoneymanagement;
}
