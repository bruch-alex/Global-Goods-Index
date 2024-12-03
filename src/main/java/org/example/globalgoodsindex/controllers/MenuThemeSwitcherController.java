package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import org.example.globalgoodsindex.services.L10N;
import org.example.globalgoodsindex.services.ThemeManager;
import org.example.globalgoodsindex.services.UserPreferencesManager;

public class MenuThemeSwitcherController {

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

    private ThemeManager themeManager;

    @FXML
    public void initialize() {
        bindStrings();

        UserPreferencesManager preferencesManager = new UserPreferencesManager();
        themeManager = new ThemeManager(preferencesManager);

        // create ToggleGroup for theme options
        ToggleGroup themeToggleGroup = new ToggleGroup();
        lightThemeMenuItem.setToggleGroup(themeToggleGroup);
        darkThemeMenuItem.setToggleGroup(themeToggleGroup);
        nordLightThemeMenuItem.setToggleGroup(themeToggleGroup);
        nordDarkThemeMenuItem.setToggleGroup(themeToggleGroup);


        String savedTheme = preferencesManager.getTheme();
        switch (savedTheme) {
            case "PrimerDark":
                darkThemeMenuItem.setSelected(true);
                break;
            case "NordLight":
                nordLightThemeMenuItem.setSelected(true);
                break;
            case "NordDark":
                nordDarkThemeMenuItem.setSelected(true);
                break;
            default:
                lightThemeMenuItem.setSelected(true);
                break;
        }

        themeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == lightThemeMenuItem) {
                themeManager.applyTheme("PrimerLight");
            } else if (newValue == darkThemeMenuItem) {
                themeManager.applyTheme("PrimerDark");
            } else if (newValue == nordLightThemeMenuItem) {
                themeManager.applyTheme("NordLight");
            } else if (newValue == nordDarkThemeMenuItem) {
                themeManager.applyTheme("NordDark");
            }
        });
    }

    @FXML
    private void bindStrings() {
        themeSwitcher.textProperty().bind(L10N.createStringBinding("theme"));
    }
}
