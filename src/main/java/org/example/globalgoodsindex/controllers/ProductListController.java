package org.example.globalgoodsindex.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.globalgoodsindex.Main;
import org.example.globalgoodsindex.core.models.Entry;
import org.example.globalgoodsindex.core.models.Product;

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
        productList.setItems(FXCollections.observableArrayList(Main.dataHandler.getProducts()));
        productList.setCellFactory(_ -> new CheckBoxFactory());

        productSearchField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            List<Product> filteredData = Main.dataHandler.getProducts().stream()
                    .filter(item -> I18N.get(item.getName()).toLowerCase().contains(newValue.toLowerCase()))
                    .toList();

            productList.getItems().clear();
            productList.setItems(FXCollections.observableArrayList(filteredData));
            productList.setCellFactory(_ -> new CheckBoxFactory());
        }));
    }

    @FXML
    private void bindStrings() {
        productsLabel.textProperty().bind(I18N.createStringBinding("products"));
        productSearchField.promptTextProperty().bind(I18N.createStringBinding("search"));
    }
}
