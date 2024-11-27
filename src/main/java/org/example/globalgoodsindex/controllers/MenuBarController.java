package org.example.globalgoodsindex.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.awt.*;
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
    }

    @FXML
    private void bindStrings(){
        settingsMenu.textProperty().bind(I18N.createStringBinding("settings"));
        viewMenu.textProperty().bind(I18N.createStringBinding("view"));
        helpMenu.textProperty().bind(I18N.createStringBinding("help"));
        aboutMenuItem.textProperty().bind(I18N.createStringBinding("about"));
        githubMenuItem.textProperty().bind(I18N.createStringBinding("visit_github"));
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
}
