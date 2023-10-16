/**
 * Represents specialized spots on the Monopoly board that perform specific actions.
 * Examples include "Go", "Income Tax", "Chance", and "Community Chest".
 */
public class ActionSpot implements Spot {

    private final String name;
    private final ActionType actionType;

    /**
     * Initializes a new instance of ActionSpot with the given name and action type.
     *
     * @param name       The display name of this spot.
     * @param actionType The kind of action associated with this spot.
     */
    public ActionSpot(String name, ActionType actionType) {
        this.name = name;
        this.actionType = actionType;
    }

    @Override
    public String getName() {
        return name;
    }
    public ActionType getActionType() {
        return actionType;
    }

    /**
     * Invoked when a player lands on this spot.
     *
     * @param player The player who landed on this spot.
     */
    @Override
    public void onABoardSpot(Player player) {
        System.out.println(player.getName() + " landed on " + getName());
        
        switch (actionType) {
            case GO:
                System.out.println("Collect $200 salary as you pass.");
                break;
            case CHANCE:
                System.out.println("Draw a Chance card.");
                break;
            case COMMUNITY_CHEST:
                System.out.println("Draw a Community Chest card.");
                break;
            case LUXURY_TAX:
                System.out.println("Pay luxury tax of $75.");
                break;
            case INCOME_TAX:
                System.out.println("Pay income tax of $200 or 10% of total assets.");
                break;
            case JAIL:
                System.out.println("Just visiting.");
                break;
            case FREE_PARKING:
                System.out.println("Relax a bit! Nothing happens.");
                break;
            case GO_TO_JAIL:
                sendToJail(player);
                break;
            default:
                System.out.println("Action not defined for this spot.");
                break;
        }
    }

    /**
     * Sends the given player to jail.
     *
     * @param player The player to send to jail.
     */
    private void sendToJail(Player player) {
        player.setInJail(true);
        player.setJailTurns(3); 
        player.setPosition(10); 
        System.out.println(player.getName() + " has been sent to jail.");
    }

    /**
     * Defines the various types of action spots available on the board.
     */
    public enum ActionType {
        GO, CHANCE, COMMUNITY_CHEST, INCOME_TAX, JAIL, 
        FREE_PARKING, GO_TO_JAIL, LUXURY_TAX, PROPERTY, 
        RAILROAD, UTILITY 
    }
}
