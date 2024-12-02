package org.example.globalgoodsindex.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.example.globalgoodsindex.core.services.L10N;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class MenuBarController {
    @FXML
    private Menu settingsMenu;

    @FXML
    private Menu viewMenu;

    @FXML
    private Menu helpMenu;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private MenuItem githubMenuItem;

    @FXML
    private void initialize() {
        bindStrings();
        githubMenuItem.setOnAction(_ -> openWebsite("https://github.com/bruch-alex/Global-Goods-Index"));
        aboutMenuItem.setOnAction(_ -> openAboutWindow());
    }

    @FXML
    private void bindStrings(){
        settingsMenu.textProperty().bind(L10N.createStringBinding("settings"));
        viewMenu.textProperty().bind(L10N.createStringBinding("view"));
        helpMenu.textProperty().bind(L10N.createStringBinding("help"));
        aboutMenuItem.textProperty().bind(L10N.createStringBinding("about"));
        githubMenuItem.textProperty().bind(L10N.createStringBinding("visit_github"));
    }

    private void openWebsite(String url) {
        Task<Void> openWebSiteTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    URI uri = new URI(url);
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.browse(uri); // Open the URL in the default web browser
                    }
                } catch (Exception e) {
                    e.printStackTrace();  // Handle exceptions (invalid URL, etc.)
                }
                return null;
            }
        };
        Thread t1 = new Thread(openWebSiteTask);
        t1.setDaemon(true);
        t1.start();
    }

    @FXML
    private void openAboutWindow() {
        try {
            // Load the new window FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/about.fxml"));
            Stage newStage = new Stage();
            Scene newScene = new Scene(loader.load());
            newStage.titleProperty().bind(L10N.createStringBinding("about"));
            // Create a new stage (window) for the new window
            newStage.setScene(newScene);
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
