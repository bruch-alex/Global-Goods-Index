package org.example.globalgoodsindex.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.globalgoodsindex.core.models.Entry;

public class ProductsListController {
    @FXML
    private ListView<Entry> productsList;

    @FXML
    public void initialize() {

        ObservableList<Entry> items = FXCollections.observableArrayList(
                new Entry("Iphone", 1000),
                new Entry("Banana", 150),
                new Entry("Apple", 300));

        if (items.isEmpty()) {
            System.out.println("ObservableList is empty!");
        } else {
            System.out.println("ObservableList contains: " + items.size() + " items.");
        }

        productsList.setItems(items);
        productsList.setCellFactory(_ -> new ListEntryWithCheckBox());
    }
}
