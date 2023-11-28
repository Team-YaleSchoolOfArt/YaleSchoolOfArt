package HelperClasses;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Upload {

    public static Text statusText = new Text("");
    public static VBox vbox = new VBox();

    public static File file;

    public static Node getContent() {
        final double TEXT_WRAP_WIDTH = 300;

        // Set font for the upload heading
        Font uploadFont = Font.font("Bauhaus 93", FontWeight.BOLD, 55);

        VBox topSection = createStyledTextSection("Upload", TEXT_WRAP_WIDTH, uploadFont, null);
        topSection.setAlignment(Pos.CENTER);

        // Name field
        TextField nameField = new TextField();
        nameField.setPromptText("Your Name");
        nameField.setMaxWidth(300);
        nameField.setPadding(new Insets(5));

        // File chooser for image upload
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Button chooseFileButton = new Button("Choose Image File");
        chooseFileButton.setOnAction(event -> handleFileSelection(fileChooser));

        // Message for upload status
        statusText = new Text("");

        VBox middleSection = new VBox(50, statusText, nameField, chooseFileButton);
        middleSection.setAlignment(Pos.CENTER);
        middleSection.setSpacing(10);
        middleSection.setPadding(new Insets(50, 0, 50, 0));

        // Upload button
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(event -> handleUpload(nameField));
        VBox bottomSection = new VBox(uploadButton);
        bottomSection.setAlignment(Pos.CENTER);

        vbox = new VBox(topSection, middleSection, bottomSection);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        vbox.setPadding(new Insets(20));

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

    private static void handleFileSelection(FileChooser fileChooser) {
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            file = selectedFile;
        } else {
            statusText.setText("No file selected.");
            statusText.setFill(Color.RED);
        }
    }

    private static void handleUpload(TextField nameField) {
        String name = nameField.getText();
        if (name.isEmpty()) {
            statusText.setText("Please Enter Your Name.");
            statusText.setFill(Color.RED);
        } else {
            // Perform upload logic here
            statusText.setText("Uploading...");
            statusText.setFill(Color.GREEN);
            
            if (file != null) {
                try {
                    String projectDirectory = System.getProperty("user.dir");

                    // Copy the uploaded file to the project directory
                    File destination = new File(projectDirectory + File.separator + file.getName());
                    Files.copy(file.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    // Append a new line to the gallery_data.txt file
                    appendToGalleryData(file.getName(), name, projectDirectory);

                    statusText.setText("Upload Successful!");
                    statusText.setFill(Color.GREEN);
                } catch (IOException e) {
                    e.printStackTrace();
                    statusText.setText("Error Uploading File.");
                    statusText.setFill(Color.RED);
                }
            } else {
                statusText.setText("No file selected.");
                statusText.setFill(Color.RED);
            }
        }
    }

    private static void appendToGalleryData(String fileName, String creator, String projectDirectory) {
    // Read the existing content of the gallery_data.txt file
    List<String> lines;
    try {
        Path filePath = Paths.get(projectDirectory, "gallery_data.txt");
        lines = Files.readAllLines(filePath);
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }

    // Append a new line with the uploaded image information
    String newLine = fileName + ", Created By: " + creator;
    lines.add(newLine);

    // Write the updated content back to the gallery_data.txt file
    try {
        Path filePath = Paths.get(projectDirectory, "gallery_data.txt");
        Files.write(filePath, lines);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}