/**
 * This is an enumeration class that allows the mapping of monopoly pieces to their token images. 
 */
public enum MonopolyPiece {
    HAT("Top Hat", "tophat.png"),
    CAR("Car", "car.png"),
    CAT("Cat", "cat.png"),
    DOG("Dog", "dog.png"),
    SHOE("Shoe", "shoe.png"),
    HORSE("Horse", "horse.png"),
    WHEELBARROW("Wheel Barrow", "wheelbarrow.jpeg"),
    IRON("Iron", "iron.png");

    private final String pieceName;
    private final String iconURL;  

    MonopolyPiece(String pieceName, String iconURL) {
        this.pieceName = pieceName;
        this.iconURL = iconURL;
    }

    public String getPieceName() {
        return pieceName;
    }

    public String getIconURL() {  
        return iconURL;
    }

    public static MonopolyPiece getPiece(int choice) {
        return values()[choice - 1];
    }
}
