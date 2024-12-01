package org.example.globalgoodsindex.core.services;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ThemeManager {

    private final UserPreferencesManager preferencesManager;

    public static BooleanProperty isLightTheme;

    public ThemeManager(UserPreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
        isLightTheme = new SimpleBooleanProperty();
    }

    public static BooleanProperty getIsLightThemeProperty(){
        return isLightTheme;
    }

    public void applySavedTheme() {
        String savedTheme = preferencesManager.getTheme();
        applyTheme(savedTheme);
    }

    public void applyTheme(String themeName) {
        switch (themeName) {
            case "PrimerDark":
                Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
                isLightTheme.setValue(false);
                break;
            case "NordLight":
                Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
                isLightTheme.setValue(true);
                break;
            case "NordDark":
                Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
                isLightTheme.setValue(false);
                break;
            default:
                Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
                isLightTheme.setValue(true);
                break;
        }
        preferencesManager.saveTheme(themeName);
    }
}
