/**
 * The ComputerPlayerFactory class gives a way to create computer player objects
 * with different strategies for gameplay.
 */
public class ComputerPlayerFactory {
    
    /**
     * Creates a computer player with the specified name, game piece, and strategy configuration.
     *
     * @param name The name of the computer player.
     * @param piece The MonopolyPiece representing the game piece associated with the computer player.
     * @param configuration The strategy configuration to determine the player's behavior:
     *                      - 1: Creates a computer player with the DefaultStrategy.
     *                      - 2: Creates a computer player with the ConservativeStrategy.
     * @return A ComputerPlayer object configured according to the specified parameters.
     */
    public static ComputerPlayer createComputerPlayer(String name, MonopolyPiece piece, int configuration) {
        switch (configuration) {
            case 1:
                return new ComputerPlayer(name, new DefaultStrategy(), piece);
            case 2:
                return new ComputerPlayer(name, new ConservativeStrategy(), piece);
            default:
                throw new IllegalArgumentException("Invalid configuration value");
        }
    }
}
