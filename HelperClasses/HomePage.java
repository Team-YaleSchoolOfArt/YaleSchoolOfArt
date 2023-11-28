package HelperClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HomePage {

    public class ImageTracker {
        public String image;
        public String author;

        public ImageTracker(String image, String author) {
            this.image = image;
            this.author = author;
        }
    }

    private LinkedList<ImageTracker> gallery;
    private int galleryIndex;

    public HomePage() {
        this.gallery = new LinkedList<>();
        this.galleryIndex = 0;
        getGallery();
    }

    public Node getContent() {
        final double TEXT_WRAP_WIDTH = 1300;
        final double IMAGE_WIDTH = 275;
        final double IMAGE_HEIGHT = 275;

        // Blue and white theme
        Color blueColor = Color.web("#00356B");

        // Set font for the gallery heading
        Font welcomeFont = Font.font("Palatino Linotype", FontWeight.BOLD, 18);
        Font galleryFont = Font.font("Bauhaus 93", FontWeight.BOLD, 60);
        Font footnoteFont = Font.font("Palatino Linotype", FontWeight.BOLD, 14);

        VBox topSection = createStyledTextSection(Info.HOME_TEXT, TEXT_WRAP_WIDTH, welcomeFont, null);
        VBox middleSection = createStyledTextSection("Public Gallery", TEXT_WRAP_WIDTH, galleryFont, blueColor);

        HBox imageBox = createImageGalleryBox(IMAGE_WIDTH, IMAGE_HEIGHT);
        Text bottomText = new Text("Yale School of Art\n1156 Chapel Street, POB 208339\nNew Haven, Connecticut, 06520-8339");
        bottomText.setWrappingWidth(TEXT_WRAP_WIDTH);
        VBox bottomSection = createStyledTextSection(bottomText.getText(), TEXT_WRAP_WIDTH, footnoteFont, blueColor);
        middleSection.getChildren().add(imageBox);
        middleSection.setSpacing(30);

        VBox vbox = new VBox(topSection, middleSection, bottomSection);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(100);
        return vbox;
    }

    private VBox createStyledTextSection(String content, double wrapWidth, Font font, Color textColor) {
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

    private HBox createImageGalleryBox(double IMAGE_WIDTH, double IMAGE_HEIGHT) {
        HBox imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setSpacing(20.0);

        Button leftButton = new Button("<");
        Button rightButton = new Button(">");
        leftButton.setOnAction((event) -> refreshGalleryLeft(imageBox, IMAGE_WIDTH, IMAGE_HEIGHT, leftButton, rightButton));
        rightButton.setOnAction((event) -> refreshGalleryRight(imageBox, IMAGE_WIDTH, IMAGE_HEIGHT, leftButton, rightButton));

        imageBox.getChildren().add(leftButton);

        for (int i = 0; i < 4; i++) {
            if (this.galleryIndex >= this.gallery.size() || this.galleryIndex < 0)
                this.galleryIndex = 0;

            imageBox.getChildren().add(createImageView(
                    this.gallery.get(galleryIndex).image,
                    this.gallery.get(galleryIndex).author,
                    IMAGE_WIDTH,
                    IMAGE_HEIGHT));
            this.galleryIndex++;
        }

        imageBox.getChildren().add(rightButton);
        return imageBox;
    }

    private void refreshGalleryRight(HBox imageBox, double IMAGE_WIDTH, double IMAGE_HEIGHT, Button leftButton, Button rightButton) {
        imageBox.getChildren().clear();
        imageBox.getChildren().add(leftButton);

        for (int i = 0; i < 4; i++) {
            if (this.galleryIndex >= this.gallery.size() || this.galleryIndex < 0)
                this.galleryIndex = 0;

            imageBox.getChildren().add(createImageView(
                    this.gallery.get(galleryIndex).image,
                    this.gallery.get(galleryIndex).author,
                    IMAGE_WIDTH,
                    IMAGE_HEIGHT));
            this.galleryIndex++;
        }

        imageBox.getChildren().add(rightButton);
    }

    private void refreshGalleryLeft(HBox imageBox, double IMAGE_WIDTH, double IMAGE_HEIGHT, Button leftButton, Button rightButton) {
        imageBox.getChildren().clear();
        imageBox.getChildren().add(leftButton);

        for (int i = 0; i < 4; i++) {
            if (this.galleryIndex < 0 || this.galleryIndex >= this.gallery.size())
                this.galleryIndex = this.gallery.size() - 1;

            imageBox.getChildren().add(1, createImageView(
                    this.gallery.get(galleryIndex).image,
                    this.gallery.get(galleryIndex).author,
                    IMAGE_WIDTH,
                    IMAGE_HEIGHT));
            this.galleryIndex--;
        }

        imageBox.getChildren().add(rightButton);
    }

    public static HBox createImageView(String imagePath, String text, double prefWidth, double prefHeight) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(prefWidth);
        imageView.setFitHeight(prefHeight);
        imageView.setPreserveRatio(false);

        Label authorLabel = new Label(text);
        authorLabel.setPadding(new Insets(5, 5, 5, 5));

        Rectangle overlay = new Rectangle(0, 0, 0, 0);
        overlay.setFill(Color.WHITE);

        StackPane stackPane = new StackPane(imageView, overlay, authorLabel);
        overlay.widthProperty().bind(authorLabel.widthProperty());
        overlay.heightProperty().bind(authorLabel.heightProperty());
        StackPane.setAlignment(authorLabel, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(overlay, Pos.BOTTOM_RIGHT);

        HBox hbox = new HBox(stackPane);
        stackPane.setMinSize(prefWidth, prefHeight);
        stackPane.setMaxSize(prefWidth, prefHeight);
        hbox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        return new HBox(hbox);
    }

    private void getGallery() {
        try (BufferedReader reader = new BufferedReader(new FileReader("gallery_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String imageFilename = parts[0];
                    String authorText = parts[1];
                    gallery.add(new ImageTracker(imageFilename, authorText));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}