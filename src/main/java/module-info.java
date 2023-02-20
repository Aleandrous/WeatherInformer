module alex.weatherinfo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens alex.weatherinfo to javafx.fxml;
    exports alex.weatherinfo;
}