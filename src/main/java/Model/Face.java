package Model;

// An interface representing a face of the Rubik's cube.

public interface Face {
  // Different things we can do to a face:

  /**
   * Get the color at a specific row and column
   *
   * @param row - row index
   * @param col - the column index
   * @return the color at that position
   */
  Colors getColorAt(int row, int col);

  /**
   * Get the size of the face (assuming it's square)
   * Total number of cells.
   *
   * @return the size of the face
   */
  int getSize();

  /**
   * Get the dimension of the face
   * Ex. If a cube is 3x3, return 3
   */
  int getDimension();

  /**
   * Check if the face is uniform
   *
   * @return true if the face is uniform, false otherwise
   */
  boolean isUniform();

  /**
   * Set the color at a specific row and column
   *
   * @param rowIndex  - the row index
   * @param newColors - the new colors for that row
   */
  void setRow(int rowIndex, Colors[] newColors);

  /**
   * Set the color at a specific row and column
   *
   * @param colIndex  - the column index
   * @param newColors - the new colors for that column
   */
  void setCol(int colIndex, Colors[] newColors);

  /**
   * Get a row at a specific index
   *
   * @param rowIndex - the row index
   * @return the colors for that row
   */
  Colors[] getRow(int rowIndex);

  /**
   * Get a column at a specific index
   *
   * @param colIndex - the column index
   * @return the colors for that column
   */
  Colors[] getCol(int colIndex);

  /**
   * Get the name of the face
   *
   * @return the name of the face
   */
  FaceValues getFaceName();

  void rotateFace(boolean clockwise);

  /**
   * We will not put the rotation methods here!
   * Rotation should be a function of the cube and not the face!
   */


}
