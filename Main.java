import HelperClasses.HomePage;
import HelperClasses.Info;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.Node;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Labels
        Label homeLabel = new Label("Yale School of Art");
        Label aboutLabel = new Label("About the School");
        Label applyLabel = new Label("Apply to the School");
        Label newsLabel = new Label("News");
        Label eventsLabel = new Label("Public Events");
        Label publicationsLabel = new Label("Publications");

        // CSS for Labels
        homeLabel.setStyle("-fx-font-size: 30px;" + 
                           "-fx-font-weight: bold;" +
                           "-fx-text-fill: #00356B;");

        // Menu Bar Texts
        Text addressText = new Text(Info.ADDRESS_TEXT);
        addressText.setWrappingWidth(600);
        Text homeText = new Text(Info.HOME_TEXT);
        homeText.setWrappingWidth(600);
        Text aboutText = new Text(Info.ABOUT_TEXT);
        aboutText.setWrappingWidth(600);
        Text contactText = new Text(Info.CONTACT_TEXT);
        contactText.setWrappingWidth(600);
        Text applyText = new Text(Info.APPLY_TEXT);
        applyText.setWrappingWidth(600);
        Text newsText = new Text(Info.NEWS_TEXT);
        newsText.setWrappingWidth(600);
        Text eventsText = new Text(Info.EVENTS_TEXT);
        eventsText.setWrappingWidth(600);
        Text publicationsText = new Text(Info.PUBLICATIONS_TEXT);
        publicationsText.setWrappingWidth(600);

        // MenuBar and Menu items
        MenuBar menuBar = new MenuBar();
        Label homeButtonLabel = new Label("Home");
        Menu homeMenu = new Menu("", homeButtonLabel);
        Menu aboutMenu = new Menu("About");
        Menu applyMenu = new Menu("Apply");
        Menu newsMenu = new Menu("News");
        Label loginButtonLabel = new Label("Login");
        Menu logInMenu = new Menu("", loginButtonLabel);

        menuBar.getMenus().addAll(homeMenu, aboutMenu, applyMenu, newsMenu, logInMenu);

        // About Menu
        MenuItem historyMenu = new MenuItem("About Us");
        MenuItem contactMenu = new MenuItem("Contact");
        aboutMenu.getItems().addAll(historyMenu, new SeparatorMenuItem(), contactMenu);

        // Apply Menu
        MenuItem graduateMenu = new MenuItem("Undergraduate Admission");
        applyMenu.getItems().addAll(graduateMenu);

        // News Menu
        MenuItem newsTabMenu = new MenuItem("News");
        MenuItem eventsMenu = new MenuItem("Events");
        MenuItem publicationsMenu = new MenuItem("Publications");
        newsMenu.getItems().addAll(newsTabMenu, eventsMenu, publicationsMenu);

        // Buttons and TextField for login
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        Label userLabel = new Label("Username");
        TextField userField = new TextField();
        userLabel.setPadding(new Insets(5));
        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordLabel.setPadding(new Insets(5));

        // Login Box
        HBox userBox = new HBox(userLabel, userField);
        userBox.setSpacing(10);
        HBox passBox = new HBox(passwordLabel, passwordField);
        passBox.setSpacing(10);
        HBox buttonBox = new HBox(submitButton, cancelButton);
        buttonBox.setSpacing(10);
        VBox loginBox = new VBox(userBox, passBox, buttonBox);
        loginBox.setSpacing(20);

        // Styling
        String menuItemStyle = "-fx-font-size: 16px;" +
                               "-fx-padding: 0 20 0 20;";
        for (Menu menu : menuBar.getMenus()) {
            for (MenuItem menuItem : menu.getItems()) {
                menuItem.setStyle(menuItemStyle);
            }
        }

        String menuTitleStyle = "-fx-font-size: 20px;" + 
                                "-fx-min-width: 180px;" +
                                "-fx-pref-width: 200px;" +
                                "-fx-font-weight: bold;";
        for (Menu menu : menuBar.getMenus()) {
            menu.setStyle(menuTitleStyle);
        }

        menuBar.setMaxWidth(Double.MAX_VALUE);

        // Layout setup
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        HBox topBar = new HBox(homeLabel, menuBar);
        topBar.setSpacing(20);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10, 0, 10, 0));

        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER_LEFT);
        centerBox.setPadding(new Insets(20));
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(centerBox);

        // First load
        loadContent(centerBox, new HomePage().getContent());

        // Event handlers
        homeButtonLabel.setOnMouseClicked(mouseEvent -> loadContent(centerBox, new HomePage().getContent()));
        historyMenu.setOnAction(e -> loadContent(centerBox, aboutLabel, aboutText));
        contactMenu.setOnAction(e -> loadContent(centerBox, aboutLabel, contactText));
        graduateMenu.setOnAction(e -> loadContent(centerBox, applyLabel, applyText));
        newsTabMenu.setOnAction(e -> loadContent(centerBox, newsLabel, newsText));
        eventsMenu.setOnAction(e -> loadContent(centerBox, eventsLabel, eventsText));
        publicationsMenu.setOnAction(e -> loadContent(centerBox, publicationsLabel, publicationsText));
        loginButtonLabel.setOnMouseClicked(mouseEvent -> loadContent(centerBox, loginBox));

        // Scene creation
        Scene scene = new Scene(root, 1350, 900);
        primaryStage.setTitle("Yale School of Arts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadContent(VBox container, Node... content) {
        container.getChildren().clear();
        container.getChildren().addAll(content);
    }
}