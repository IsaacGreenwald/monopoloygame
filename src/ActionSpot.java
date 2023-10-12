
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
        switch (actionType) {
            case GO:
                // Give player $200 if they pass GO (not on game start)
                break;
            case CHANCE:
                // Draw a chance card
                break;
            case COMMUNITY_CHEST:
                // Draw a community chest card
                break;
            // ... handle other actions
            default:
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