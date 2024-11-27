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
    private RadioMenuItem russianMenuItem;

    @FXML
    private void initialize() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        englishMenuItem.setToggleGroup(languageToggleGroup);
        germanMenuItem.setToggleGroup(languageToggleGroup);
        russianMenuItem.setToggleGroup(languageToggleGroup);

        bindStrings();
        englishMenuItem.setSelected(true);

        languageToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == englishMenuItem) changeLanguage("en");
            else if (newValue == germanMenuItem) changeLanguage("de");
            else if (newValue == russianMenuItem) changeLanguage("ru");
        });
    }

    public void changeLanguage(String language) {
        switch (language) {
            case "de" -> I18N.setLocale(new Locale("de"));
            case "ru" -> I18N.setLocale(new Locale("ru"));
            default -> I18N.setLocale(new Locale("en"));
        }
    }

    @FXML
    private void bindStrings() {
        languageSelector.textProperty().bind(I18N.createStringBinding("language"));
        englishMenuItem.textProperty().bind(I18N.createStringBinding("english"));
        germanMenuItem.textProperty().bind(I18N.createStringBinding("german"));
        russianMenuItem.textProperty().bind(I18N.createStringBinding("russian"));
    }

}
