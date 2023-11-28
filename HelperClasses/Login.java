package HelperClasses;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Login {

    public static Text statusText = new Text("");
    public static VBox vbox = new VBox();

    public static Node getContent() {
        final double TEXT_WRAP_WIDTH = 300;

        // Set font for the login heading
        Font loginFont = Font.font("Bauhaus 93", FontWeight.BOLD, 55);

        VBox topSection = createStyledTextSection("Login", TEXT_WRAP_WIDTH, loginFont, null);
        topSection.setAlignment(Pos.CENTER);

        // Username and Password fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        usernameField.setPadding(new Insets(5));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setPadding(new Insets(5));

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> handleLogin(usernameField, passwordField));

        // Message for login status
        statusText = new Text("");

        VBox middleSection = new VBox(50, statusText, usernameField, passwordField);
        middleSection.setAlignment(Pos.CENTER);
        middleSection.setSpacing(10);
        middleSection.setPadding(new Insets(50, 0, 50, 0));

        VBox bottomSection = new VBox(loginButton);
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

    private static void handleLogin(TextField username, TextField password) 
    {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            statusText.setText("Please Enter A Username & Password.");
            statusText.setFill(Color.RED);
        }
        if (username.getText().equals("Username") && password.getText().equals("Password"))
        {
            statusText.setText("Logging In...");
            statusText.setFill(Color.GREEN);
            vbox.getChildren().clear();
            vbox.getChildren().setAll(Upload.getContent());
        }
        else
        {
            username.clear();
            password.clear();
            statusText.setText("Wrong Credentials");
            statusText.setFill(Color.RED);
        }
    }
}