import javafx.stage.Stage;
/**
 * This class is in charge of initializing and handling each spot in the grid. 
 */
public class Board {
    private static final int SIZE = 40;
    private static Spot[] spots = new Spot[SIZE];
    private Stage primaryStage;

    public Board(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeSpots();
    }

    /**
     * Initializes the spots on the Monopoly board.
     */
    private void initializeSpots() {
    	spots[0] = new ActionSpot("Go", ActionSpot.ActionType.GO);
    	spots[1] = new Property("Mediterranean Avenue", 60, 2, 30, primaryStage);
    	spots[2] = new ActionSpot("Community Chest", ActionSpot.ActionType.COMMUNITY_CHEST);
    	spots[3] = new Property("Baltic Avenue", 60, 4, 30, primaryStage);
    	spots[4] = new ActionSpot("Income Tax", ActionSpot.ActionType.INCOME_TAX);
    	spots[5] = new Property("Reading Railroad", 200, 25, 100, primaryStage);
    	spots[6] = new Property("Oriental Avenue", 100, 6, 50, primaryStage);
    	spots[7] = new ActionSpot("Chance", ActionSpot.ActionType.CHANCE);
    	spots[8] = new Property("Vermont Avenue", 100, 6, 50, primaryStage);
    	spots[9] = new Property("Connecticut Avenue", 120, 8, 60, primaryStage);
    	spots[10] = new ActionSpot("Jail / Just Visiting", ActionSpot.ActionType.JAIL);
    	spots[11] = new Property("St. Charles Place", 140, 10, 70, primaryStage);
    	spots[12] = new Property("Electric Company", 150, 0, 75, primaryStage); // rent is 4x dice roll - implement later
    	spots[13] = new Property("States Avenue", 140, 10, 70, primaryStage);
    	spots[14] = new Property("Virginia Avenue", 160, 12, 80, primaryStage);
    	spots[15] = new Property("Pennsylvania Railroad", 200, 25, 100, primaryStage);
    	spots[16] = new Property("St. James Place", 180, 14, 90, primaryStage);
    	spots[17] = new ActionSpot("Community Chest", ActionSpot.ActionType.COMMUNITY_CHEST);
    	spots[18] = new Property("Tennessee Avenue", 180, 14, 90, primaryStage);
    	spots[19] = new Property("New York Avenue", 200, 16, 100, primaryStage);
    	spots[20] = new ActionSpot("Free Parking", ActionSpot.ActionType.FREE_PARKING);
    	spots[21] = new Property("Kentucky Avenue", 220, 18, 110, primaryStage);
    	spots[22] = new ActionSpot("Chance", ActionSpot.ActionType.CHANCE);
    	spots[23] = new Property("Indiana Avenue", 220, 18, 110, primaryStage);
    	spots[24] = new Property("Illinois Avenue", 240, 20, 120, primaryStage);
    	spots[25] = new Property("B. & O. Railroad", 200, 25, 100, primaryStage);
    	spots[26] = new Property("Atlantic Avenue", 260, 22, 130, primaryStage);
    	spots[27] = new Property("Ventnor Avenue", 260, 22, 130, primaryStage);
    	spots[28] = new Property("Water Works", 150, 0, 75, primaryStage); // rent is 4x dice roll - implement later
    	spots[29] = new Property("Marvin Gardens", 280, 24, 140, primaryStage);
    	spots[30] = new ActionSpot("Go To Jail", ActionSpot.ActionType.GO_TO_JAIL);
    	spots[31] = new Property("Pacific Avenue", 300, 26, 150, primaryStage);
    	spots[32] = new Property("North Carolina Avenue", 300, 26, 150, primaryStage);
    	spots[33] = new ActionSpot("Community Chest", ActionSpot.ActionType.COMMUNITY_CHEST);
    	spots[34] = new Property("Pennsylvania Avenue", 320, 28, 160, primaryStage);
    	spots[35] = new Property("Short Line", 200, 25, 100, primaryStage);
    	spots[36] = new ActionSpot("Chance", ActionSpot.ActionType.CHANCE);
    	spots[37] = new Property("Park Place", 350, 35, 175, primaryStage);
    	spots[38] = new ActionSpot("Luxury Tax", ActionSpot.ActionType.LUXURY_TAX);
    	spots[39] = new Property("Boardwalk", 400, 50, 200, primaryStage);

    }


    public Spot getSpot(int position) {
        return spots[position];
    }

    public static int getSize() {
        return SIZE;
    }
}
