package org.example.globalgoodsindex.core.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

public class Entry {
    private final String name;
    private final double price;
    private BooleanProperty isSelected;

    public Entry(String name, double price) {
        this.name = name;
        this.price = price;
        this.isSelected = new SimpleBooleanProperty(false);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isSelected() {
        return this.isSelected.get();
    }

    public BooleanProperty selectedProperty(){
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected.set(selected);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", isSelected=" + isSelected +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(name, entry.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
