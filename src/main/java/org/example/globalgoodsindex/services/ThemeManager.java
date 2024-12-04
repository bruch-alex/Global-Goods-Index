package org.example.globalgoodsindex.services;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ThemeManager {

    private final UserPreferencesManager preferencesManager;

    public ThemeManager(UserPreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;

    }

    public void applySavedTheme() {
        String savedTheme = preferencesManager.getTheme();
        applyTheme(savedTheme);
    }

    public void applyTheme(String themeName) {
        switch (themeName) {
            case "PrimerDark":
                Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
                break;
            case "NordLight":
                Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
                break;
            case "NordDark":
                Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
                break;
            default:
                Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
                break;
        }
        preferencesManager.saveTheme(themeName);
    }
}
