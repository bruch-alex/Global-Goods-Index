package org.example.globalgoodsindex.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.globalgoodsindex.Main;
import org.example.globalgoodsindex.core.models.Entry;

public class ProductListController {
    @FXML
    private ListView<Entry> productList;

    @FXML
    private Label productsLabel;

    @FXML
    public void initialize() {
        bindStrings();
        productList.setItems(FXCollections.observableArrayList(Main.dataHandler.getProducts()));
        productList.setCellFactory(_ -> new CheckBoxFactory());
    }

    @FXML
    private void bindStrings() {
        productsLabel.textProperty().bind(I18N.createStringBinding("products"));
    }
}
