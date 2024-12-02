package org.example.globalgoodsindex.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.globalgoodsindex.App;
import org.example.globalgoodsindex.core.models.Entry;
import org.example.globalgoodsindex.core.models.Product;
import org.example.globalgoodsindex.core.services.L10N;

import java.util.List;

public class ProductListController {
    @FXML
    private ListView<Entry> productList;

    @FXML
    private Label productsLabel;

    @FXML
    private TextField productSearchField;

    @FXML
    public void initialize() {
        bindStrings();
        productList.setItems(FXCollections.observableArrayList(App.dataHandler.getProducts()));
        productList.setCellFactory(_ -> new CheckBoxFactory());

        productSearchField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            List<Product> filteredData = App.dataHandler.getProducts().stream()
                    .filter(item -> item.getName().toLowerCase().contains(newValue.toLowerCase()))
                    .toList();

            productList.getItems().clear();
            productList.setItems(FXCollections.observableArrayList(filteredData));
            productList.setCellFactory(_ -> new CheckBoxFactory());
        }));
    }

    @FXML
    private void bindStrings() {
        productsLabel.textProperty().bind(L10N.createStringBinding("products"));
        productSearchField.promptTextProperty().bind(L10N.createStringBinding("search"));
    }
}
