import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a property that can be bid on by players.
 */
public class PropertyBidding extends Property {

    private int increaseBidAmount = 25;    
    private List<Player> bidders;    
    private int currentBid;

    /**
     * Creates a new PropertyBidding object.
     * @param name Name of the property.
     * @param price Price of the property.
     * @param startingRent Starting rent for the property.
     * @param mortgageValue Mortgage value of the property.
     * @param primaryStage Primary stage for JavaFX.
     */
    public PropertyBidding(String name, int price, int startingRent, int mortgageValue, Stage primaryStage) {
        super(name, price, startingRent, mortgageValue, primaryStage);
        this.bidders = new ArrayList<>();
        this.currentBid = price / 2; // Starting bid is half the property price
    }

    /**
     * Initiates the bidding process for this property.
     * @param players List of players participating in the game.
     */
    public void startBidding(List<Player> players) {
        // Ask each player if they want to bid
        for (Player player : players) {
            if (player.getMoney() >= currentBid && askForBidding(player)) {
                bidders.add(player);
            }
        }

        // Keep increasing bids until only one bidder remains
        while (bidders.size() > 1) {
            List<Player> activeBidders = new ArrayList<>(bidders);
            bidders.clear();

            for (Player bidder : activeBidders) {
                if (askForIncrease(bidder)) {
                    currentBid += increaseBidAmount;
                    if (bidder.getMoney() >= currentBid) {
                        bidders.add(bidder);
                    }
                }
            }
        }

        // Declare the winner of the bid
        if (!bidders.isEmpty()) {
            Player winner = bidders.get(0);
            System.out.println(winner.getName() + " has won the bid for " + currentBid);
            winner.setMoney(winner.getMoney() - currentBid);
            this.setOwner(winner);
        } else {
            System.out.println("No player won the bid for the property.");
        }
    }

    /**
     * Asks a player if they want to bid on this property.
     * @param player The player being asked.
     * @return True if player wants to bid, false otherwise.
     */
    private boolean askForBidding(Player player) {
        System.out.println("Does " + player.getName() + " want to bid on " + getName() + " for $" + currentBid + "?");
        ButtonType result = promptUser("Does " + player.getName() + " want to bid on " + getName() + " for $" + currentBid + "?",
                ButtonType.YES, ButtonType.NO);

        return result == ButtonType.YES;
    }
    
    /**
     * Asks a player if they want to increase their bid on this property.
     * @param bidder The player being asked.
     * @return True if player wants to increase their bid, false otherwise.
     */
    private boolean askForIncrease(Player bidder) {
        System.out.println("Does " + bidder.getName() + " want to increase bid on " + getName() + " for $" + currentBid + "?");
        ButtonType result = promptUser("Does " + bidder.getName() + " want to increase bid on " + getName() + " for $" + currentBid + "?",
                ButtonType.YES, ButtonType.NO);

        return result == ButtonType.YES;
    }

    /**
     * Gets the amount by which a bid is increased.
     * @return The increase bid amount.
     */
    public int getIncreaseBidAmount() {
        return increaseBidAmount;
    }

    /**
     * Sets the amount by which a bid is increased.
     * @param increaseBidAmount New increase bid amount.
     */
    public void setIncreaseBidAmount(int increaseBidAmount) {
        this.increaseBidAmount = increaseBidAmount;
    }

    /**
     * Gets the list of current bidders.
     * @return List of players currently bidding.
     */
    public List<Player> getBidders() {
        return bidders;
    }

    /**
     * Gets the current highest bid.
     * @return Current bid.
     */
    public int getCurrentBid() {
        return currentBid;
    }
    
}
