package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import org.example.globalgoodsindex.services.L10N;
import org.example.globalgoodsindex.services.ThemeManager;
import org.example.globalgoodsindex.services.UserPreferencesManager;

public class ThemeSwitcherController {

    @FXML
    private Menu themeSwitcher;
    @FXML
    private RadioMenuItem lightThemeMenuItem;
    @FXML
    private RadioMenuItem darkThemeMenuItem;
    @FXML
    private RadioMenuItem nordLightThemeMenuItem;
    @FXML
    private RadioMenuItem nordDarkThemeMenuItem;

    private static final String PRIMER_LIGHT = "PrimerLight";
    private static final String PRIMER_DARK = "PrimerDark";
    private static final String NORD_LIGHT = "NordLight";
    private static final String NORD_DARK = "NordDark";

    private ThemeManager themeManager;

    @FXML
    public void initialize() {
        bindStrings();

        UserPreferencesManager preferencesManager = new UserPreferencesManager();
        themeManager = new ThemeManager(preferencesManager);

        ToggleGroup themeToggleGroup = new ToggleGroup();
        initializeToggleGroup(themeToggleGroup);

        String savedTheme = themeManager.getSavedTheme();
        selectMenuItemForTheme(savedTheme);
        themeManager.applyTheme(savedTheme);

        themeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == lightThemeMenuItem) themeManager.applyTheme(PRIMER_LIGHT);
            else if (newValue == darkThemeMenuItem) themeManager.applyTheme(PRIMER_DARK);
            else if (newValue == nordLightThemeMenuItem) themeManager.applyTheme(NORD_LIGHT);
            else if (newValue == nordDarkThemeMenuItem) themeManager.applyTheme(NORD_DARK);
        });
    }

    private void initializeToggleGroup(ToggleGroup themeToggleGroup) {
        lightThemeMenuItem.setToggleGroup(themeToggleGroup);
        darkThemeMenuItem.setToggleGroup(themeToggleGroup);
        nordLightThemeMenuItem.setToggleGroup(themeToggleGroup);
        nordDarkThemeMenuItem.setToggleGroup(themeToggleGroup);
    }

    private void selectMenuItemForTheme(String themeName) {
        switch (themeName) {
            case PRIMER_DARK -> darkThemeMenuItem.setSelected(true);
            case NORD_LIGHT -> nordLightThemeMenuItem.setSelected(true);
            case NORD_DARK -> nordDarkThemeMenuItem.setSelected(true);
            default -> lightThemeMenuItem.setSelected(true);
        }
    }

    @FXML
    private void bindStrings() {
        themeSwitcher.textProperty().bind(L10N.createStringBinding("theme"));
    }
}
