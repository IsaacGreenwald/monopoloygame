import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is the main UI class for our monopoly game and it utilizes javaFX
 */
public class MonopolyApp extends Application {

	private VBox playerInfoContainer;
	private Board board;
	private AnchorPane root;
	private GameDatabaseOperations dbOps;
	private GridPane gameGrid; 
	
	private static final String[] DICE_IMAGES = {
			"Dice1.png", "Dice2.png", "Dice3.png", "Dice4.png", "Dice5.png", "Dice6.png"
	};

	private static final double DICE_SIZE = 60.0; 
	private static final double spotWidth = 109.9;
	private static final double spotHeight = 83.2;
	private int currentPlayerIndex = 0;
	List<Player> players = new ArrayList<>();
	private Stage primaryStage;
	
	public MonopolyApp(int[] playerConfigurations, String[] playerNames, MonopolyPiece[] playerPieces) {
		
	    System.out.println("Contents of playerPieces array:");
	    for (MonopolyPiece piece : playerPieces) {
	        System.out.println(piece);
	    }
	    if (playerNames.length != playerPieces.length || playerNames.length > 4 || playerConfigurations.length > 3) {
	        System.err.println("Invalid input arrays.");
	        System.out.println(playerNames.length);
	        return;
	    }

	    // First player is always human
	    Player humanPlayer = new Player(playerNames[0]);
	    humanPlayer.setPiece(playerPieces[0]);
	    players.add(humanPlayer);
	    System.out.println(humanPlayer.getPiece().toString());

	    // Handle computer players
	    for (int i = 1; i < playerNames.length; i++) {
	        String name = playerNames[i];
	        MonopolyPiece piece = playerPieces[i];

	        // For computer players, use the configurations
	        int configuration = playerConfigurations[i]; // Offset by 1 because the first player is human
	        if (configuration < 1 || configuration > 3) {
	            System.err.println("Invalid configuration for player " + name);
	        } else {
	            ComputerPlayer player = ComputerPlayerFactory.createComputerPlayer(name, piece, configuration);
	            players.add(player);
	            System.out.println(player.getPiece().toString());
	        }
	    }
	}


