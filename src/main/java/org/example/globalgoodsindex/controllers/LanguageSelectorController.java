package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

import java.util.Locale;

public class LanguageSelectorController {

    @FXML
    private Menu languageSelector;
    @FXML
    private RadioMenuItem englishMenuItem;
    @FXML
    private RadioMenuItem germanMenuItem;

    @FXML
    private void initialize() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        englishMenuItem.setToggleGroup(languageToggleGroup);
        germanMenuItem.setToggleGroup(languageToggleGroup);

        languageSelector.textProperty().bind(I18N.createStringBinding("language"));
        englishMenuItem.textProperty().bind(I18N.createStringBinding("english"));
        englishMenuItem.setSelected(true);
        germanMenuItem.textProperty().bind(I18N.createStringBinding("german"));

        languageToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == englishMenuItem) changeLanguage("en");
            else if (newValue == germanMenuItem) changeLanguage("de");

        });
    }

    public void changeLanguage(String language) {
        switch (language) {
            case "de":
                I18N.setLocale(new Locale("de"));
                break;
            default:
                I18N.setLocale(new Locale("en"));
                break;
        }
    }

}
