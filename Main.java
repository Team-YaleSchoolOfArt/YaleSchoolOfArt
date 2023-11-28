import HelperClasses.AboutUs;
import HelperClasses.Apply;
import HelperClasses.Contact;
import HelperClasses.HomePage;
import HelperClasses.Info;
import HelperClasses.Login;
import HelperClasses.News;
import HelperClasses.Publication;
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

        // CSS for Labels
        homeLabel.setStyle("-fx-font-size: 30px;" + 
                           "-fx-font-weight: bold;" +
                           "-fx-text-fill: #00356B;");

        // Menu Bar Texts
        Text homeText = new Text(Info.HOME_TEXT);
        homeText.setWrappingWidth(600);
        Text aboutText = new Text(Info.ABOUT_TEXT);
        aboutText.setWrappingWidth(600);
        Text contactText = new Text(Info.CONTACT_TEXT);
        contactText.setWrappingWidth(600);
        Text applyText = new Text(Info.APPLY_TEXT);
        applyText.setWrappingWidth(600);
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
        MenuItem aboutUsMenu = new MenuItem("About Us");
        MenuItem contactMenu = new MenuItem("Contact");
        aboutMenu.getItems().addAll(aboutUsMenu, new SeparatorMenuItem(), contactMenu);

        // Apply Menu
        MenuItem graduateMenu = new MenuItem("Undergraduate Admission");
        applyMenu.getItems().addAll(graduateMenu);

        // News Menu
        MenuItem latestNewsMenu = new MenuItem("Latest News");
        MenuItem publicationsMenu = new MenuItem("Publications");
        newsMenu.getItems().addAll(latestNewsMenu, publicationsMenu);

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
        aboutUsMenu.setOnAction(e -> loadContent(centerBox, AboutUs.getContent()));
        contactMenu.setOnAction(e -> loadContent(centerBox, Contact.getContent()));
        graduateMenu.setOnAction(e -> loadContent(centerBox, Apply.getContent(getHostServices())));
        latestNewsMenu.setOnAction(e -> loadContent(centerBox, News.getContent(getHostServices())));
        publicationsMenu.setOnAction(e -> loadContent(centerBox, Publication.getContent(getHostServices())));
        loginButtonLabel.setOnMouseClicked(mouseEvent -> loadContent(centerBox, Login.getContent()));

        // Scene creation
        Scene scene = new Scene(root, 1400, 1200);
        primaryStage.setTitle("Yale School of Arts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadContent(VBox container, Node... content) {
        container.getChildren().clear();
        container.getChildren().addAll(content);
    }
}