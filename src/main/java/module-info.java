module com.coudpark.manipuladorjson {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires json.simple;

    opens com.coudpark.manipuladorjson to javafx.fxml;
    exports com.coudpark.manipuladorjson;
}