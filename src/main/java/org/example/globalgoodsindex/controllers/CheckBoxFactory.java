package org.example.globalgoodsindex.controllers;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import org.example.globalgoodsindex.core.models.Entry;
import org.example.globalgoodsindex.core.services.L10N;

public class CheckBoxFactory extends ListCell<Entry> {
    @Override
    protected void updateItem(Entry entry, boolean empty) {
        super.updateItem(entry, empty);
        //System.out.println("updateItem called for: " + (entry != null ? entry.getName() : "empty"));
        if (empty || entry == null) {
            setGraphic(null);
        } else {
            CheckBox checkBox = new CheckBox();
            checkBox.textProperty().bind(L10N.createStringBinding(entry.getName()));
            checkBox.selectedProperty().bindBidirectional(entry.selectedProperty());

            // Listener for debugging only
            checkBox.selectedProperty().addListener((_, oldValue, newValue) -> {
                if (newValue || oldValue) {
                    // debugging
                    System.out.println("Changed value for " + checkBox.getText() + " from " + oldValue + " to " + newValue);
                    System.out.println("Value for " + entry.getName() + " is " + entry.isSelected());
                }
            });

            setGraphic(checkBox);
        }
    }
}
