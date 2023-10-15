import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;


public class Board extends GridPane { // Extends the GridPane to allow easy grid placement
    private static final int SIZE = 40;
    private Spot[] spots = new Spot[SIZE];

    public Board() {
        initializeSpots();
        displayBoard();
    }
    /**
     * Initializes the spots on the Monopoly board.
     */
    private void initializeSpots() {
        spots[0] = new ActionSpot("Go", ActionSpot.ActionType.GO);
        spots[1] = new Property("Mediterranean Avenue", 60, 2, 30);
        spots[2] = new ActionSpot("Community Chest", ActionSpot.ActionType.COMMUNITY_CHEST);
        spots[3] = new Property("Baltic Avenue", 60, 4, 30);
        spots[4] = new ActionSpot("Income Tax", ActionSpot.ActionType.INCOME_TAX);
        spots[5] = new Property("Reading Railroad", 200, 25, 100);
        spots[6] = new Property("Oriental Avenue", 100, 6, 50);
        spots[7] = new ActionSpot("Chance", ActionSpot.ActionType.CHANCE);
        spots[8] = new Property("Vermont Avenue", 100, 6, 50);
        spots[9] = new Property("Connecticut Avenue", 120, 8, 60);
        spots[10] = new ActionSpot("Jail / Just Visiting", ActionSpot.ActionType.JAIL);
        spots[11] = new Property("St. Charles Place", 140, 10, 70);
        spots[12] = new Property("Electric Company", 150, 0, 75); // rent is 4x dice roll - implement later
        spots[13] = new Property("States Avenue", 140, 10, 70);
        spots[14] = new Property("Virginia Avenue", 160, 12, 80);
        spots[15] = new Property("Pennsylvania Railroad", 200, 25, 100);
        spots[16] = new Property("St. James Place", 180, 14, 90);
        spots[17] = new ActionSpot("Community Chest", ActionSpot.ActionType.COMMUNITY_CHEST);
        spots[18] = new Property("Tennessee Avenue", 180, 14, 90);
        spots[19] = new Property("New York Avenue", 200, 16, 100);
        spots[20] = new ActionSpot("Free Parking", ActionSpot.ActionType.FREE_PARKING);
        spots[21] = new Property("Kentucky Avenue", 220, 18, 110);
        spots[22] = new ActionSpot("Chance", ActionSpot.ActionType.CHANCE);
        spots[23] = new Property("Indiana Avenue", 220, 18, 110);
        spots[24] = new Property("Illinois Avenue", 240, 20, 120);
        spots[25] = new Property("B. & O. Railroad", 200, 25, 100);
        spots[26] = new Property("Atlantic Avenue", 260, 22, 130);
        spots[27] = new Property("Ventnor Avenue", 260, 22, 130);
        spots[28] = new Property("Water Works", 150, 0, 75); // rent is 4x dice roll - implement later
        spots[29] = new Property("Marvin Gardens", 280, 24, 140);
        spots[30] = new ActionSpot("Go To Jail", ActionSpot.ActionType.GO_TO_JAIL);
        spots[31] = new Property("Pacific Avenue", 300, 26, 150);
        spots[32] = new Property("North Carolina Avenue", 300, 26, 150);
        spots[33] = new ActionSpot("Community Chest", ActionSpot.ActionType.COMMUNITY_CHEST);
        spots[34] = new Property("Pennsylvania Avenue", 320, 28, 160);
        spots[35] = new Property("Short Line", 200, 25, 100);
        spots[36] = new ActionSpot("Chance", ActionSpot.ActionType.CHANCE);
        spots[37] = new Property("Park Place", 350, 35, 175);
        spots[38] = new ActionSpot("Luxury Tax", ActionSpot.ActionType.LUXURY_TAX);
        spots[39] = new Property("Boardwalk", 400, 50, 200);
    }


    /**
     * Display the board using JavaFX components.
     */
    private void displayBoard() {
        int sideSize = (int) Math.sqrt(SIZE); // Assuming a square board for simplicity

        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                int spotIndex = (i * sideSize) + j;
                if (spotIndex < SIZE) {
                    Spot spot = spots[spotIndex];
                    StackPane cell = createCell(spot.getName()); // Create a cell with the spot's name
                    this.add(cell, j, i); // Add the cell to the grid
                }
            }
        }
    }

    /**
     * Create a visual cell for a spot on the board.
     * 
     * @param name The name of the spot.
     * @return A visual representation of the spot.
     */
    private StackPane createCell(String name) {
        Rectangle rect = new Rectangle(50, 50); // Adjust size as needed
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.WHITE);
        Text text = new Text(name);
        return new StackPane(rect, text); // Overlay the text on top of the rectangle
    }

    public Spot getSpot(int position) {
        return spots[position % SIZE];
    }

    public static int getSize() {
        return SIZE;
    }
}
