package org.example.globalgoodsindex.controllers;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import org.example.globalgoodsindex.core.models.Entry;

public class ListCellCheckBox extends ListCell<Entry> {
    private final CheckBox checkBox = new CheckBox();

    @Override
    protected void updateItem(Entry item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            checkBox.setText(item.getName());
            checkBox.selectedProperty().bindBidirectional(item.selectedProperty());
            setGraphic(checkBox);
        }
    }
}
