
/**
 * Represents spots on the board that perform specific actions 
 * (e.g., Go, Income Tax, Chance, Community Chest).
 */
public class ActionSpot implements Spot {
    private String name;
    private ActionType actionType;

    /**
     * Constructs a new ActionSpot with the specified name and action type.
     * 
     * @param name       The name of the action spot.
     * @param actionType The type of action to be performed.
     */
    public ActionSpot(String name, ActionType actionType) {
        this.name = name;
        this.actionType = actionType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onABoardSpot(Player player) {
    	System.out.println(player.getName() + " landed on " + getName());
    	switch (actionType) {
        	case GO:
        		System.out.println("GO");
        		break;
	        case CHANCE:
	            System.out.println("CHANCE");
	            break;
	        case COMMUNITY_CHEST:
	            System.out.println("COMMUNITY_CHEST");
	            break;
	        case JAIL:
	            System.out.println("JAIL");
	            break;
	        case FREE_PARKING:
	            System.out.println("FREE_PARKING");
	            break;
	        case GO_TO_JAIL:
	            System.out.println("GO_TO_JAIL");
	            break;
	        default:
	            System.out.println("UNKNOWN ACTION");
	            break;
        }
    }

    /**
     * Enumerates the types of actions spots can have.
     */
    public enum ActionType {
        GO, CHANCE, COMMUNITY_CHEST, INCOME_TAX, JAIL, FREE_PARKING, GO_TO_JAIL, LUXURY_TAX
        //... add other types as needed
    }

}