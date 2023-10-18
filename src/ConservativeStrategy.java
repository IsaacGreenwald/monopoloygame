import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * An alternative strategy for a computer player.
 * This strategy prioritizes purchasing properties strategically and mortgaging
 * properties based on their mortgage values.
 * Implements the Strategy interface.
 * 
 * @author igree
 */
public class ConservativeStrategy implements Strategy {

    @Override
    public boolean chooseBuyProperty(Property property, ComputerPlayer computerPlayer) {
        int willingToPay = computerPlayer.getMoney() - 100;

        // Sort the player's properties by their rent value (ascending order)
        List<Property> sortedProperties = new ArrayList<>(computerPlayer.getCards());
        Collections.sort(sortedProperties, Comparator.comparing(Property::getRent));

        // Check if the player can afford to buy the property and if it's a good deal
        if (willingToPay > property.getPrice() && property.getRent() >= 50) {

            return true;
        }

        return false;
    }

    @Override
    public void mortgageProperty(ComputerPlayer computerPlayer) {
        // Sort the player's properties by their mortgage value 
        List<Property> sortedProperties = new ArrayList<>(computerPlayer.getCards());
        Collections.sort(sortedProperties, Comparator.comparingInt(Property::getMortgageValue).reversed());

        // Mortgage the highest mortgage value property if it is really available
        if (!sortedProperties.isEmpty()) {
            Property toMortgage = sortedProperties.get(0);
            toMortgage.mortgage();
        }
    }
}
