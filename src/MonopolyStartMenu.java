import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class MonopolyStartMenu extends Application {
    private String playerName; // Store the player's name
    private MonopolyPiece playerPiece; // Store the player's selected piece

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Monopoly - Start Menu");

        // Create a StackPane to hold the background
        StackPane stackPane = new StackPane();

        // Load the background image using the same approach as in MonopolyApp
        Image backgroundImage = new Image(getClass().getClassLoader().getResource("startMenu.jpg").toExternalForm());
        BackgroundImage backgroundImageObj = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false) // make window adjustable
        );

        // Set the background image to the StackPane
        Background background = new Background(backgroundImageObj);
        stackPane.setBackground(background);

        // Create a TextField for the player's name
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        Button newGameButton = new Button("New Game");
        Button loadGameButton = new Button("Load Game");
        newGameButton.setDisable(true); // Initially disable the button

        // Create a list of available pieces
        ComboBox<MonopolyPiece> pieceSelector = new ComboBox<>();
        pieceSelector.getItems().addAll(MonopolyPiece.values());
        pieceSelector.setPromptText("Pick your piece");

        // Add listeners to update the state of the New Game button and store the selected values
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            playerName = newValue.trim();
            newGameButton.setDisable(playerName.isEmpty() || pieceSelector.getValue() == null);
        });

        pieceSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                playerPiece = newValue;
            }
            newGameButton.setDisable(nameField.getText().trim().isEmpty() || playerPiece == null);
        });

        newGameButton.setOnAction(e -> {
            // Check if playerName and playerPiece are not null and proceed to the game
            if (playerName != null && playerPiece != null) {
                System.out.println(playerName + " selected the piece " + playerPiece);
                GameOptionsWindow optionsWindow = new GameOptionsWindow(playerName, playerPiece);
                Stage optionsStage = new Stage();
                optionsWindow.start(optionsStage);
                primaryStage.close();
            }
        });

        // Create a VBox to hold the name field, piece selector, buttons, and add spacing
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(nameField, pieceSelector, newGameButton, loadGameButton);

        // Add the VBox to the StackPane
        stackPane.getChildren().add(buttonBox);

        Scene scene = new Scene(stackPane, 735, 551);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
