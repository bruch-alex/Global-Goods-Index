package org.example.globalgoodsindex.core.services;

import java.util.prefs.Preferences;

public class UserPreferencesManager {

    private static final String THEME_KEY = "preferredTheme";
    private static final String LANGUAGE_KEY = "preferredLanguage";
    private final Preferences preferences;

    public UserPreferencesManager() {
        preferences = Preferences.userNodeForPackage(UserPreferencesManager.class);
    }

    // Theme Preferences
    public void saveTheme(String themeName) {
        preferences.put(THEME_KEY, themeName);
    }

    public String getTheme() {
        return preferences.get(THEME_KEY, "PrimerLight");
    }

    //TODO: Set Language Preferences
    public void saveLanguage(String language) {
        preferences.put(LANGUAGE_KEY, language);
    }

    public String getLanguage() {
        return preferences.get(LANGUAGE_KEY, "English"); // Default to English
    }
}
