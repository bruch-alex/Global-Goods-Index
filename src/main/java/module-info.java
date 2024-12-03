module org.example.globalgoodsindex {
    requires javafx.fxml;


    requires java.desktop;
    requires java.prefs;
    requires atlantafx.base;
    requires com.fasterxml.jackson.databind;
    requires org.jsoup;

    opens org.example.globalgoodsindex.controllers to javafx.fxml;

    exports org.example.globalgoodsindex;
    opens org.example.globalgoodsindex.services to javafx.fxml;
}