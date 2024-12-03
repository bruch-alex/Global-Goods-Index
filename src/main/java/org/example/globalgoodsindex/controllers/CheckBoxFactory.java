package org.example.globalgoodsindex.controllers;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import org.example.globalgoodsindex.models.Entry;
import org.example.globalgoodsindex.models.Product;
import org.example.globalgoodsindex.services.L10N;

public class CheckBoxFactory extends ListCell<Entry> {
    @Override
    protected void updateItem(Entry entry, boolean empty) {
        super.updateItem(entry, empty);
        //System.out.println("updateItem called for: " + (entry != null ? entry.getName() : "empty"));
        if (empty || entry == null) {
            setGraphic(null);
        } else {
            CheckBox checkBox = new CheckBox();
            if (entry instanceof Product) {
                checkBox.textProperty().bind(L10N.getShortNameBinding((Product) entry));
            } else {
                checkBox.textProperty().bind(entry.nameProperty());
            }

            checkBox.selectedProperty().bindBidirectional(entry.selectedProperty());

//            // Listener for debugging only
//            checkBox.selectedProperty().addListener((_, oldValue, newValue) -> {
//                if (newValue || oldValue) {
//                    System.out.println("Changed value for checkbox " + checkBox.getText() + " from " + oldValue + " to " + newValue);
//                    System.out.println("Value for instance " + entry.getName() + " is " + entry.isSelected());
//                }
//            });

            setGraphic(checkBox);
        }
    }
}
