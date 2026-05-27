module br.com.logisticsystem {

    requires javafx.controls;
    requires javafx.fxml;

    opens br.com.logisticsystem.controllers to javafx.fxml;

    exports br.com.logisticsystem.app;
}