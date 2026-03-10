package View;

import Model.RubiksCube;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * CubeView class is responsible for creating and managing the visual
 * representation of a cube in the game.
 * It creates 125 small cubes (5x5x5 grid) and manages their positions and
 * colors.
 */

public class CubeView {

    private final Box box; // Represents the cube
    private RubiksCube model; // Reference to the RubiksCube model
    private int x, y, z; // Position of the cube
    // x = which row
    // y = which column
    // z = which level (how far front or back it is)

    /*
    Possible improvement: create a ReadOnly model that only has get methods.
    This way the view can only read from the model, but can't do any mutations.
    */

    public CubeView(int x, int y, int z, double size, RubiksCube model) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.model = model; //ONLY THE CONTROLLER SHOULD HAVE ACCESS TO THE MODEL
        box = new Box(size, size, size);
        box.setMaterial(new PhongMaterial(Color.GRAY)); // Just starting off with gray (Default color)
    }
    
}
