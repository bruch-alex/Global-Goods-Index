module org.example.globalgoodsindex {
    requires javafx.fxml;

    requires java.desktop;
    requires java.prefs;
    requires atlantafx.base;
    requires org.jsoup;

    opens org.example.globalgoodsindex.controllers to javafx.fxml;

    exports org.example.globalgoodsindex.models;
    exports org.example.globalgoodsindex;
    opens org.example.globalgoodsindex.services to javafx.fxml;
    exports org.example.globalgoodsindex.helpers;
}