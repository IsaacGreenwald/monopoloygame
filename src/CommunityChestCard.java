import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommunityChestCard {

	/**
	Advance to Go (Collect $200)
	Bank error in your favor. Collect $200
	Doctor’s fee. Pay $50
	From sale of stock you get $50
	Get Out of Jail Free
	Go to Jail. Go directly to jail, do not pass Go, do not collect $200
	Holiday fund matures. Receive $100
	Income tax refund. Collect $20
	It is your birthday. Collect $10 from every player
	Life insurance matures. Collect $100
	Pay hospital fees of $100
	Pay school fees of $50
	Receive $25 consultancy fee
	You are assessed for street repair. $40 per house. $115 per hotel
	You have won second prize in a beauty contest. Collect $10
	You inherit $100
	**/

	 private Player player;
	 private List<Runnable> cards = new ArrayList<>();
	 private Random random;

	    public CommunityChestCard(Player player) {
	        this.player = player;
	        this.random = new Random();	 
	        
	        // Add all methods to the list
	        cards.add(this::advanceToGo);
	        cards.add(this::bankErrorInYourFavor);
	        cards.add(this::doctorsFee);
	        cards.add(this::saleOfStock);
	        cards.add(this::getOutOfJailFree);
	        cards.add(this::goToJail);
	        cards.add(this::holidayFundMatures);
	        cards.add(this::incomeTaxRefund);
	        cards.add(this::itsYourBirthday);
	        cards.add(this::lifeInsuranceMatures);
	        cards.add(this::payHospitalFees);
	        cards.add(this::paySchoolFees);
	        cards.add(this::receiveConsultancyFee);
	        cards.add(this::assessedForStreetRepair);
	        cards.add(this::wonBeautyContest);
	        cards.add(this::youInherit);
	        
	    }
	    
	    public void pickCard() {
	        int index = random.nextInt(cards.size()); // pick a random index
	        cards.get(index).run(); // run the method at the picked index
	    }

	    public void advanceToGo() {
	    	DialogUtils.showAlert("Advance to Go (Collect $200)");
	        player.setPosition(0);
	        player.setMoney(player.getMoney() + 200);
	    }

	    public void bankErrorInYourFavor() {
	    	DialogUtils.showAlert("Bank error in your favor. Collect $200");
	        player.setMoney(player.getMoney() + 200);
	    }

	    public void doctorsFee() {
	    	DialogUtils.showAlert("Doctor’s fee. Pay $50");
	        player.setMoney(player.getMoney() - 50);
	    }

	    public void saleOfStock() {
	    	DialogUtils.showAlert("From sale of stock you get $50");
	        player.setMoney(player.getMoney() + 50);
	    }

	    public void getOutOfJailFree() {
	    	DialogUtils.showAlert("Get Out of Jail Free");
	    	DialogUtils.showAlert(player.getName() + " has recieved a get out of jail free card");
	        player.giveGetOutOfJailFreeCard();
	    }

	    public void goToJail() {
	    	DialogUtils.showAlert("Go to Jail. Go directly to jail, do not pass Go, do not collect $200");
	        player.setInJail(true);
	        player.setPosition(10);
	    }

	    public void holidayFundMatures() {
	    	DialogUtils.showAlert("Holiday fund matures. Receive $100");
	        player.setMoney(player.getMoney() + 100);
	    }

	    public void incomeTaxRefund() {
	    	DialogUtils.showAlert("Income tax refund. Collect $20");
	        player.setMoney(player.getMoney() + 20);
	    }

	    public void itsYourBirthday() {
	    	DialogUtils.showAlert("It is your birthday. Collect $10 from every player");
	        // Not sure about this yet
	    }

	    public void lifeInsuranceMatures() {
	    	DialogUtils.showAlert("Life insurance matures. Collect $100");
	        player.setMoney(player.getMoney() + 100);
	    }

	    public void payHospitalFees() {
	    	DialogUtils.showAlert("Pay hospital fees of $100");
	        player.setMoney(player.getMoney() - 100);
	    }

	    public void paySchoolFees() {
	    	DialogUtils.showAlert("Pay school fees of $50");
	        player.setMoney(player.getMoney() - 50);
	    }

	    public void receiveConsultancyFee() {
	    	DialogUtils.showAlert("Receive $25 consultancy fee");
	        player.setMoney(player.getMoney() + 25);
	    }

	    public void assessedForStreetRepair() {
	    	DialogUtils.showAlert("You are assessed for street repair. $40 per house. $115 per hotel");
	        // Not sure about this yet
	    }

	    public void wonBeautyContest() {
	    	DialogUtils.showAlert("You have won second prize in a beauty contest. Collect $10");
	        player.setMoney(player.getMoney() + 10);
	    }

	    public void youInherit() {
	    	DialogUtils.showAlert("You inherit $100");
	        player.setMoney(player.getMoney() + 100);
	    }
	}
