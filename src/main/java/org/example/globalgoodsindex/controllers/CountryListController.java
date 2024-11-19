package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.example.globalgoodsindex.core.models.Entry;
import org.example.globalgoodsindex.core.services.CSVReader;

public class CountryListController {
    @FXML
    private ListView<Entry> countryList;

    @FXML
    public void initialize() {
        countryList.setItems(CSVReader.readCSV("/data/salaries/salaries.txt"));

        //countryList.setCellFactory(param -> new ListCellCheckBox());

        // Set the cell factory to display names and checkboxes
        countryList.setCellFactory(new Callback<ListView<Entry>, ListCell<Entry>>() {
            @Override
            public ListCell<Entry> call(ListView<Entry> param) {
                return new ListCell<Entry>() {
                    private final CheckBox checkBox = new CheckBox();

                    @Override
                    protected void updateItem(Entry entry, boolean empty) {
                        super.updateItem(entry, empty);

                        if (empty || entry == null) {
                            setGraphic(null);
                        } else {
                            // Set the name as the label and bind the checkbox to the selected property
                            checkBox.setText(entry.getName());
                            checkBox.selectedProperty().bindBidirectional(entry.selectedProperty());

                            // Print the number when the checkbox is selected
                            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                                if (newValue) {
                                    System.out.println("Checked: " + entry.getName() + " - Number: " + entry.getPrice() + entry.isSelected());
                                }
                            });

                            setGraphic(checkBox);
                        }
                    }
                };
            }
        });
    }
}
