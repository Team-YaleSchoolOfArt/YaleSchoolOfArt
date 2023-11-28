package HelperClasses;

import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Apply {
    public static Node getContent(HostServices host) {
        final double TEXT_WRAP_WIDTH = 900;
        final double IMAGE_WIDTH = 275;
        final double IMAGE_HEIGHT = 570;

        // Blue and white theme
        Color blueColor = Color.web("#00356B");

        // Set font for the gallery heading
        Font welcomeFont = Font.font("Bauhaus 93", FontWeight.BOLD, 32);
        Font footnoteFont = Font.font("Palatino Linotype", FontWeight.BOLD, 14);

        VBox topSection = createStyledTextSection("Undergraduate Application", TEXT_WRAP_WIDTH, welcomeFont, null);
        topSection.setAlignment(Pos.CENTER);

        VBox middleSection = new VBox();
        middleSection.getChildren().add(
                createBodyLeft("/resources/apply1.png", 
                        Info.APPLY_TEXT,
                        IMAGE_WIDTH, IMAGE_HEIGHT, TEXT_WRAP_WIDTH, host)
        );

        middleSection.setAlignment(Pos.CENTER);
        middleSection.setPadding(new Insets(0, 60, 0, 60));

        Text bottomText = new Text("Yale School of Art\n1156 Chapel Street, POB 208339\nNew Haven, Connecticut, 06520-8339");
        VBox bottomSection = createStyledTextSection(bottomText.getText(), TEXT_WRAP_WIDTH, footnoteFont, blueColor);
        bottomSection.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(topSection, middleSection, bottomSection);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(100);
        return vbox;
    }

    private static VBox createStyledTextSection(String content, double wrapWidth, Font font, Color textColor) {
        Text text = new Text(content);
        text.setWrappingWidth(wrapWidth);
        text.setTextAlignment(TextAlignment.CENTER);

        // Apply styling
        if (font != null) {
            text.setFont(font);
        }
        if (textColor != null) {
            text.setFill(textColor);
        }

        return new VBox(text);
    }

    public static HBox createBodyLeft(String imagePath, String text, double prefWidth, double prefHeight,
                                      double prefWrapWidth, HostServices host) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(prefWidth);
        imageView.setFitHeight(prefHeight);
        imageView.setPreserveRatio(false);
        imageView.setOnMouseEntered(event -> {
            // Highlight the image when hovered
            imageView.setEffect(new javafx.scene.effect.DropShadow());
        });

        imageView.setOnMouseExited(event -> {
            // Remove the highlight effect when the mouse exits
            imageView.setEffect(null);
        });

        // Create a Text node for the paragraph
        Text paragraph = new Text(text);
        paragraph.setWrappingWidth(prefWrapWidth);

        // Set font and style for the Text node
        Font font = Font.font("Arial", 18);
        paragraph.setFont(font);
        paragraph.setStyle("-fx-font-weight: bold;");

        // Add spacing between lines and set text alignment
        paragraph.setLineSpacing(5);
        paragraph.setTextAlignment(TextAlignment.JUSTIFY);

        StackPane stackPane = new StackPane(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER_LEFT);
        StackPane.setAlignment(paragraph, Pos.CENTER_LEFT);
        stackPane.setOnMouseClicked((event) -> {
            host.showDocument("https://apply.art.yale.edu/apply/");
        });

        HBox hbox = new HBox(stackPane);
        stackPane.setMinSize(prefWidth, prefHeight);
        stackPane.setMaxSize(prefWidth, prefHeight);
        hbox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        HBox finalHBox = new HBox(paragraph, hbox);
        finalHBox.setAlignment(Pos.CENTER);
        finalHBox.setSpacing(50);
        return finalHBox;
    }
}