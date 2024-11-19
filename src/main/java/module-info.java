module org.example.globalgoodsindex {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens org.example.globalgoodsindex to javafx.fxml;
    exports org.example.globalgoodsindex;
}