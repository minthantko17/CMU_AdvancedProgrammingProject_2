module se233.advprogrammingproject2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;

//    opens se233.advprogrammingproject2 to javafx.fxml;
    opens se233.advprogrammingproject2 to javafx.fxml, org.apache.logging.log4j;
    opens se233.advprogrammingproject2.Controllers to javafx.fxml, org.apache.logging.log4j;

    exports se233.advprogrammingproject2;
    exports se233.advprogrammingproject2.Controllers;
}