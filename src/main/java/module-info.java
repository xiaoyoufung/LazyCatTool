module se233.lazycattool {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;
    requires java.desktop;


    opens se233.lazycattool to javafx.fxml;
    exports se233.lazycattool;
}