package Model;

// The colors to be used for the rubik's cube!
public enum Colors {
    RED, BLUE, PURPLE, PINK, WHITE, BLACK;

    @Override
    public String toString() {
      return switch (this) {
        case RED -> "Red";
        case BLUE -> "Blue";
        case PURPLE -> "Purple";
        case PINK -> "Pink";
        case WHITE -> "White";
        case BLACK -> "Black";
        default -> "Unknown Color";
      };
    }
}
