package org.example.globalgoodsindex.controllers;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.beans.binding.Bindings;

import java.util.concurrent.CompletableFuture;


public class LoadingScreenController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label loadingLabel;

    private final BooleanProperty salariesLoaded = new SimpleBooleanProperty(false);
    private final BooleanProperty productsLoaded = new SimpleBooleanProperty(false);

    public void bindToProgress(CompletableFuture<?> salariesFuture, CompletableFuture<?> productsFuture) {
        // Update the properties when futures complete
        salariesFuture.thenRun(() -> Platform.runLater(() -> salariesLoaded.set(true)));
        productsFuture.thenRun(() -> Platform.runLater(() -> productsLoaded.set(true)));

        // Bind the progress bar
        progressBar.progressProperty().bind(
                Bindings.createDoubleBinding(
                        () -> (salariesLoaded.get() ? 0.5 : 0.0) + (productsLoaded.get() ? 0.5 : 0.0),
                        salariesLoaded,
                        productsLoaded
                )
        );

        // Bind the loading label
        loadingLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> {
                            if (!salariesLoaded.get()) return "Loading salaries...";
                            if (!productsLoaded.get()) return "Loading products...";
                            return "Finishing up...";
                        },
                        salariesLoaded,
                        productsLoaded
                )
        );
    }
}
