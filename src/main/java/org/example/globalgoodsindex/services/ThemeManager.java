package org.example.globalgoodsindex.services;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;

import java.util.HashMap;
import java.util.Map;

public class ThemeManager {
    private final Map<String, String> themeStylesheets = new HashMap<>();
    private final UserPreferencesManager preferencesManager;

    public ThemeManager(UserPreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
        registerThemes();
    }

    private void registerThemes() {
        themeStylesheets.put("PrimerLight", new PrimerLight().getUserAgentStylesheet());
        themeStylesheets.put("PrimerDark", new PrimerDark().getUserAgentStylesheet());
        themeStylesheets.put("NordLight", new NordLight().getUserAgentStylesheet());
        themeStylesheets.put("NordDark", new NordDark().getUserAgentStylesheet());
    }

    public void applyTheme(String themeName) {
        String stylesheet = themeStylesheets.getOrDefault(themeName, themeStylesheets.get("PrimerLight"));
        Application.setUserAgentStylesheet(stylesheet);
        preferencesManager.saveTheme(themeName);
    }

    public String getSavedTheme() {
        return preferencesManager.getTheme();
    }
}

