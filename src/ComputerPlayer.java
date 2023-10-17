import java.util.ArrayList;

import javafx.scene.image.ImageView;

public class ComputerPlayer extends Player{

	private int money;
	private int position;
	private boolean inJail;
	private ArrayList<Property> cards;
    private MonopolyPiece piece;
    private ImageView tokenImageView;
	private int consecutiveDoubles;
    private boolean hasGetOutOfJailFreeCard = false;
    private int jailTurns;        
    private Strategy strategy;

    /**
     * constructor for the computer player class
     * @param id number to keep track of computer players
     * @param strategy given by user
     */
	
	public ComputerPlayer(String id, Strategy strategy) {
		super(id);
		cards = new ArrayList<Property>();
		this.setMoney(1500); // Starting amount in Monopoly
        this.setPosition(0);
        this.inJail = false; // Initialize the inJail flag
        this.jailTurns = 0; 
        this.strategy = strategy;
		
	}
	
	/**
	 * 
	 * @return properties owned by computer player
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

}
