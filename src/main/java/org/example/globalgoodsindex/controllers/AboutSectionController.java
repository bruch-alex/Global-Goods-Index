package org.example.globalgoodsindex.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class AboutSectionController {

    @FXML
    private Label appNameLabel;

    @FXML
    private VBox contentBox;

    public void initialize() {
        try {

            InputStream inputStream = getClass().getResourceAsStream("/data/about_section_data.json");

            if (inputStream == null) {
                throw new RuntimeException("File not found: /data/about_section_data.json");
            }

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = mapper.readValue(inputStream, Map.class);
            List<Map<String, String>> sections = (List<Map<String, String>>) data.get("sections");


            for (Map<String, String> section : sections) {
                addSection(section.get("title"), section.get("content"));
            }

        } catch (IOException e) {
            e.printStackTrace();
            addSection("Error", "Failed to load About section data.");
        }
    }

    private void addSection(String title, String content) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("title-label");

        Label contentLabel = new Label(content);
        contentLabel.getStyleClass().add("content-label");

        contentBox.getChildren().addAll(titleLabel, contentLabel);
    }

}
