package Model;

public interface Cube {
    // Different things we can do to a cube:

    // Get a specific face of the cube
    Face getFace(int index);

    // Initialize the cube with 6 faces of given size

    void initializeCube(boolean randomize);

    // Rotation methods!

    void rotateLeft(int rowToRotate);

    void rotateRight(int rowToRotate);

    void rotateUp(int colToRotate);

    void rotateDown(int colToRotate);

    boolean isSolved();
}
