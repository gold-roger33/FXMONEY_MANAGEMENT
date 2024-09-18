module com.personalmoneymanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.personalmoneymanagement to javafx.fxml;
    exports com.personalmoneymanagement;
}
