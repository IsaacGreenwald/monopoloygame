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
		System.out.println("Advance to BoardWalk");
		player.setPosition(39);
	}
	
	public void advanceToGo() {
		System.out.println("Advance to Go (Collect $200)");
		player.setPosition(0);
		player.setMoney(player.getMoney() + 200);
	}
	
    public void advanceToIllinoisAvenue() {
        System.out.println("Advance to Illinois Avenue. If you pass Go, collect $200");
        int previousLocation = player.getPosition();
        player.setPosition(24);
        if(previousLocation >= 25) {
    		player.setMoney(player.getMoney() + 200);
        }
    }

    public void advanceToStCharlesPlace() {
        System.out.println("Advance to St. Charles Place. If you pass Go, collect $200");
        int previousLocation = player.getPosition();
        player.setPosition(11);
        if(previousLocation >= 12) {
    		player.setMoney(player.getMoney() + 200);
        }    
    }

    public void advanceToNearestRailroad() {
        System.out.println("Advance to the nearest Railroad...");
        int position = player.getPosition();

        if (position < 5 || position >= 35) {
            player.setPosition(5); // Advance to the railroad at position 5
            System.out.println("You advanced to Reading Railroad");
        } else if (position < 15) {
            player.setPosition(15); // Advance to the railroad at position 15
            System.out.println("You advanced to Pennsylvania Railroad");
        } else if (position < 25) {
            player.setPosition(25); // Advance to the railroad at position 25
            System.out.println("You advanced to B. & O. Railroad");
        } else if (position < 35) {
            player.setPosition(35); // Advance to the railroad at position 35
            System.out.println("You advanced to Short Line");
        }
    }

    public void advanceToNearestUtility() {
        System.out.println("Advance token to nearest Utility...");
        int position = player.getPosition();

        if (position < 12 || position >= 28) {
            player.setPosition(12); // Advance to "Electric Company" at position 12
        } else if (position < 28) {
            player.setPosition(28); // Advance to "Water Works" at position 28
        }
    }


    public void bankPaysDividend() {
        System.out.println("Bank pays you dividend of $50");
        player.setMoney(player.getMoney() + 50);
    }

    public void getOutOfJailFree() {
    	System.out.println("Get Out of Jail Free");
        System.out.println(player.getName() + " has recieved a get out of jail free card");
        player.giveGetOutOfJailFreeCard();
    }

    public void goBackThreeSpaces() {
        System.out.println("Go Back 3 Spaces");
        player.setPosition(player.getPosition() - 3);
        System.out.println("You are not on " + player.getPosition());
    }

    public void goToJail() {
        System.out.println("Go to Jail. Go directly to jail, do not pass Go, do not collect $200");
        player.setInJail(true);
        player.setPosition(10);
    }

    // public void makeGeneralRepairs() {
        // System.out.println("Make general repairs on all your property...");
        // Not sure about this yet
    // }

    public void speedingFine() {
        System.out.println("Speeding fine $15");
        player.setMoney(player.getMoney() - 15);
    }

    public void tripToReadingRailroad() {
        System.out.println("Take a trip to Reading Railroad. If you pass Go, collect $200");
        int previousLocation = player.getPosition();
        player.setPosition(5);
        if(previousLocation >= 6) {
    		player.setMoney(player.getMoney() + 200);
        }
    }

    public void electedChairman() {
        System.out.println("You have been elected Chairman of the Board. Pay each player $50");
        
        // Get the list of computer players
        ArrayList<ComputerPlayer> cpList = monopolyGame.getComputerPlayerList();

        // Total amount to be deducted from main player
        int totalPaid = cpList.size() * 50;

        // Pay each computer player $50
        for (ComputerPlayer cp : cpList) {
            cp.setMoney(cp.getMoney() + 50);
        }
        
        // Deduct the total paid amount from the main player
        player.setMoney(player.getMoney() - totalPaid); 
    }


    public void buildingLoanMatures() {
        System.out.println("Your building loan matures. Collect $150");
        player.setMoney(player.getMoney() + 150);
    }
	
}

