package org.example.globalgoodsindex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.globalgoodsindex.core.models.DataHandler;
import org.example.globalgoodsindex.core.services.L10N;
import org.example.globalgoodsindex.core.services.ThemeManager;
import org.example.globalgoodsindex.core.services.UserPreferencesManager;

import java.io.IOException;

public class App extends Application {
    public static DataHandler dataHandler;

    public void run() {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        dataHandler = new DataHandler();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.titleProperty().bind(L10N.createStringBinding("appName"));

        UserPreferencesManager preferencesManager = new UserPreferencesManager();
        ThemeManager themeManager = new ThemeManager(preferencesManager);

        // apply saved theme
        themeManager.applySavedTheme();
        stage.setScene(scene);
        stage.show();
    }

}
