package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.example.globalgoodsindex.services.AppConfig;
import org.example.globalgoodsindex.services.L10N;

import java.text.MessageFormat;

public class AboutSectionController {

    @FXML
    private VBox contentBox;

    public void initialize() {

        addDescriptionSection(
                L10N.get("about.description.content"),
                L10N.get("appName"),
                L10N.get("about.target_audience.content")
        );

        addAcknowledgmentSection(
                L10N.get("about.acknowledgment.title"),
                L10N.get("about.acknowledgment.content"),
                L10N.get("about.credit.content"),
                AppConfig.get("numbeo.url")
        );

        addDevelopersSection(
                L10N.get("about.developers.title"),
                new String[][]{
                        {"bruch-alex", "github.bruch-alex"},
                        {"nat-laz", "github.nat-laz"}
                }
        );

        addFeedbackAndShareSection(
                L10N.get("about.feedback.title"),
                L10N.get("about.feedback.content"),
                AppConfig.get("survey.url"),
                L10N.get("about.share.content")
        );

        addFooter(L10N.get("about.footer"));
    }

    private void addDescriptionSection(String description, String styledPart, String targetAudience) {
        TextFlow descriptionFlow = createStyledText(description, styledPart);
        Label targetAudienceLabel = createContentLabel(targetAudience);

        VBox sectionBox = new VBox(descriptionFlow, targetAudienceLabel);
        sectionBox.setSpacing(10);

        contentBox.getChildren().add(sectionBox);
    }

    private void addAcknowledgmentSection(String title, String acknowledgmentContent, String creditContent, String linkUrl) {
        Label titleLabel = createTitleLabel(title);

        TextFlow acknowledgmentFlow = createHyperlinkedText(
                acknowledgmentContent,
                "numbeo.com",
                linkUrl
        );

        TextFlow creditFlow = createHyperlinkedText(
                creditContent,
                "Numbeo",
                linkUrl
        );

        VBox sectionBox = new VBox(titleLabel, acknowledgmentFlow, creditFlow);
        sectionBox.setSpacing(10);

        contentBox.getChildren().add(sectionBox);
    }

    private void addDevelopersSection(String title, String[][] developersKeys) {
        Label titleLabel = createTitleLabel(title);

        HBox developersBox = new HBox();
        developersBox.setSpacing(15);
        developersBox.setAlignment(Pos.CENTER);

        for (String[] developer : developersKeys) {
            String name = developer[0];
            String propertyKey = developer[1];

            String link = AppConfig.get(propertyKey);
            if (link == null || link.isBlank()) {
                continue;
            }

            developersBox.getChildren().add(createDeveloperEntry(name, link));
        }


        VBox sectionBox = new VBox(titleLabel, developersBox);
        sectionBox.setSpacing(10);

        contentBox.getChildren().add(sectionBox);
    }


    private void addFeedbackAndShareSection(String title, String feedbackContent, String feedbackUrl, String shareContent) {
        Label titleLabel = createTitleLabel(title);

        String clickableText = L10N.get("about.feedback.clickable");
        TextFlow feedbackFlow = createHyperlinkedText(feedbackContent, clickableText, feedbackUrl);

        Text shareText = new Text(shareContent);
        shareText.getStyleClass().add("content-label");
        TextFlow shareFlow = new TextFlow(shareText);
        shareFlow.setTextAlignment(TextAlignment.CENTER);

        VBox sectionBox = new VBox(titleLabel, feedbackFlow, shareFlow);
        sectionBox.setSpacing(10);

        contentBox.getChildren().add(sectionBox);
    }


    private void addFooter(String footerText) {
        Label footerLabel = createFooterLabel(footerText);
        contentBox.getChildren().add(footerLabel);
    }

    // Utility methods for creating styled components
    private Label createTitleLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("title-label");
        label.setWrapText(true);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private Label createContentLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("content-label");
        label.setWrapText(true);
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
    }

    private TextFlow createStyledText(String fullText, String styledPart) {
        int styledIndex = fullText.indexOf(styledPart);

        if (styledIndex == -1) {
            throw new IllegalArgumentException("Styled part not found in full text.");
        }

        Text preText = new Text(fullText.substring(0, styledIndex));
        Text styledText = new Text(styledPart);
        styledText.setStyle("-fx-font-style: italic; -fx-text-fill: gray; -fx-font-family: Arial");
        Text postText = new Text(fullText.substring(styledIndex + styledPart.length()));

        TextFlow textFlow = new TextFlow(preText, styledText, postText);
        textFlow.getStyleClass().add("content-label");
        return textFlow;
    }

    private TextFlow createHyperlinkedText(String fullText, String hyperlinkText, String url) {
        String formattedText = MessageFormat.format(fullText, hyperlinkText);
        int hyperlinkIndex = formattedText.indexOf(hyperlinkText);

        if (hyperlinkIndex == -1) {
            throw new IllegalArgumentException("Hyperlink text not found in full text.");
        }

        Text preText = new Text(formattedText.substring(0, hyperlinkIndex));
        Hyperlink link = new Hyperlink(hyperlinkText);
        link.setOnAction(event -> MenuBarController.openWebsite(url));
        link.getStyleClass().add("hyperlink");
        Text postText = new Text(formattedText.substring(hyperlinkIndex + hyperlinkText.length()));

        TextFlow textFlow = new TextFlow(preText, link, postText);
        textFlow.getStyleClass().add("content-label");
        return textFlow;
    }


    private HBox createDeveloperEntry(String name, String url) {
        ImageView githubIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/github-icon.png")));
        githubIcon.setFitWidth(15);
        githubIcon.setFitHeight(15);
        githubIcon.setPreserveRatio(true);

        Hyperlink developerLink = new Hyperlink(name);
        developerLink.setOnAction(event -> MenuBarController.openWebsite(url));
        developerLink.getStyleClass().add("hyperlink");

        HBox developerBox = new HBox(githubIcon, developerLink);
        developerBox.setSpacing(4);
        developerBox.setAlignment(Pos.CENTER);

        return developerBox;
    }


    private Label createFooterLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("footer-label");
        label.setWrapText(true);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        return label;
    }

}
