import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameOptionsWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Options");

        // Makes dropdown menu with numbers 1 to 7, use for number of bot players
        ComboBox<Integer> numberDropdown = new ComboBox<>();
        for (int i = 1; i <= 7; i++) {
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

        numberDropdown.setOnAction(e -> {
            int selectedNumber = numberDropdown.getValue();
            System.out.println("Selected Number: " + selectedNumber);

            playerDropdowns.clear(); // Clear existing player selection boxes
            //dropdowns for choosing strategy, reduce to 2
            for (int i = 1; i <= selectedNumber; i++) {
                ComboBox<Integer> playerDropdown = new ComboBox<>();
                playerDropdown.getItems().addAll(1, 2, 3);
                playerDropdown.setValue(1);
                playerDropdowns.add(playerDropdown);
            }

            layout.getChildren().addAll(playerDropdowns);
            layout.getChildren().add(startGameButton);
            startGameButton.setDisable(false);
            
        });
        
        startGameButton.setOnAction(e -> {
            int selectedNumber = numberDropdown.getValue();
            System.out.println("Selected Number: " + selectedNumber); //debug print

            // Array to store the player configurations
            int[] playerConfigurations = new int[selectedNumber];
            for (int i = 0; i < selectedNumber; i++) {
                playerConfigurations[i] = playerDropdowns.get(i).getValue();
            }

            // Launch game
            MonopolyApp game = new MonopolyApp();
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
            BackgroundSize.DEFAULT
        );
        layout.setBackground(new Background(background));

        Scene scene = new Scene(layout, 1000, 621);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
