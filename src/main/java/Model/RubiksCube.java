package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RubiksCube implements Cube {

  //Giving each of the sides a specific value so we can access them easily in the array
  private static final int FRONT = 0;
  private static final int BACK = 1;
  private static final int LEFT = 2;
  private static final int RIGHT = 3;
  private static final int TOP = 4;
  private static final int BOTTOM = 5;

  // A cube has 6 faces
  private final RubiksFace[] faces; // Order: FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM
  private boolean gameStart; // checks to see if the game has started
  // (mostly just important for win condition)

  public RubiksCube() {
    // Initialize the 6 faces of the cube
    faces = new RubiksFace[6];
    gameStart = false;
  }

  @Override
  public void initializeCube(boolean randomize) {
    // Assign the colors to each face

    faces[FRONT] = new RubiksFace(3, Colors.RED, FaceValues.FRONT);
    faces[BACK] = new RubiksFace(3, Colors.BLUE, FaceValues.BACK);
    faces[LEFT] = new RubiksFace(3, Colors.PURPLE, FaceValues.LEFT);
    faces[RIGHT] = new RubiksFace(3, Colors.PINK, FaceValues.RIGHT);
    faces[TOP] = new RubiksFace(3, Colors.WHITE, FaceValues.TOP);
    faces[BOTTOM] = new RubiksFace(3, Colors.BLACK, FaceValues.BOTTOM);

    //Technically, by default, this is a "solved cube". We have to keep this in mind
    //when determining win conditions

    //This is also what a cube looks like when you first buy it, I'm pretty sure it comes solves ^^

    // If randomize is true, we can add code here to shuffle the cube
    // We want to shuffle the faces randomly
    if (randomize) {
      List<Colors> colorList = new ArrayList<>();

      for (RubiksFace face : faces) {
        for (int r = 0; r < 3; r++) {
          for (int c = 0; c < 3; c++) {
            colorList.add(face.getColorAt(r, c));
          }
        }
      }

      Collections.shuffle(colorList);

      int idx = 0;
      for (int i = 0; i < 6; i++) {
        for (int r = 0; r < 3; r++) {
          for (int c = 0; c < 3; c++) {
            faces[i].getFaceColors()[r][c] = colorList.get(idx++);
          }
        }
      }
    }
    gameStart = true;
  }

  @Override
  public Face getFace(int index) {
    return faces[index];
  }

  // These will be the hardest methods to implement.
  // But making the AI work will be much easier if we implement these correctly.

  // FaceIndex is the index of the face we want to implement a specific rotation
  // on.
  // RowsToRotate is the number of rows we want to rotate on that face. (only for
  // left and right)
  // ColsToRotate is the number of columns we want to rotate on that face. (only
  // for up and down)

  // RotateLeft: <---- this way (to make sure I can visualize it and won't mess
  // up)
  // What happens when we rotate left?
  // Top and bottom faces won't change (the values don't change)
  // Front, back, left, and right faces will change.

  // Front face front row -> Left face front row
  // Left face front row -> Back face front row
  // Back face front row -> Right face front row
  // Right face front row -> Front face front row

  @Override
  public void rotateLeft(int rowToRotate)
      throws IllegalArgumentException {
    if (!checkLegal(rowToRotate)) {
      throw new IllegalArgumentException("Illegal rotation parameters");
    }
    // Implement the rest of the method here
    // Rotate the specified row

    // Create a temporary array (starting with the front face)

    Colors[] temp;
    temp = faces[FRONT].getRow(rowToRotate); // Front face front row

    Colors[] temp2;
    temp2 = faces[LEFT].getRow(rowToRotate); // Left face front row

    faces[LEFT].setRow(rowToRotate, temp); // Front face front
    // row ->
    // Left face front
    // row
    temp = temp2;
    temp2 = faces[BACK].getRow(rowToRotate); // Back face front row

    faces[BACK].setRow(rowToRotate, temp); // Left face front
    // row ->
    // Back face front
    // row
    temp = temp2;
    temp2 = faces[RIGHT].getRow(rowToRotate); // Right face front row

    faces[RIGHT].setRow(rowToRotate, temp); // Back face front
    // row ->
    // Right face front
    // row
    temp = temp2;
    faces[FRONT].setRow(rowToRotate, temp); // Right face front
    // row ->
    // Front face front
    // row
    if (rowToRotate == 0) faces[TOP].rotateFace(true);
    else if (rowToRotate == 2) faces[BOTTOM].rotateFace(false);

  }

  // RotateRight: this way ---->

  @Override
  public void rotateRight(int rowToRotate) {
    if (!checkLegal(rowToRotate)) {
      throw new IllegalArgumentException("Illegal rotation parameters");
    }

    // Pretty similar to rotateLeft, but in the opposite direction
    // Create a temporary array (starting with the front face)
    Colors[] temp;
    temp = faces[FRONT].getRow(rowToRotate); // Front face front row

    Colors[] temp2;
    for (int i : new int[] {RIGHT, BACK, LEFT}) {
      temp2 = faces[i].getRow(rowToRotate); // Right face front row

      faces[i].setRow(rowToRotate, temp); // Front face front
      // row ->
      // Right face front
      // row
      temp = temp2;
    }// Back face front row
    // Right face front
    // Left face front row
    // Back face front

    // row ->
    // Back face front
    // row

    // row ->
    // Left face front
    // row
    faces[FRONT].setRow(rowToRotate, temp); // Left face front
    // row ->
    // Front face front
    // row
    if (rowToRotate == 0) faces[TOP].rotateFace(false);
    else if (rowToRotate == 2) faces[BOTTOM].rotateFace(true);

  }

  // RotateUp: ^ this way
  // What happens when we rotate up?

  // Left side and Right side faces won't change (the values don't change)
  // Top, bottom, front, and back faces will change.

  @Override
  public void rotateUp(int colToRotate) {
    if (!checkLegal(colToRotate)) {
      throw new IllegalArgumentException("Illegal rotation parameters");
    }

    // Create a temporary array (starting with the front face)
    // Like we did before

    Colors[] temp;
    temp = faces[FRONT].getCol(colToRotate); // Front face front col

    Colors[] temp2;
    for (int i : new int[] {TOP, BACK, BOTTOM}) {
      temp2 = faces[i].getCol(colToRotate); // Top face front col

      faces[i].setCol(colToRotate, temp); // Front face front
      // col ->
      // Top face front
      // col

      temp = temp2;
    }// Back face front col
    // Top face front
    // Bottom face front col
    // Back face front

    // col ->
    // Back face front
    // col

    // col ->
    // Bottom face front
    // col
    faces[FRONT].setCol(colToRotate, temp); // Bottom face front
    // col ->
    // Front face front
    // col
    if (colToRotate == 0) faces[LEFT].rotateFace(false);
    else if (colToRotate == 2) faces[RIGHT].rotateFace(true);
  }

  @Override
  public void rotateDown(int colToRotate) {
    if (!checkLegal(colToRotate)) {
      throw new IllegalArgumentException("Illegal rotation parameters");
    }

    // Pretty similar to rotateUp, but in the opposite direction

    Colors[] temp;
    temp = faces[FRONT].getCol(colToRotate); // Front face front col

    Colors[] temp2;
    temp2 = faces[BOTTOM].getCol(colToRotate); // Bottom face front col

    faces[BOTTOM].setCol(colToRotate, temp); // Front face front
    // col ->
    // Bottom face front
    // col
    temp = temp2;
    temp2 = faces[BACK].getCol(colToRotate); // Back face front col

    faces[BACK].setCol(colToRotate, temp); // Bottom face front
    // col ->
    // Back face front
    // col
    temp = temp2;
    temp2 = faces[TOP].getCol(colToRotate); // Top face front col

    faces[TOP].setCol(colToRotate, temp); // Back face front
    // col ->
    // Top face front
    // col
    temp = temp2;
    faces[FRONT].setCol(colToRotate, temp); // Top face front
    // col ->
    // Front face front
    // col
    if (colToRotate == 0) faces[LEFT].rotateFace(false);
    else if (colToRotate == 2) faces[RIGHT].rotateFace(true);
  }

  @Override
  public boolean isSolved() {
    return gameStart
        && faces[FRONT].isUniform()
        && faces[LEFT].isUniform()
        && faces[BACK].isUniform()
        && faces[RIGHT].isUniform()
        && faces[TOP].isUniform()
        && faces[BOTTOM].isUniform();
  }

  // Helper method to check legality of rotation
  private boolean checkLegal(int rotationNumber) {
    if (!gameStart) return false;
    return rotationNumber >= 0 && rotationNumber < faces[FRONT].getDimension();
  }
}
