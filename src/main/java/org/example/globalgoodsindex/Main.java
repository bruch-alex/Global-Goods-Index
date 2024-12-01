package org.example.globalgoodsindex;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.globalgoodsindex.core.models.DataHandler;
import org.example.globalgoodsindex.core.models.Product;
import org.example.globalgoodsindex.core.models.Salaries;
import org.example.globalgoodsindex.core.services.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Main extends Application {
    public static DataHandler dataHandler;
    public static Stage primaryStage;

    public static void main(String[] args) {


        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Main.primaryStage = stage;
        ScraperManager scraperManager = new ScraperManager();
        String salariesFilePath = "/data/salaries/salaries.csv";
        String productsFilePath = "/data/products/products.csv";

        // TODO: ProgressBar: inform till the data is loaded
        // Start asynchronous preparation for both salaries and products
        CompletableFuture<ObservableList<Salaries>> salariesFuture = scraperManager.prepareSalariesAsync(salariesFilePath);
        CompletableFuture<Map<String, List<Salaries>>> productsFuture = scraperManager.prepareProductsAsync(productsFilePath);

        // Wait for both futures to complete
        CompletableFuture.allOf(salariesFuture, productsFuture).thenAccept(ignored -> {
            try {
                ObservableList<Salaries> salaries = salariesFuture.get();
                Map<String, List<Salaries>> productsMap = productsFuture.get();


                if (salaries.isEmpty() || productsMap.isEmpty()) {
                    System.err.println("Failed to prepare required data. Exiting.");
                    Platform.exit();
                    return;
                }

                // Proceed to initialize the UI with prepared data
                Platform.runLater(() -> {
                    dataHandler = new DataHandler(salaries, convertProductMapToObservableList(productsMap)); // Pass data to DataHandler
                    System.out.println("Salaries list: " + Main.dataHandler.getSalaries());
                    Main.dataHandler.getSalaries().forEach(salary -> {
                        System.out.println("Name: " + salary.getName() + ", Localized: " + salary.getLocalizedName());
                    });
                    initializeUI(stage);
                });
            } catch (Exception e) {
                System.err.println("Error while preparing data: " + e.getMessage());
                e.printStackTrace();
                Platform.exit();
            }
        });
    }

    private ObservableList<Product> convertProductMapToObservableList(Map<String, List<Salaries>> productsMap) {
        ObservableList<Product> products = FXCollections.observableArrayList();

        productsMap.forEach((productName, salariesList) -> {
            Product product = new Product(productName, salariesList);
            products.add(product);
        });

        return products;
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
