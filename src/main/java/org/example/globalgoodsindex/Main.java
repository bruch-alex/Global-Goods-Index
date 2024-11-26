package org.example.globalgoodsindex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.globalgoodsindex.core.models.DataHandler;
import org.example.globalgoodsindex.core.services.ThemeManager;
import org.example.globalgoodsindex.core.services.UserPreferencesManager;

import java.io.IOException;

public class Main extends Application {
    public static DataHandler dataHandler;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        dataHandler = new DataHandler();

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Scene scene = new Scene(mainLoader.load(), 1000, 800);
        System.out.println("Scene in Main: " + scene);

        UserPreferencesManager preferencesManager = new UserPreferencesManager();
        ThemeManager themeManager = new ThemeManager(preferencesManager);

        // apply saved theme
        themeManager.applySavedTheme();

        stage.setTitle("Global Goods Comparison");
        stage.setScene(scene);
        stage.show();
    }
}
