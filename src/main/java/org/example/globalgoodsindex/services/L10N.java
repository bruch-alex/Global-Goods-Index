package org.example.globalgoodsindex.services;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.example.globalgoodsindex.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class L10N {
    private static final ObjectProperty<Locale> locale;

    static {
        locale = new SimpleObjectProperty<>(Locale.getDefault());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    public static Locale getLocale() {
        return locale.get();
    }

    public static void setLocale(Locale locale) {
        localeProperty().set(locale);
        Locale.setDefault(locale);
    }

    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    /**
     * gets the string with the given key from the resource bundle for the current locale and uses it as first argument
     * to MessageFormat.format, passing in the optional args and returning the result.
     *
     * @param key message key
     * @return localized string
     */
    public static String get(String key) {
        ResourceBundle countries = ResourceBundle.getBundle("L10n/" + getLocale().getISO3Language() + "/countries", getLocale());
        ResourceBundle products = ResourceBundle.getBundle("L10n/" + getLocale().getISO3Language() + "/products", getLocale());
        ResourceBundle ui = ResourceBundle.getBundle("L10n/" + getLocale().getISO3Language() + "/ui", getLocale());

        ArrayList<ResourceBundle> bundles = new ArrayList<>(List.of(
                countries, products, ui
        ));
        for (var bundle : bundles) {
            if (bundle.containsKey(key)) return bundle.getString(key);
        }
        return null;
    }

    /**
     * creates a String binding to a localized String for the given message bundle key
     *
     * @param key key
     * @return String binding
     */
    public static StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(() -> get(key), locale);
    }

    public static StringBinding createShortNameBinding(Product product) {
        String shortNameKey = product.getDatabaseName() + ".short";
        return L10N.createStringBinding(shortNameKey);
    }
}
