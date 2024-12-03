package org.example.globalgoodsindex.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ToastNotifyController15ProMax {
    public static void showToast(Stage ownerStage, String message, int durationInMillis) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);

        Label label = new Label(message);
        label.setStyle("-fx-background-color: #0969da; -fx-text-fill: #f6f8fa; -fx-padding: 10px; "
                + "-fx-border-radius: 2px; -fx-background-radius: 2px;");
        label.setWrapText(true);
        label.setPrefWidth(180);

        StackPane pane = new StackPane(label);
        pane.setStyle("-fx-background-color: transparent;");
        pane.setPadding(new Insets(0, 10, 10, 0));
        pane.setAlignment(Pos.BOTTOM_LEFT);

        popup.getContent().add(pane);

        // Positioning the toast
        popup.setOnShown(e -> {
            popup.setX(ownerStage.getX()); // => left offset
            popup.setY(ownerStage.getY() + ownerStage.getHeight() - pane.getHeight() - 80); // => top offset
        });

        popup.show(ownerStage);

        // Auto-hide after the specified duration
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(durationInMillis), ae -> popup.hide()));
        timeline.play();
    }
}
