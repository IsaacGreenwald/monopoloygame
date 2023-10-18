public class ComputerPlayerFactory {
    public static ComputerPlayer createComputerPlayer(String name, MonopolyPiece piece, int configuration) {
        switch(configuration) {
            case 1:
                return new ComputerPlayer(name, new DefaultStrategy(), piece);
            case 2:
                return new ComputerPlayer(name, new ConservativeStrategy(), piece);
            default:
                throw new IllegalArgumentException("Invalid configuration value");
        }
    }
}