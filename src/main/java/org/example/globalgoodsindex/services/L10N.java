package org.example.globalgoodsindex.services;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.example.globalgoodsindex.models.Product;

import java.text.MessageFormat;
import java.util.*;

public final class L10N {
    private static final ObjectProperty<Locale> locale;

    static {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    /**
     * get the supported Locales.
     *
     * @return List of Locale objects.
     */
    public static List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(Locale.ENGLISH, Locale.GERMAN));
    }

    /**
     * get the default locale. This is the systems default if contained in the supported locales, english otherwise.
     *
     * @return
     */
    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
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
     * @param key  message key
     * @param args optional arguments for the message
     * @return localized formatted string
     */
    public static String get(final String key, final Object... args) {
        ResourceBundle countries = ResourceBundle.getBundle("L10n/" + getLocale().getISO3Language()+ "/countries", getLocale());
        ResourceBundle products = ResourceBundle.getBundle("L10n/" + getLocale().getISO3Language() + "/products", getLocale());
        ResourceBundle ui = ResourceBundle.getBundle("L10n/" + getLocale().getISO3Language() + "/ui", getLocale());

        ArrayList<ResourceBundle> bundles = new ArrayList<>(List.of(
                countries, products, ui
        ));
        for (var bundle : bundles){
            if (bundle.containsKey(key)) return MessageFormat.format(bundle.getString(key), args);
        }
        return null;
    }

    /**
     * creates a String binding to a localized String for the given message bundle key
     *
     * @param key key
     * @return String binding
     */
    public static StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }

    public static StringBinding getShortNameBinding(Product product) {
        String shortNameKey = product.getDatabaseName() + ".short";
        return L10N.createStringBinding(shortNameKey);
    }
}
