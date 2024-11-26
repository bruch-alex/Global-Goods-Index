package org.example.globalgoodsindex.controllers;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXML;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconThemeSwitcherController {

    @FXML
    private FontIcon themeIcon;

    private boolean isLightTheme = true;

    @FXML
    public void toggleTheme() {
        if (isLightTheme) {
            Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
            themeIcon.setIconLiteral("fas-moon");
        } else {
            Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
            themeIcon.setIconLiteral("fas-sun");
        }
        isLightTheme = !isLightTheme;
    }
}
