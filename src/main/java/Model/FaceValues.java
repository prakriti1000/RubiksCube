package Model;

public enum FaceValues {
    FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM;

    public String toString() {
        return this.name().toUpperCase();
    }
}
