package org.example.globalgoodsindex.core.services;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;

public class ThemeManager {

    private final UserPreferencesManager preferencesManager;

    public ThemeManager(UserPreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    // apply the saved theme
    public void applySavedTheme() {
        String savedTheme = preferencesManager.getTheme();
        applyTheme(savedTheme);
    }

    // Apply a specific theme and save it
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
