import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

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


/**
 * This is the launch that javaFX utilizes. 
 * @param args
 */
    public static void main(String[] args) {
        System.setProperty("prism.allowhidpi", "false");
        launch(args);
    }
/**
 * Starts the game and initializes the grid, board and anchor plane / utils 
 * @params primaryStage - the stage that the board object uses for transitions. 
 */
    @Override
    public void start(Stage primaryStage) {
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




        Button rollDiceButton = new Button("Roll Dice");
        rollDiceButton.setOnAction(event -> rollDice(root));

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(rollDiceButton);
        buttonBox.setLayoutX(10.0);
        buttonBox.setLayoutY(10.0);

        root.getChildren().add(buttonBox);
        setupPlayerInfoPanel();
        initializePlayersOnBoard();


        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Monopoly Game");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    /**
     * Rolls dice for the current player and moves their piece 
     * according to the roll result.
     * @param anchorPane - the pane where dice images are displayed.
     */
    public void rollDice(AnchorPane anchorPane) {
        Player currentPlayer = getCurrentPlayer(); 

        anchorPane.getChildren().removeIf(node -> node instanceof ImageView && node.getStyleClass().contains("diceImage"));

        int result = Player.rollDice();
        ImageView[] diceImageViews = createDiceImageViews(result);

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

        int currentPosition = currentPlayer.getPosition();
        int newPosition = (currentPosition + result) % Board.getSize();
        currentPlayer.setPosition(newPosition);
        removeTokenFromBoard(currentPlayer);
        positionPlayerOnBoard(currentPlayer, newPosition);

        Spot landedSpot = board.getSpot(newPosition);
        if(landedSpot == null) {
            System.out.println("Error: Spot not found at position " + newPosition);
            System.out.println("New position is: " + newPosition + ", landedSpot is: " + (landedSpot == null ? "null" : landedSpot.getName()));

            return;
        }

        if (landedSpot instanceof Property) {
            showSpotInfo(currentPlayer, newPosition); 
        }
    }
    /**
     * Retrieves the current player based on the player index.
     * @return Player the player whose turn it currently is.
     */
    private Player getCurrentPlayer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return currentPlayer;
    }

    /**
     * Creates image views for dice based on the roll result.
     * @param result - the sum of values from two dice.
     * @return ImageView[] an array containing images of the dice.
     */
    private ImageView[] createDiceImageViews(int result) {
        Random rand = new Random();

        if (result < 2 || result > 12) {
            throw new IllegalArgumentException("Invalid dice result: " + result);
        }

        int dice1, dice2;
        do {
            dice1 = rand.nextInt(6) + 1; 
            dice2 = result - dice1;     
        } while (dice2 < 1 || dice2 > 6);

        ImageView[] diceImageViews = new ImageView[2];
        diceImageViews[0] = createDiceImageView(dice1 - 1);
        diceImageViews[1] = createDiceImageView(dice2 - 1);

        return diceImageViews;
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
        
        createSamplePlayers();
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
     * Creates sample players and adds them to the game  TO BE REPLACED BY A FACTORY
     */
    private void createSamplePlayers() { 
        Player player1 = new Player("Quinn");
        player1.setPiece(MonopolyPiece.HAT);
        player1.setMoney(1500);

        Player player2 = new Player("Isaac");
        player2.setPiece(MonopolyPiece.CAT);
        player2.setMoney(1500);

        Player player3 = new Player("Nick");
        player3.setPiece(MonopolyPiece.DOG);
        player3.setMoney(1500);
        
        Player player4 = new Player("Yunus");
        player4.setPiece(MonopolyPiece.HORSE);
        player4.setMoney(1500);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
    }

    /**
     * Populates the specified VBox container with information about each player 
     * in the provided list of players. Each player's information consists of their name 
     * and the current amount of money they have.
     * 
     * @param players  -  List of players whose information is to be displayed.
     * @param container - The VBox container where the player information will be added.
     */
    public void populatePlayerInfo(List<Player> players, VBox container) {
        container.getChildren().clear();  

        for(Player player : players) {
        	Label playerName = new Label(player.getName() + " (" + player.getPiece().name() + ")");
            playerName.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");

            Label playerAmount = new Label("Amount: $" + player.getMoney());
            playerAmount.setId(player.getName() + "_money");
            playerAmount.setStyle("-fx-font-size: 20px;");

            VBox playerPanel = new VBox(5, playerName, playerAmount);
            playerPanel.setSpacing(5);
            playerPanel.setPadding(new Insets(5, 0, 5, -50)); 
            
            container.getChildren().add(playerPanel); 
        }
    }

    /**
     * Updates the money display for a given player.
     * @param player - the player whose money display needs to be updated.
     */
    public void updatePlayerMoney(Player player) {
        Label playerMoneyLabel = (Label) playerInfoContainer.lookup("#" + player.getName() + "_money");
        if (playerMoneyLabel != null) {
            playerMoneyLabel.setText("Amount: $" + player.getMoney());
        
        }
    }
    /**
     * Moves a player's piece to a specified spot on the board.
     * @param player the player to move.
     * @param spot the destination spot.
     */ 
    public void positionPlayerOnBoard(Player player, int spot) {
        ImageView playerToken = new ImageView(new Image(player.getPiece().getIconURL()));
        playerToken.setFitWidth(30);
        playerToken.setFitHeight(30);
        playerToken.setUserData(player); 

        Point2D spotCoordinates = getGridCoordinates(spot);

        double adjustedX = (spotWidth - playerToken.getFitWidth()) / 2;
        double adjustedY = (spotHeight - playerToken.getFitHeight()) / 2;
        playerToken.setTranslateX(adjustedX);
        playerToken.setTranslateY(adjustedY);

        GridPane.setColumnIndex(playerToken, (int) spotCoordinates.getX());
        GridPane.setRowIndex(playerToken, (int) spotCoordinates.getY());

        gameGrid.getChildren().add(playerToken);
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
        Spot landedSpot = board.getSpot(spotPosition);
        landedSpot.onABoardSpot(currentPlayer); 
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
    
}

