package org.example.globalgoodsindex;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.globalgoodsindex.core.models.DataHandler;
import org.example.globalgoodsindex.core.services.*;

import java.io.IOException;

public class Main extends Application {
    public static DataHandler dataHandler;

    public static void main(String[] args) {
        //Test scrapeMarketProducts
//        FetchData fetchData = new FetchData();
//        fetchData.scrapeMarketProducts("src/main/resources/data/products/market-products.csv");

        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        ScraperManager scraperManager = new ScraperManager();
        String salariesFilePath = "src/main/resources/data/salaries/salaries.csv";

        // start asynchronous scraping
        scraperManager.prepareSalariesFileAsync(salariesFilePath)
                .thenAccept(scrapingSuccessful -> {
                    if (!scrapingSuccessful) {
                        System.err.println("Failed to prepare salaries file. Exiting application.");
                        Platform.exit();
                    } else {
                        // initialize DataHandler after scraping
                        Platform.runLater(() -> {
                            dataHandler = new DataHandler();
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/main.fxml"));
                                Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
                                stage.titleProperty().bind(L10N.createStringBinding("appName"));

                                UserPreferencesManager preferencesManager = new UserPreferencesManager();
                                ThemeManager themeManager = new ThemeManager(preferencesManager);

                                themeManager.applySavedTheme();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
    }

}
