package org.example.globalgoodsindex.controllers;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import org.example.globalgoodsindex.services.ThemeManager;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconThemeSwitcherController {

    @FXML
    private FontIcon themeIcon;

    private BooleanProperty isLightTheme2;


    @FXML
    public void initialize(){
        isLightTheme2 = new SimpleBooleanProperty();
        isLightTheme2.bindBidirectional(ThemeManager.getIsLightThemeProperty());
        themeIcon.visibleProperty().setValue(false);
    }

    @FXML
    public void toggleTheme() {
        if (isLightTheme2.getValue()) {
//            Application.getUserAgentStylesheet()
            // get name of current theme
            // if (isLightTheme) name.deleteChars(last 5 chars){
            //      switch(name) {
            //        case "prime": Application.setUser....(PrimeDark)
            //                      isLightTheme2.set(false)
            //           }
            // }
            Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
            themeIcon.setIconLiteral("fas-moon");
        } else {
            Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
            themeIcon.setIconLiteral("fas-sun");
        }
        isLightTheme2.setValue(!(isLightTheme2.getValue()));
    }



}
