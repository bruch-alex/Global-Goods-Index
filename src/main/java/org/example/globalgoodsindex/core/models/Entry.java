package org.example.globalgoodsindex.core.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public abstract class Entry {
    protected final String name;
    protected final BooleanProperty selectedProperty;

    public Entry(String name) {
        this.name = name;
        this.selectedProperty = new SimpleBooleanProperty(false);
    }

    public String getName() {
        return this.name;
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
