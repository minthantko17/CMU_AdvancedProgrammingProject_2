module se233.advprogrammingproject2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;


    opens se233.advprogrammingproject2 to javafx.fxml;
    exports se233.advprogrammingproject2;
}