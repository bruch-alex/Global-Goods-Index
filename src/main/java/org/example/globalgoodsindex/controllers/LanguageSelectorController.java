package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;

import java.util.Locale;

public class LanguageSelectorController {

    @FXML
    private Menu languageSelector;
    @FXML
    private CheckMenuItem englishMenuItem;
    @FXML
    private CheckMenuItem germanMenuItem;
    // The current Locale can be set dynamically (either default or based on user input)


    @FXML
    private void initialize() {
        languageSelector.textProperty().bind(I18N.createStringBinding("language"));
        englishMenuItem.textProperty().bind(I18N.createStringBinding("english"));
        englishMenuItem.setSelected(true);
        germanMenuItem.textProperty().bind(I18N.createStringBinding("german"));

        englishMenuItem.setOnAction(_ -> handleMenuItemSelection(englishMenuItem, germanMenuItem));
        germanMenuItem.setOnAction(_ -> handleMenuItemSelection(germanMenuItem, englishMenuItem));
    }

    private void handleMenuItemSelection(CheckMenuItem selectedItem, CheckMenuItem... otherItems) {
        // If the selected item is checked, uncheck the others
        if (selectedItem.isSelected()) {
            for (CheckMenuItem item : otherItems) {
                item.setSelected(false);
            }
            changeLanguage(selectedItem.getId());
        }
    }

    public void changeLanguage(String language) {
        switch (language) {
            case "germanMenuItem":
                I18N.setLocale(new Locale("de"));
                break;
            default:
                I18N.setLocale(new Locale("en"));
                break;
        }
    }

}
