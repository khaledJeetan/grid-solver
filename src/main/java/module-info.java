module com.example.aihw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.media;

    opens com.example.aihw to javafx.fxml;
    exports com.example.aihw;
}