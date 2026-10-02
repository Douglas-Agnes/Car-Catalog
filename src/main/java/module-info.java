module com.example.mpapart3carcatalogagnesdouglas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.mpapart4carcatalogagnesdouglas to javafx.fxml;
    exports com.example.mpapart4carcatalogagnesdouglas;
}