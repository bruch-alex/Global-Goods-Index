package org.example.globalgoodsindex.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
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
                + "-fx-border-radius: 2px; -fx-background-radius: 2px;" + "-fx-font-size: 18");
        label.setWrapText(true);
        label.setPrefWidth(250);

        StackPane pane = new StackPane(label);
        pane.setStyle("-fx-background-color: transparent;");
        pane.setPadding(new Insets(0, 10, 10, 0));

        popup.getContent().add(pane);

        // Positioning the toast
        popup.setOnShown(e -> {
            double centerX = ownerStage.getX() + (ownerStage.getWidth() / 2) - (pane.getWidth() / 2);
            double centerY = ownerStage.getY() + (ownerStage.getHeight() / 2) - (pane.getHeight() / 2);

            popup.setX(centerX);
            popup.setY(centerY);
        });

        popup.show(ownerStage);

        // Auto-hide after the specified duration
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(durationInMillis), ae -> popup.hide()));
        timeline.play();
    }
}
