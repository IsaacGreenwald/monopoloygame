import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class MonopolyStartMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Monopoly - Start Menu");

        // Make StackPane to hold background
        StackPane stackPane = new StackPane();

        // Load background image
        Image backgroundImage = new Image(getClass().getClassLoader().getResource("startMenu.jpg").toExternalForm());
        BackgroundImage backgroundImageObj = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        // Set background image to the StackPane
        Background background = new Background(backgroundImageObj);
        stackPane.setBackground(background);

        Button newGameButton = new Button("New Game");
        Button loadGameButton = new Button("Load Game");

        newGameButton.setOnAction(e -> {
            GameOptionsWindow optionsWindow = new GameOptionsWindow();
            Stage optionsStage = new Stage();
            optionsWindow.start(optionsStage);
            primaryStage.close();
        });

        loadGameButton.setOnAction(e -> {
            // Add code to load a saved game
        });

        // Make VBox to hold buttons
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(newGameButton, loadGameButton);

        // Add VBox to StackPane
        stackPane.getChildren().add(buttonBox);

        Scene scene = new Scene(stackPane, 735, 551);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
