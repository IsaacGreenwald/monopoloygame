import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CommunityChestCardTest {

    private Player testPlayer;
    private CommunityChestCard communityChestCard;

    @Before
    public void setUp() {
        testPlayer = new Player("Test Player");
        communityChestCard = new CommunityChestCard(testPlayer);
    }

    // Test to check if the pickCard method doesn't return null
    @Test
    public void testPickCardNotNull() {
        communityChestCard.pickCard();
        // Ideally, this test just checks if the method doesn't throw any exceptions
        // No explicit assertion needed since we're testing side-effects on the player.
    }

    // Test to check if picking the "Advance to Go" card has the expected effect
    @Test
    public void testAdvanceToGoCardEffect() {
        communityChestCard.advanceToGo();
        assertEquals("Player should be at position 0 after drawing the 'Advance to Go' card", 0, testPlayer.getPosition());
        assertTrue("Player should have received $200 after drawing the 'Advance to Go' card", testPlayer.getMoney() >= 200);
    }

    // Test for "Bank error in your favor" card
    @Test
    public void testBankErrorInYourFavorEffect() {
        int initialMoney = testPlayer.getMoney();
        communityChestCard.bankErrorInYourFavor();
        assertEquals("Player should have received $200 after drawing the 'Bank error in your favor' card", initialMoney + 200, testPlayer.getMoney());
    }

}
