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
            if (loadingScreenController == null) {
                throw new RuntimeException("LoadingScreenController is null. Check FXML file and fx:controller attribute.");
            }

            // Load data
            ScraperManager scraperManager = new ScraperManager();
            String salariesResourcePath = "/data/salaries/salaries.csv";
            String productsResourcePath = "/data/products/products.csv";

            CompletableFuture<ObservableList<Salaries>> salariesFuture = scraperManager.prepareSalariesAsync(salariesResourcePath);
            CompletableFuture<ObservableList<Product>> productsFuture = scraperManager.prepareProductsAsync(productsResourcePath);

            loadingScreenController.bindToProgress(salariesFuture, productsFuture);

            // Wait for both tasks to complete and then transition to main UI
            CompletableFuture.allOf(salariesFuture, productsFuture)
                    .thenRun(() -> {
                        try {
                            ObservableList<Salaries> salaries = salariesFuture.join();
                            ObservableList<Product> products = productsFuture.join();

                            if (salaries.isEmpty() || products.isEmpty()) {
                                throw new RuntimeException("Failed to load data. Please check scraping or CSV paths.");
                            }

                            dataHandler = new DataHandler(salaries, products);
                            Platform.runLater(() -> initializeUI(stage));
                        } catch (Exception e) {
                            Platform.runLater(() -> {
                                System.err.println("Error during data loading: " + e.getMessage());
                                e.printStackTrace();
                                Platform.exit();
                            });
                        }
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
