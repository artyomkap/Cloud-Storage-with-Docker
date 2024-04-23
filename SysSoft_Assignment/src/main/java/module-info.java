module com.mycompany.syssoft_assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.syssoft_assignment to javafx.fxml;
    exports com.mycompany.syssoft_assignment;
}
