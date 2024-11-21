package org.example.globalgoodsindex.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.globalgoodsindex.Main;
import org.example.globalgoodsindex.core.models.Entry;
import org.example.globalgoodsindex.core.services.CSVReader;


public class CountryListController {
    @FXML
    private ListView<Entry> countryList;

    @FXML
    public void initialize() {
        countryList.setItems(FXCollections.observableArrayList(Main.dataHandler.getSalaries()));

        // Set the cell factory to display names and checkboxes
        countryList.setCellFactory(_ -> new CheckBoxFactory());
    }
}
