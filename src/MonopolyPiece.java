/**
 * Enumeration representing Monopoly pieces
 */
public enum MonopolyPiece {
    HAT("Top Hat"),
    CAR("Lambo"),
    DOG("Duck"),
    THIMBLE("Thimble"),
    SHOE("Penguin"),
    SHIP("Ship"),
    WHEELBARROW("Plane"),
    IRON("Iron");

    private final String pieceName;

    MonopolyPiece(String pieceName) {
        this.pieceName = pieceName;
    }

    public String getPieceName() {
        return pieceName;
    }

    public static MonopolyPiece getPiece(int choice) {
        return values()[choice - 1];
    }
}
