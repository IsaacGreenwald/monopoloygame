import java.util.ArrayList;
import javafx.scene.image.ImageView;

public class ComputerPlayer extends Player {

    private int money;
    private int position;
    private boolean inJail;
    private ArrayList<Property> cards;
    private MonopolyPiece piece;
    private ImageView tokenImageView;
    private int consecutiveDoubles;
    private boolean hasGetOutOfJailFreeCard = false;
    private int jailTurns;
    private static Strategy strategy;
    private int configuration;

    /**
     * Constructor for the computer player class
     * @param id Number to keep track of computer players
     * @param strategy Given by the user
     */
    public ComputerPlayer(String id, Strategy strategy, MonopolyPiece piece) {
        super(id);
        cards = new ArrayList<Property>();
        this.setMoney(1500); // Starting amount in Monopoly
        this.setPosition(0);
        this.inJail = false; // Initialize the inJail flag
        this.jailTurns = 0;
        this.strategy = strategy;
        this.piece = piece;
    }

    /**
     * @return Properties owned by computer player
     */
    public ArrayList<Property> getCards() {
        return this.cards;
    }

    public void chooseBuyProperty(Property property) {
        strategy.chooseBuyProperty(property, this);
    }

    public void addProperty(Property property) {
        cards.add(property);
    }

    public void mortgage() {
        strategy.mortgageProperty(this);
    }

    public int getConfiguration() {
        return configuration;
    }

    public void setConfiguration(int configuration) {
        this.configuration = configuration;
    }

    public MonopolyPiece getPiece() {
        return piece;
    }

    public void setPiece(MonopolyPiece piece) {
        this.piece = piece;
    }

    // Add getters and setters for other properties as needed
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public ImageView getTokenImageView() {
        return tokenImageView;
    }

    public void setTokenImageView(ImageView tokenImageView) {
        this.tokenImageView = tokenImageView;
    }

    public int getConsecutiveDoubles() {
        return consecutiveDoubles;
    }

    public void setConsecutiveDoubles(int consecutiveDoubles) {
        this.consecutiveDoubles = consecutiveDoubles;
    }

    public boolean hasGetOutOfJailFreeCard() {
        return hasGetOutOfJailFreeCard;
    }

    public void setHasGetOutOfJailFreeCard(boolean hasGetOutOfJailFreeCard) {
        this.hasGetOutOfJailFreeCard = hasGetOutOfJailFreeCard;
    }

    public int getJailTurns() {
        return jailTurns;
    }

    public void setJailTurns(int jailTurns) {
        this.jailTurns = jailTurns;
    }

    public static Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
