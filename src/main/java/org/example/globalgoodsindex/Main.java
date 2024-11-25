package org.example.globalgoodsindex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.globalgoodsindex.core.models.DataHandler;

import java.io.IOException;

public class Main extends Application {
    public static DataHandler dataHandler;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        dataHandler = new DataHandler();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Global Goods Comparison");
        stage.setScene(scene);
        stage.show();
    }
}
