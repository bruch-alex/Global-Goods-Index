package org.example.globalgoodsindex.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.globalgoodsindex.Main;
import org.example.globalgoodsindex.core.models.Entry;


public class CountryListController {

    @FXML
    private ListView<Entry> countryList;

    @FXML
    private Label labelCountries;

    @FXML
    public void initialize() {
        countryList.setItems(FXCollections.observableArrayList(Main.dataHandler.getSalaries()));
        countryList.setCellFactory(_ -> new CheckBoxFactory());
    }

    @FXML
    private void bindStrings(){
        labelCountries.textProperty().bind(I18N.createStringBinding("countries"));
    }
}
