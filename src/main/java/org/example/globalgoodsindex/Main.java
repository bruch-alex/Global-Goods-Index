package org.example.globalgoodsindex;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.example.globalgoodsindex.controllers.LoadingScreenController;
import org.example.globalgoodsindex.core.models.DataHandler;
import org.example.globalgoodsindex.core.models.Product;
import org.example.globalgoodsindex.core.models.Salaries;
import org.example.globalgoodsindex.core.services.*;


import java.io.IOException;
import java.net.URL;

import java.util.concurrent.CompletableFuture;

public class Main extends Application {
    public static DataHandler dataHandler;
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Main.primaryStage = stage;

        // Show loading screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loading.fxml"));
            VBox loadingBox = loader.load();
            Scene loadingScene = new Scene(loadingBox, 400, 300);
            stage.setScene(loadingScene);
            stage.setTitle("Loading");
            stage.show();

            LoadingScreenController loadingScreenController = loader.getController();

            // Load data
            ScraperManager scraperManager = new ScraperManager();
            String salariesResourcePath = "/data/salaries/salaries.csv";
            String productsResourcePath = "/data/products/products.csv";

            // Check resources
            URL salariesResourceUrl = getClass().getResource(salariesResourcePath);
            URL productsResourceUrl = getClass().getResource(productsResourcePath);

            System.out.println("Salaries resource found: " + (salariesResourceUrl != null));
            System.out.println("Products resource found: " + (productsResourceUrl != null));


            CompletableFuture<ObservableList<Salaries>> salariesFuture = scraperManager.prepareSalariesAsync(salariesResourcePath);
            CompletableFuture<ObservableList<Product>> productsFuture = scraperManager.prepareProductsAsync(productsResourcePath);

            loadingScreenController.bindToProgress(salariesFuture, productsFuture);

            DataHandler.loadDataAsync(salariesResourcePath, productsResourcePath, scraperManager)
                    .thenAccept(handler -> {
                        Platform.runLater(() -> {
                            dataHandler = handler;
                            initializeUI(stage);
                        });
                    })
                    .exceptionally(ex -> {
                        System.err.println("Error loading data: " + ex.getMessage());
                        Platform.exit();
                        return null;
                    });
        } catch (IOException e) {
            System.err.println("Error loading FXML for loading screen: " + e.getMessage());
            e.printStackTrace();
            Platform.exit();
        }
    }


    private void initializeUI(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/main.fxml"));
            URL resource = Main.class.getResource("/fxml/main.fxml");
            System.out.println("FXML file found: " + (resource != null));

            Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
            stage.titleProperty().bind(L10N.createStringBinding("appName"));

            UserPreferencesManager preferencesManager = new UserPreferencesManager();
            ThemeManager themeManager = new ThemeManager(preferencesManager);

            themeManager.applySavedTheme();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
