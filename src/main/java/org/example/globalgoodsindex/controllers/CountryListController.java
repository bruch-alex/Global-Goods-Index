package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.globalgoodsindex.core.models.Entry;
import org.example.globalgoodsindex.core.services.CSVReader;

public class CountryListController {
    @FXML
    private ListView<Entry> countryList;

    @FXML
    public void initialize() {
        countryList.setItems(CSVReader.readCSV("/data/salaries/salaries.txt"));

        // Set the cell factory to display names and checkboxes
        countryList.setCellFactory(_ -> new ListEntryWithCheckBox());
    }
}
