package org.example.globalgoodsindex.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.globalgoodsindex.Main;
import org.example.globalgoodsindex.core.models.Entry;

public class ProductListController {
    @FXML
    private ListView<Entry> productList;

    @FXML
    public void initialize() {

        //countryList.setItems(FXCollections.observableArrayList(Main.dataHandler.getSalaries()));
        productList.setItems(FXCollections.observableArrayList(Main.dataHandler.getProducts()));
        productList.setCellFactory(_ -> new CheckBoxFactory());
    }
}
