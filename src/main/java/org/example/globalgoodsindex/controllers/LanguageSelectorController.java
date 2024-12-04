package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import org.example.globalgoodsindex.services.L10N;
import org.example.globalgoodsindex.services.UserPreferencesManager;

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

    private final UserPreferencesManager preferencesManager;

    public LanguageSelectorController() {
        preferencesManager = new UserPreferencesManager();
    }

    @FXML
    private void initialize() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        englishMenuItem.setToggleGroup(languageToggleGroup);
        germanMenuItem.setToggleGroup(languageToggleGroup);
        russianMenuItem.setToggleGroup(languageToggleGroup);

        String savedLanguage = preferencesManager.getLanguage();
        switch (savedLanguage) {
            case "de" -> germanMenuItem.setSelected(true);
            case "ru" -> russianMenuItem.setSelected(true);
            default -> englishMenuItem.setSelected(true);
        }
        changeLanguage(savedLanguage);

        bindStrings();

        languageToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == englishMenuItem) saveAndChangeLanguage("en");
            else if (newValue == germanMenuItem) saveAndChangeLanguage("de");
            else if (newValue == russianMenuItem) saveAndChangeLanguage("ru");
        });
    }

    private void saveAndChangeLanguage(String language) {
        preferencesManager.saveLanguage(language);
        changeLanguage(language);
    }

    public void changeLanguage(String language) {
        switch (language) {
            case "de" -> L10N.setLocale(new Locale("de"));
            case "ru" -> L10N.setLocale(new Locale("ru"));
            default -> L10N.setLocale(new Locale("en"));
        }
    }

    @FXML
    private void bindStrings() {
        languageSelector.textProperty().bind(L10N.createStringBinding("language"));
        englishMenuItem.textProperty().bind(L10N.createStringBinding("english"));
        germanMenuItem.textProperty().bind(L10N.createStringBinding("german"));
        russianMenuItem.textProperty().bind(L10N.createStringBinding("russian"));
    }

}
