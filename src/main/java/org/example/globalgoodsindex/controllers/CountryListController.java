package org.example.globalgoodsindex.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.globalgoodsindex.App;
import org.example.globalgoodsindex.models.Entry;
import org.example.globalgoodsindex.models.Salary;
import org.example.globalgoodsindex.services.L10N;

import java.util.List;


public class CountryListController {

    @FXML
    private ListView<Entry> countryList;

    @FXML
    private Label labelCountries;

    @FXML
    private TextField countrySearchField;

    @FXML
    public void initialize() {
        bindStrings();
        countryList.setItems(FXCollections.observableArrayList(App.dataHandler.getSalaries()));
        countryList.setCellFactory(_ -> new CheckBoxFactory());

        countrySearchField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            List<Salary> filteredData = App.dataHandler.getSalaries().stream()
                    .filter(item -> item.getName().toLowerCase().contains(newValue.toLowerCase()))
                    .toList();

            countryList.getItems().clear();
            countryList.setItems(FXCollections.observableArrayList(filteredData));
            countryList.setCellFactory(_ -> new CheckBoxFactory());
        }));
    }

    @FXML
    private void bindStrings(){
        labelCountries.textProperty().bind(L10N.createStringBinding("countries"));
        countrySearchField.promptTextProperty().bind(L10N.createStringBinding("search"));
    }
}
