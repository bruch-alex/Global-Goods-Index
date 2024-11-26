module org.example.globalgoodsindex {
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires java.prefs;
    requires atlantafx.base;

    opens org.example.globalgoodsindex.controllers to javafx.fxml;

    exports org.example.globalgoodsindex;
    opens org.example.globalgoodsindex.core.services to javafx.fxml;
}