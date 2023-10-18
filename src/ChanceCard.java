import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChanceCard {

	/**
	Advance to Boardwalk
	Advance to Go (Collect $200)
	Advance to Illinois Avenue. If you pass Go, collect $200
	Advance to St. Charles Place. If you pass Go, collect $200
	Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay wonder twice the rental to which they are otherwise entitled
	Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.
	Bank pays you dividend of $50
	Get Out of Jail Free
	Go Back 3 Spaces
	Go to Jail. Go directly to Jail, do not pass Go, do not collect $200
	Make general repairs on all your property. For each house pay $25. For each hotel pay $100
	Speeding fine $15
	Take a trip to Reading Railroad. If you pass Go, collect $200
	You have been elected Chairman of the Board. Pay each player $50
	Your building loan matures. Collect $150
	**/
	
	private Player player;
    private List<Runnable> cards = new ArrayList<>();
    private Random random;
	 private MonopolyGame monopolyGame;
	
	public ChanceCard(Player player) {
        this.player = player;
        this.random = new Random();	
        this.monopolyGame = monopolyGame;
        
        // Add all methods to the list
        cards.add(this::advanceToBoardWalk);
        cards.add(this::advanceToGo);
        cards.add(this::advanceToIllinoisAvenue);
        cards.add(this::advanceToStCharlesPlace);
        cards.add(this::advanceToNearestRailroad);
        cards.add(this::advanceToNearestUtility);
        cards.add(this::bankPaysDividend);
        cards.add(this::getOutOfJailFree);
        cards.add(this::goBackThreeSpaces);
        cards.add(this::goToJail);
        // cards.add(this::makeGeneralRepairs);
        cards.add(this::speedingFine);
        cards.add(this::tripToReadingRailroad);
        cards.add(this::electedChairman);
        cards.add(this::buildingLoanMatures);
        
    }
	
    public void pickCard() {
        int index = random.nextInt(cards.size());
        cards.get(index).run();
    }
	
	public void advanceToBoardWalk() {
		DialogUtils.showAlert("Advance to BoardWalk");
		player.setPosition(39);
	}
	
	public void advanceToGo() {
		DialogUtils.showAlert("Advance to Go (Collect $200)");
		player.setPosition(0);
		player.setMoney(player.getMoney() + 200);
	}
	
    public void advanceToIllinoisAvenue() {

    	DialogUtils.showAlert("Advance to Illinois Avenue. If you pass Go, collect $200");
        int previousLocation = player.getPosition();
        player.setPosition(24);
        if(previousLocation >= 25) {
    		player.setMoney(player.getMoney() + 200);
        }
    }

    public void advanceToStCharlesPlace() {

    	DialogUtils.showAlert("Advance to St. Charles Place. If you pass Go, collect $200");
        int previousLocation = player.getPosition();
        player.setPosition(11);
        if(previousLocation >= 12) {
    		player.setMoney(player.getMoney() + 200);
        }    
    }

    public void advanceToNearestRailroad() {
    	DialogUtils.showAlert("Advance to the nearest Railroad...");
        int position = player.getPosition();

        if (position < 5 || position >= 35) {
            player.setPosition(5); // Advance to the railroad at position 5
            DialogUtils.showAlert("You advanced to Reading Railroad");
        } else if (position < 15) {
            player.setPosition(15); // Advance to the railroad at position 15
            DialogUtils.showAlert("You advanced to Pennsylvania Railroad");
        } else if (position < 25) {
            player.setPosition(25); // Advance to the railroad at position 25
            DialogUtils.showAlert("You advanced to B. & O. Railroad");
        } else if (position < 35) {
            player.setPosition(35); // Advance to the railroad at position 35
            DialogUtils.showAlert("You advanced to Short Line");
        }
    }

    public void advanceToNearestUtility() {
    	DialogUtils.showAlert("Advance token to nearest Utility...");
        int position = player.getPosition();

        if (position < 12 || position >= 28) {
            player.setPosition(12); // Advance to "Electric Company" at position 12
        } else if (position < 28) {
            player.setPosition(28); // Advance to "Water Works" at position 28
        }
    }


    public void bankPaysDividend() {
    	DialogUtils.showAlert("Bank pays you dividend of $50");
        player.setMoney(player.getMoney() + 50);
    }

    public void getOutOfJailFree() {
    	DialogUtils.showAlert("Get Out of Jail Free");
    	DialogUtils.showAlert(player.getName() + " has recieved a get out of jail free card");
        player.giveGetOutOfJailFreeCard();
    }

    public void goBackThreeSpaces() {
    	DialogUtils.showAlert("Go Back 3 Spaces");
        player.setPosition(player.getPosition() - 3);
        DialogUtils.showAlert("You are not on " + player.getPosition());
    }

    public void goToJail() {
    	DialogUtils.showAlert("Go to Jail. Go directly to jail, do not pass Go, do not collect $200");
        player.setInJail(true);
        player.setPosition(10);
    }

    // public void makeGeneralRepairs() {
        // System.out.println("Make general repairs on all your property...");
        // Not sure about this yet
    // }

    public void speedingFine() {
    	DialogUtils.showAlert("Speeding fine $15");
        player.setMoney(player.getMoney() - 15);
    }

    public void tripToReadingRailroad() {
    	DialogUtils.showAlert("Take a trip to Reading Railroad. If you pass Go, collect $200");
        int previousLocation = player.getPosition();
        player.setPosition(5);
        if(previousLocation >= 6) {
    		player.setMoney(player.getMoney() + 200);
        }
    }

    public void electedChairman() {
    	DialogUtils.showAlert("You have been elected Chairman of the Board.");
        
    }


    public void buildingLoanMatures() {
    	DialogUtils.showAlert("Your building loan matures. Collect $150");
        player.setMoney(player.getMoney() + 150);
    }
	
}