import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameOptionsWindow extends Application {
    private String mainPlayerName;
    private MonopolyPiece mainPlayerPiece;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public GameOptionsWindow(String mainPlayerName, MonopolyPiece mainPlayerPiece) {
        this.mainPlayerName = mainPlayerName;
        this.mainPlayerPiece = mainPlayerPiece;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Options");

        // Makes dropdown menu with numbers 1 to 7, use for number of bot players
        ComboBox<Integer> numberDropdown = new ComboBox<>();
        for (int i = 1; i <= 3; i++) {
            numberDropdown.getItems().add(i);
        }
        numberDropdown.setValue(1); // Default selection

        // Start Game button
        Button startGameButton = new Button("Start Game");
        startGameButton.setDisable(true); // Initially disable the button

        // Window layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(numberDropdown);

        ObservableList<ComboBox<Integer>> playerDropdowns = FXCollections.observableArrayList();
        // A list to hold the name text fields and piece selection dropdowns for players
        ObservableList<TextField> playerNameFields = FXCollections.observableArrayList();
        ObservableList<ComboBox<MonopolyPiece>> pieceDropdowns = FXCollections.observableArrayList();

        numberDropdown.setOnAction(e -> {
            int selectedNumber = numberDropdown.getValue();
            System.out.println("Selected Number: " + selectedNumber);

            playerDropdowns.clear();
            playerNameFields.clear();
            pieceDropdowns.clear();

            for (int i = 1; i <= selectedNumber; i++) {
                VBox playerBox = new VBox(10); // Create a VBox to hold player information
                playerBox.setStyle("-fx-background-color: white; -fx-padding: 10;"); // Set a white background with padding
                Label playerNameLabel = new Label("Player " + i + " Name:");
                TextField playerNameField = new TextField();

                Label pieceLabel = new Label("Choose piece:");
                ComboBox<MonopolyPiece> pieceDropdown = new ComboBox<>(); // <-- This is the dropdown for the current loop iteration

                // Directly add MonopolyPiece values to the dropdown
                pieceDropdown.getItems().addAll(MonopolyPiece.values());

                // Set a default value for the ComboBox.
                pieceDropdown.setValue(MonopolyPiece.CAR);

                // Add an event listener to handle piece selection. Now, you directly get the MonopolyPiece enum value.
                pieceDropdown.setOnAction(event -> {
                    MonopolyPiece selectedPiece = pieceDropdown.getValue();
                    if (selectedPiece != null) {
                        // No need for conversion. You have the actual enum value.
                        // Use selectedPiece directly.
                    }
                });

                playerBox.getChildren().addAll(playerNameLabel, playerNameField, pieceLabel, pieceDropdown);
                layout.getChildren().add(playerBox);

                ComboBox<Integer> playerDropdown = new ComboBox<>();
                playerDropdown.getItems().addAll(1, 2);
                playerDropdown.setValue(1);
                layout.getChildren().add(playerDropdown);
                
                playerDropdowns.add(playerDropdown);
                playerNameFields.add(playerNameField);
                pieceDropdowns.add(pieceDropdown);
            }

            layout.getChildren().add(startGameButton);
            startGameButton.setDisable(false);
        });
       
        startGameButton.setOnAction(e -> {
            int selectedNumber = numberDropdown.getValue();
            System.out.println("Selected Number: " + selectedNumber);

            int[] playerConfigurations = new int[selectedNumber + 1];  // +1 for the main player
            String[] playerNames = new String[selectedNumber + 1];     // +1 for the main player
            MonopolyPiece[] playerPieces = new MonopolyPiece[selectedNumber + 1];    // +1 for the main player

            // Add the main player's details to the start of the arrays
            playerNames[0] = this.mainPlayerName;
            playerPieces[0] = this.mainPlayerPiece;
            // Here you can set a default or special configuration value for the main player if needed.
            playerConfigurations[0] = 0; // for instance

            for (int i = 1; i <= selectedNumber; i++) {
                if (i-1 < playerDropdowns.size() && i-1 < playerNameFields.size() && i-1 < pieceDropdowns.size()) {
                    playerConfigurations[i] = playerDropdowns.get(i-1).getValue();
                    playerNames[i] = playerNameFields.get(i-1).getText();
                    playerPieces[i] = pieceDropdowns.get(i-1).getValue();

                    System.out.println(playerNames[i] + " - " + playerPieces[i] + " - Strategy: " + playerConfigurations[i]);
                } else {
                }
            }

            // Launch game
            MonopolyApp game = new MonopolyApp(playerConfigurations, playerNames, playerPieces);
            Stage gameStage = new Stage();
            game.start(gameStage);
            primaryStage.close();
        });



        // Load background image
        Image backgroundImage = new Image(getClass().getClassLoader().getResource("optionsMenu.jpg").toExternalForm());
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(1.0, 1.0, true, true, false, false) // make window adjustable
        );
        layout.setBackground(new Background(background));
       
       
        Scene scene = new Scene(layout, 1000, 621);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}