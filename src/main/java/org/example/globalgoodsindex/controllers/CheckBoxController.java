package org.example.globalgoodsindex.controllers;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import org.example.globalgoodsindex.core.models.Entry;

public class CheckBoxController extends ListCell<Entry> {
    private final CheckBox checkBox = new CheckBox();

    @Override
    protected void updateItem(Entry entry, boolean empty) {
        super.updateItem(entry, empty);

        if (empty || entry == null) {
            setGraphic(null);
        } else {
            checkBox.setText(entry.getName());
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue || oldValue) {
                    // sout for debugging
                    System.out.println("Changed value for " + entry.getName() + " from " + oldValue + " to " + newValue);
                    entry.changeValue();
                }
            });
            setGraphic(checkBox);
        }
    }
}
