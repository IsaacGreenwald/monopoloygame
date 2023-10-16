import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ChanceCardTest {

    private Player player;
    private ChanceCard chanceCard;

    @Before
    public void setup() {
        player = new Player("Test Player");
        chanceCard = new ChanceCard(player);
    }

    @Test
    public void testAdvanceToBoardWalk() {
        chanceCard.advanceToBoardWalk();
        assertEquals(39, player.getPosition());
    }

    @Test
    public void testAdvanceToGo() {
        chanceCard.advanceToGo();
        assertEquals(0, player.getPosition());
        assertEquals(200, player.getMoney());  // Assuming player starts with 0 money.
    }

    @Test
    public void testBankPaysDividend() {
        chanceCard.bankPaysDividend();
        assertEquals(50, player.getMoney());  // Assuming player starts with 0 money.
    }

    @Test
    public void testSpeedingFine() {
        player.setMoney(100);  // Set the player's money to 100 before the fine.
        chanceCard.speedingFine();
        assertEquals(85, player.getMoney());  // Expect 85 after a 15 fine.
    }

}