	/**
	 * This is the launch that javaFX utilizes. 
	 * @param args
	 */
	//public static void main(String[] args) {
//		System.setProperty("prism.allowhidpi", "false");
//		launch(args);
//	}
	/**
	 * Starts the game and initializes the grid, board and anchor plane / utils 
	 * @params primaryStage - the stage that the board object uses for transitions. 
	 */
	@Override
	public void start(Stage primaryStage) {
		// Create and initialize the database connection
		Connection dbConnection = initializeDatabaseConnection();

		// Create an instance of GameDatabaseOperations and pass the connection
		dbOps = new GameDatabaseOperations(dbConnection);

		this.primaryStage = primaryStage;
		board = new Board(primaryStage);
		Image boardImage = new Image(getClass().getClassLoader().getResource("background1.jpg").toExternalForm());
		ImageView boardView = new ImageView(boardImage);

		boardView.setFitHeight(832.0);
		boardView.setFitWidth(1099.0);
		boardView.setLayoutX(1.0);
		boardView.setLayoutY(70.0);
		boardView.setPickOnBounds(true);
		boardView.setPreserveRatio(true);

		root = new AnchorPane();
		root.setId("anchorPaneId");
		root.setMaxSize(1100.0, 900.0);
		root.setMinSize(1100.0, 980.0);
		root.setPrefSize(1100.0, 900.0);
		root.getChildren().add(boardView);

		double cornerWidth = 273.0;
		double cornerHeight = 250.0;
		double spotWidth = 170.0;
		double spotHeight = 205.0;

		GridPane grid = new GridPane();
		grid.setGridLinesVisible(false);

		grid.setLayoutX(1.0);
		grid.setLayoutY(70.0);
		grid.setPrefWidth(1099.0);
		grid.setPrefHeight(832.0);

		for (int i = 0; i < 11; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			RowConstraints rowConst = new RowConstraints();
			if (i == 0 || i == 10) {
				colConst.setPrefWidth(cornerWidth);
				rowConst.setPrefHeight(cornerHeight);
			} else {
				colConst.setPrefWidth(spotWidth);
				rowConst.setPrefHeight(spotHeight);
			}
			grid.getColumnConstraints().add(colConst);
			grid.getRowConstraints().add(rowConst);
		}

		root.getChildren().add(grid);
		gameGrid = grid;
		Button saveButton = new Button("Save");
		saveButton.setPrefWidth(100); 
		saveButton.setPrefHeight(40);
		saveButton.setOnAction(event -> onSaveButtonPressed());
		saveButton.getStyleClass().add("custom-button");


		Button rollDiceButton = new Button("ROLL DICE");
		rollDiceButton.setPrefWidth(250);
		rollDiceButton.setPrefHeight(40);
		rollDiceButton.setOnAction(event -> onDiceRollButtonPressed(root));
		rollDiceButton.getStyleClass().add("custom-button");
		HBox buttonBox = new HBox(10);
		buttonBox.getChildren().addAll(rollDiceButton, saveButton);
		buttonBox.setLayoutX(10.0);
		buttonBox.setLayoutY(10.0);
		root.getChildren().add(buttonBox);
		
		String comboBoxStyle = "-fx-background-color: #ffffff; -fx-border-color: #cccccc;";
		String buttonStyle = "-fx-background-color: #007bff; -fx-text-fill: white;";
		ComboBox<String> propertyDropdown = new ComboBox<>();
		propertyDropdown.setStyle(comboBoxStyle);

		Button MortgageButton = new Button("MORTGAGE PROPERTIES");
		MortgageButton.setPrefWidth(300);
		MortgageButton.setPrefHeight(40);
		MortgageButton.setOnAction(event -> {
		    String selectedProperty = propertyDropdown.getValue();
		    if (selectedProperty != null) {
		        mortgageOrUnmortgageProperty(root, selectedProperty);
		    }
		    refreshPropertyDropdown(propertyDropdown);
		});
		MortgageButton.setStyle(buttonStyle);


		MortgageButton.getStyleClass().add("custom-button");

		HBox mortgageBox = new HBox(10);
		mortgageBox.getChildren().addAll(propertyDropdown, MortgageButton);
		mortgageBox.setLayoutX(600.0);
		mortgageBox.setLayoutY(10.0);
		root.getChildren().add(mortgageBox);

		setupPlayerInfoPanel();
		initializePlayersOnBoard();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Monopoly Game");
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	
	
	
	private Connection initializeDatabaseConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/MonopolyGame", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error while connecting to the database: " + e.getMessage());
		}
		return connection;
	}
	private void onSaveButtonPressed() {
		// Get the current player who will be saved to the database
		Player currentPlayer = getCurrentPlayer();
		// Insert the new player into the database
		try {
			dbOps.insertPlayer(currentPlayer.getName(), currentPlayer.getMoney(), currentPlayer.getPosition());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			dbOps.updatePlayerMoney(currentPlayer.getName(), currentPlayer.getMoney());
			System.out.println("Player's money updated successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}                   
		try {
			dbOps.saveGame(currentPlayer.getName(), currentPlayer.getMoney(), currentPlayer.getPosition(), board);
			System.out.println("Game saved successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		primaryStage.setFullScreen(true);

		primaryStage.show();
	}
	
	public void refreshPlayerInfoPanel() {
	    populatePlayerInfo(players, playerInfoContainer);
	}



	/**
	 * Rolls dice for the current player and moves their piece 
	 * according to the roll result.
	 * @param anchorPane - the pane where dice images are displayed.
	 */
	public int[] rollDice(AnchorPane anchorPane) {
		Player currentPlayer = getCurrentPlayer();

		anchorPane.getChildren().removeIf(node -> node instanceof ImageView && node.getStyleClass().contains("diceImage"));

		int[] diceResults = currentPlayer.rollDice(); 
		Pair<int[], ImageView[]> diceResultPair = createDiceImageViews(diceResults[0] + diceResults[1]);
		ImageView[] diceImageViews = diceResultPair.getValue();
 
		double totalDiceWidth = 2 * DICE_SIZE + 10; 
		double startX = (anchorPane.getWidth() - totalDiceWidth) / 2; 

		for (ImageView imageView : diceImageViews) {
			imageView.setFitWidth(DICE_SIZE); 
			imageView.setFitHeight(DICE_SIZE); 
			imageView.getStyleClass().add("diceImage");
			AnchorPane.setTopAnchor(imageView, (anchorPane.getHeight() - DICE_SIZE) / 2); 
			AnchorPane.setLeftAnchor(imageView, startX);
			startX += DICE_SIZE + 10; 
		}
		anchorPane.getChildren().addAll(diceImageViews);

		return diceResults; 
	}



	public void onDiceRollButtonPressed(AnchorPane anchorPane) {
		Player currentPlayer = getCurrentPlayer();

		if (currentPlayer instanceof ComputerPlayer && currentPlayer.getMoney() < 500) {
			ComputerPlayer.getStrategy().mortgageProperty((ComputerPlayer) currentPlayer); 
			
		}

		int[] diceResults = rollDice(anchorPane);
		int dice1 = diceResults[0];
		int dice2 = diceResults[1];

		currentPlayer.move(board, dice1, dice2);

		if (currentPlayer.isInJail() && currentPlayer.getJailTurns() > 0) {
			positionPlayerOnBoard(currentPlayer, currentPlayer.getPosition());

			nextPlayer();
			return;
		}     

		removeTokenFromBoard(currentPlayer);
		positionPlayerOnBoard(currentPlayer, currentPlayer.getPosition());

		Spot landedSpot = board.getSpot(currentPlayer.getPosition());

		if(landedSpot == null) {
			System.out.println("Error: Spot not found at position " + currentPlayer.getPosition());
			return;
		}

		if (landedSpot instanceof Property) {
			
			showSpotInfo(currentPlayer, currentPlayer.getPosition()); 
		}
		positionPlayerOnBoard(currentPlayer, currentPlayer.getPosition());

	


	if (landedSpot instanceof ActionSpot) {
		showSpotInfo(currentPlayer, currentPlayer.getPosition()); 
		positionPlayerOnBoard(currentPlayer, currentPlayer.getPosition());

	}

	if (dice1 == dice2) {
		if (currentPlayer.getConsecutiveDoubles() == 3) {
			showAlert(currentPlayer.getName() + " rolled doubles three times in a row and is now in jail!");
			positionPlayerOnBoard(currentPlayer, currentPlayer.getPosition());
		} else {
			return; 
		}
		
        removeLostPlayers();
        checkForWinner();
	}


	nextPlayer();
}
	
	private void removeLostPlayers() {
	    players.removeIf(Player::hasLost);
	}

	private void declareWinner(Player winner) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Game Over");
	    alert.setHeaderText("Player " + winner.getName() + " is the winner!");
	    alert.setContentText("Congratulations! You are the last player standing.");
	    alert.showAndWait();
	}

	private void checkForWinner() {
	    int activePlayers = 0;
	    Player winner = null;

	    for (Player player : players) {
	        if (!player.hasLost()) {
	            System.out.println(activePlayers);
	            activePlayers++;
	            winner = player; 
	        }
	    }

	    if (activePlayers == 1) {
	        declareWinner(winner);
	    }
	}


/**
 * Retrieves the current player based on the player index.
 * @return Player the player whose turn it currently is.
 */
private Player getCurrentPlayer() {
	return players.get(currentPlayerIndex);
}

private void nextPlayer() {
	currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	refreshPlayerInfoPanel();
}

/**
 * Creates image views for dice based on the roll result.
 * @param result - the sum of values from two dice.
 * @return ImageView[] an array containing images of the dice.
 */
private Pair<int[], ImageView[]> createDiceImageViews(int result) {
	Random rand = new Random();
	int dice1, dice2;

	if (result < 2 || result > 12) {
		throw new IllegalArgumentException("Invalid dice result: " + result);
	}

	do {
		dice1 = rand.nextInt(6) + 1;
		dice2 = result - dice1;     
	} while (dice2 < 1 || dice2 > 6);

	ImageView[] diceImageViews = new ImageView[2];
	diceImageViews[0] = createDiceImageView(dice1 - 1);
	diceImageViews[1] = createDiceImageView(dice2 - 1);

	return new Pair<>(new int[]{ dice1, dice2 }, diceImageViews);
}


/**
 * Creates an ImageView of a dice based on its value.
 * @param result the value of the dice 
 * @return ImageView the image view of the dice.
 */
private ImageView createDiceImageView(int result) {
	Image diceImage = new Image(getClass().getResourceAsStream(DICE_IMAGES[result]));
	return new ImageView(diceImage);
}
/**
 * Sets up the information panel for players, including their name, piece, and money.
 */
private void setupPlayerInfoPanel() {
	playerInfoContainer = new VBox();
	playerInfoContainer.setSpacing(16); 
	AnchorPane.setRightAnchor(playerInfoContainer, 90.0);  
	root.getChildren().add(playerInfoContainer);
	populatePlayerInfo(players, playerInfoContainer);

}

/**
 * Places the players on the starting position of the board.
 */
private void initializePlayersOnBoard() {
	for (int i = 0; i < players.size(); i++) {
		Player player = players.get(i);
		ImageView playerToken = new ImageView(new Image(player.getPiece().getIconURL()));
		playerToken.setFitWidth(30);
		playerToken.setFitHeight(30);
		playerToken.setUserData(player); 

		Point2D startCoords = getGridStartingCoordinates(i);
		GridPane.setColumnIndex(playerToken, (int) startCoords.getX());
		GridPane.setRowIndex(playerToken, (int) startCoords.getY());
		gameGrid.getChildren().add(playerToken);
	}
}

/**
 * Computes the starting coordinates for a player based on their index.
 * @param playerIndex - the index of the player.
 * @return Point2D the starting coordinates for the player.
 */
private Point2D getGridStartingCoordinates(int playerIndex) {
	int column = 10; 
	int row = 10;    
	double startX = column * spotWidth + (playerIndex % 2) * 35;
	double startY = row * spotHeight + (playerIndex / 2) * 35;

	return new Point2D(startX, startY);
}




/**
 * Populates a container with player information, including their name, associated MonopolyPiece,
 * money amount, and visual styling.
 *
 * @param players   The list of Player objects containing player information to be displayed.
 * @param container The VBox container where player information panels will be added.
 */
public void populatePlayerInfo(List<Player> players, VBox container) {
    container.getChildren().clear();

    int idx = 0;
    for (Player player : players) {
        MonopolyPiece piece = player.getPiece();
        Label playerName;
        if (piece != null) {
            playerName = new Label(player.getName() + " (" + piece.name() + ")");
            playerName.setStyle("-fx-font-weight: bold; -fx-font-size: 24px; -fx-text-fill: #333;");
        } else {
            System.err.println("Player " + player.getName() + " does not have a valid MonopolyPiece.");
            playerName = new Label(player.getName() + " (No Piece)");
            playerName.setStyle("-fx-font-weight: bold; -fx-font-size: 24px; -fx-text-fill: #FF0000;");
        }

        Label playerAmount = new Label("Amount: $" + player.getMoney());
        playerAmount.setId(player.getName() + "_money");
        playerAmount.setStyle("-fx-font-size: 20px; -fx-text-fill: #555;");

        VBox playerPanel = new VBox(10, playerName, playerAmount);
        playerPanel.setPadding(new Insets(10, 10, 10, 10));

        // Different background colors for alternating players
        if (idx % 2 == 0) {
            playerPanel.setStyle("-fx-background-color: #f3f3f3; -fx-border-radius: 5; -fx-background-radius: 5;");
        } else {
            playerPanel.setStyle("-fx-background-color: #e9e9e9; -fx-border-radius: 5; -fx-background-radius: 5;");
        }

        // Highlight the current player's panel
        if (idx == currentPlayerIndex) {
            playerPanel.setStyle("-fx-background-color: #FFEB3B; -fx-border-radius: 5; -fx-background-radius: 5;"); // Example: Set it to a yellow color
        }

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0f);
        dropShadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
        playerPanel.setEffect(dropShadow);

        container.getChildren().add(playerPanel);
        idx++;
    }

    container.setPadding(new Insets(10, 10, 10, 90));
    container.setSpacing(10);
}



/**
 * Updates the money display for a given player.
 * @param player - the player whose money display needs to be updated.
 */
public void updatePlayerMoney(Player player) {
    Label playerMoneyLabel = (Label) playerInfoContainer.lookup("#" + player.getName() + "_money");
    if (playerMoneyLabel != null) {
        playerMoneyLabel.setText("Amount: $" + player.getMoney());
        if (player.getMoney() <= 0) {
            player.hasLost();
        }
    }
}




/**
 * Moves a player's piece to a specified spot on the board.
 * @param player the player to move.
 * @param spot the destination spot.
 */
public void positionPlayerOnBoard(Player player, int spot) {
	// Remove the old token (if it exists)
	removeTokenFromBoard(player);

	// Look for existing token on the board
	ImageView playerToken = null;
	for (Node node : gameGrid.getChildren()) {
		if (node instanceof ImageView && node.getUserData() == player) {
			playerToken = (ImageView) node;
			break;
		}
	}

	// If token doesn't exist, create a new one
	if (playerToken == null) {
		playerToken = new ImageView(new Image(player.getPiece().getIconURL()));
		playerToken.setFitWidth(30);
		playerToken.setFitHeight(30);
		playerToken.setUserData(player);
		gameGrid.getChildren().add(playerToken);
	}

	Point2D spotCoordinates = getGridCoordinates(spot);

	double adjustedX = (spotWidth - playerToken.getFitWidth()) / 2;
	double adjustedY = (spotHeight - playerToken.getFitHeight()) / 2;
	playerToken.setTranslateX(adjustedX);
	playerToken.setTranslateY(adjustedY);

	GridPane.setColumnIndex(playerToken, (int) spotCoordinates.getX());
	GridPane.setRowIndex(playerToken, (int) spotCoordinates.getY());
}

/**
 * Removes a player's piece from the board.
 * @param player - the player whose piece needs to be removed.
 */
private void removeTokenFromBoard(Player player) {
	gameGrid.getChildren().removeIf(node -> node instanceof ImageView && node.getUserData() == player);
}
/**
 * Displays information about a spot when a player lands on it.
 * @param currentPlayer - the player who landed on the spot.
 * @param spotPosition - the position of the spot.
 */
private void showSpotInfo(Player currentPlayer, int spotPosition) {
	Spot spot = board.getSpot(spotPosition);

	spot.onABoardSpot(currentPlayer); 
	updatePlayerMoney(currentPlayer);
}

/**
 * Computes the grid coordinates for a spot based on its position.
 * @param spot - the position of the spot on the board.
 * @return Point2D the grid coordinates.
 */
public Point2D getGridCoordinates(int spot) {
	int column = 0;
	int row = 0;

	if (spot >= 0 && spot <= 10) { 
		column = 10 - spot;  
		row = 10;  
	} else if (spot > 10 && spot <= 20) {  
		column = 0;  
		row = 10 - (spot - 10);  
	} else if (spot > 20 && spot <= 30) { 
		column = spot - 20;  
		row = 0;  
	} else if (spot > 30 && spot < 40) {  
		column = 10;  
		row = spot - 30;  
	}

	return new Point2D(column, row);
}

/**
 * Represents a method to mortgage or unmortgage a property for a player in the Monopoly game.
 * @param player - represents the main player of the game. 
 */
private void mortgageOrUnmortgageProperty(Pane root, String propertyName) {
    Player currentPlayer = getCurrentPlayer();
    ArrayList<Property> properties = currentPlayer.getProperties();

    for (Property property : properties) {
        if (property.getName().equals(propertyName)) {
            if (property.isMortgaged()) {
                // Code to unmortgage the property
                property.unmortgage();
            } else {
                // Code to mortgage the property
                property.mortgage();
            }
            break;  // exit the loop once the property is found
        }
    }
}

/**
 * Displays an informational alert dialog with the specified message.
 *
 * @param message The message to be displayed in the alert.
 */
private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.initOwner(primaryStage);
    alert.setTitle("Monopoly Game Info");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}



/**
 * Refreshes the items in a ComboBox with property names that are owned by the current player
 * and are not mortgaged.
 *
 * @param propertyDropdown The ComboBox to be refreshed with property names.
 */
private void refreshPropertyDropdown(ComboBox<String> propertyDropdown) {
    Player currentPlayer = getCurrentPlayer();
    List<Property> properties = currentPlayer.getProperties();
    List<String> propertyNames = new ArrayList<>();
    for (Property property : properties) {
        if (!property.isMortgaged()) {
            propertyNames.add(property.getName());
        }
    }
    propertyDropdown.getItems().clear();
    propertyDropdown.getItems().addAll(propertyNames);
}


}
