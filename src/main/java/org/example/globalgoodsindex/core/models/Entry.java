package org.example.globalgoodsindex.core.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.globalgoodsindex.core.services.L10N;

public abstract class Entry {
    protected final String name;
    protected final StringProperty nameProperty;
    protected final BooleanProperty selectedProperty;

    public Entry(String name) {
        this.name = name;
        this.nameProperty = new SimpleStringProperty();
        this.nameProperty.bind(L10N.createStringBinding(name));
        this.selectedProperty = new SimpleBooleanProperty(false);
    }

    public String getName() {
        return this.name;
    }

    public String getLocalizedName() {
        return this.name;
    }

    public StringProperty nameProperty() {
        return this.nameProperty;
    }

    public boolean isSelected() {
        return this.selectedProperty.get();
    }

    public void setSelected(boolean selected) {
        this.selectedProperty.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return this.selectedProperty;
    }
}
