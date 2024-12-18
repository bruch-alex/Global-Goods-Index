package org.example.globalgoodsindex.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.globalgoodsindex.services.L10N;

public abstract class Entry {
    protected final StringProperty nameProperty;
    protected final BooleanProperty selectedProperty;

    public Entry(String name) {
        this.nameProperty = new SimpleStringProperty();
        this.nameProperty.bind(L10N.createStringBinding(name));
        this.selectedProperty = new SimpleBooleanProperty(false);
        checkTranslation(name);
    }

    public String getName() {
        return this.nameProperty.getValue();
    }

    public StringProperty nameProperty() {
        return this.nameProperty;
    }

    public boolean isSelected() {
        return this.selectedProperty.get();
    }

    public BooleanProperty selectedProperty() {
        return this.selectedProperty;
    }

    private void checkTranslation(String name){
        if(this.getName() == null) System.out.println("there is no name for " + name);
    }
}
