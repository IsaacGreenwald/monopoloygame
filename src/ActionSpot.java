
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
	        case PROPERTY: // NOT USED AS OF NOW
	            System.out.println("PROPERTY");
	            
	        case CHANCE:
	            System.out.println("CHANCE");
	            break;
	        case COMMUNITY_CHEST:
	            System.out.println("COMMUNITY CHEST");
	            break;
	        case LUXURY_TAX:
	            System.out.println("LUXURY TAX");
	            break;
	        case INCOME_TAX:
	            System.out.println("INCOME TAX");
	            break;
	        case RAILROAD: // NOT USED AS OF NOW
	            System.out.println("RAILROAD");
	            break;
	        case UTILITY: // NOT USED AS OF NOW
	            System.out.println("UTILITY");
	            break;
	        case JAIL:
	            System.out.println("JAIL");
	            break;
	        case FREE_PARKING:
	            System.out.println("FREE PARKING");
	            break;
	        case GO_TO_JAIL:
	            System.out.println("GO TO JAIL");
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
        GO, CHANCE, COMMUNITY_CHEST, INCOME_TAX, JAIL, FREE_PARKING, GO_TO_JAIL, LUXURY_TAX, 
        PROPERTY, RAILROAD, UTILITY
        // add other types as needed
    }
    
    
    
    

}