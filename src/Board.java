
/**
 * Represents the Monopoly game board.
 */
public class Board {
    private static final int SIZE = 40;
    private Spot[] spots = new Spot[SIZE];

    /**
     * Constructs and initializes a new Monopoly board.
     */
    public Board() {
        initializeSpots();
    }

    /**
     * Initializes the spots on the Monopoly board.
     */
    private void initializeSpots() {
        spots[0] = new ActionSpot("Go", ActionSpot.ActionType.GO);
        spots[1] = new Property("Mediterranean Avenue", 60, 2);
        spots[2] = new ActionSpot("Community Chest", ActionSpot.ActionType.COMMUNITY_CHEST);
        spots[3] = new Property("Baltic Avenue", 60, 4);
        spots[4] = new ActionSpot("Income Tax", ActionSpot.ActionType.INCOME_TAX);
        spots[5] = new Property("Reading Railroad", 200, 25);
        spots[6] = new Property("Oriental Avenue", 100, 6);
        spots[7] = new ActionSpot("Chance", ActionSpot.ActionType.CHANCE);
        spots[8] = new Property("Vermont Avenue", 100, 6);
        spots[9] = new Property("Connecticut Avenue", 120, 8);
        spots[10] = new ActionSpot("Jail / Just Visiting", ActionSpot.ActionType.JAIL);
        spots[11] = new Property("St. Charles Place", 140, 10);
        spots[12] = new Property("Electric Company", 150, 0); // rent is 4x dice roll - implement later
        spots[13] = new Property("States Avenue", 140, 10);
        spots[14] = new Property("Virginia Avenue", 160, 12);
        spots[15] = new Property("Pennsylvania Railroad", 200, 25);
        spots[16] = new Property("St. James Place", 180, 14);
        spots[17] = new ActionSpot("Community Chest", ActionSpot.ActionType.COMMUNITY_CHEST);
        spots[18] = new Property("Tennessee Avenue", 180, 14);
        spots[19] = new Property("New York Avenue", 200, 16);
        spots[20] = new ActionSpot("Free Parking", ActionSpot.ActionType.FREE_PARKING);
        spots[21] = new Property("Kentucky Avenue", 220, 18);
        spots[22] = new ActionSpot("Chance", ActionSpot.ActionType.CHANCE);
        spots[23] = new Property("Indiana Avenue", 220, 18);
        spots[24] = new Property("Illinois Avenue", 240, 20);
        spots[25] = new Property("B. & O. Railroad", 200, 25);
        spots[26] = new Property("Atlantic Avenue", 260, 22);
        spots[27] = new Property("Ventnor Avenue", 260, 22);
        spots[28] = new Property("Water Works", 150, 0); // rent is 4x dice roll - implement later
        spots[29] = new Property("Marvin Gardens", 280, 24);
        spots[30] = new ActionSpot("Go To Jail", ActionSpot.ActionType.GO_TO_JAIL);
        spots[31] = new Property("Pacific Avenue", 300, 26);
        spots[32] = new Property("North Carolina Avenue", 300, 26);
        spots[33] = new ActionSpot("Community Chest", ActionSpot.ActionType.COMMUNITY_CHEST);
        spots[34] = new Property("Pennsylvania Avenue", 320, 28);
        spots[35] = new Property("Short Line", 200, 25);
        spots[36] = new ActionSpot("Chance", ActionSpot.ActionType.CHANCE);
        spots[37] = new Property("Park Place", 350, 35);
        spots[38] = new ActionSpot("Luxury Tax", ActionSpot.ActionType.LUXURY_TAX);
        spots[39] = new Property("Boardwalk", 400, 50);
    }

    /**
     * Gets the spot at the given position on the board.
     * 
     * @param position The position on the board (0 to 39).
     * @return The spot at the given position.
     */
    public Spot getSpot(int position) {
        return spots[position % SIZE];
    }
    
    public static int getSize() {
		return SIZE;
	} 

}