module MAS {

    requires javafx.fxml;
    requires javafx.controls;
    requires org.twitter4j.core;
    requires java.sql;
    requires org.junit.jupiter.api;

    opens login;
    opens dataEntry;
    opens twitter;
    opens editLoginData;
    opens jobSheet;
    opens systemAdministrator;
    opens billing;

}