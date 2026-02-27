module com.sms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.sms.ui to javafx.fxml;
    opens com.sms.domain to javafx.base;

    exports com.sms;
    exports com.sms.ui;
}