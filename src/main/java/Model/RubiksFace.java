package Model;

public class RubiksFace implements Face {

  private Colors[][] faceColors;
  private int row; // row and col are the same
  private FaceValues faceValue;

  public RubiksFace(int row, Colors faceColors, FaceValues faceValue) {
    this.row = row;
    this.faceColors = new Colors[row][row];
    this.faceValue = faceValue;

    for (int r = 0; r < row; r++) {
      for (int c = 0; c < row; c++) {
        this.faceColors[r][c] = faceColors;
      }
    }
  }

  @Override
  public Colors getColorAt(int row, int col)
      throws IndexOutOfBoundsException {
    if (row < 0 || row >= this.row || col < 0 || col >= this.row) {
      throw new IndexOutOfBoundsException("Row or column is out of bounds");
    }
    return faceColors[row][col];
  }

  @Override
  public int getSize() {
    return row * row; // Total # of colors on the face.
  }

  @Override
  public int getDimension() {
    return row;
  }

  @Override
  public boolean isUniform() {
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.row; col++) {
        if (faceColors[row][col] != faceColors[0][0]) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public void setRow(int rowIndex, Colors[] newColors) {
    for (int col = 0; col < this.row; col++) {
      faceColors[rowIndex][col] = newColors[col];
    }
  }

  @Override
  public void setCol(int colIndex, Colors[] newColors) {
    for (int row = 0; row < this.row; row++) {
      faceColors[row][colIndex] = newColors[row];
    }
  }

  @Override
  public Colors[] getRow(int rowIndex) {
    Colors[] rowColors = new Colors[this.row];
    for (int col = 0; col < this.row; col++) {
      rowColors[col] = faceColors[rowIndex][col];
    }
    return rowColors;
  }

  @Override
  public Colors[] getCol(int colIndex) {
    Colors[] colColors = new Colors[this.row];
    for (int row = 0; row < this.row; row++) {
      colColors[row] = faceColors[row][colIndex];
    }
    return colColors;
  }

  @Override
  public FaceValues getFaceName() {
    return faceValue;
  }

  public Colors[][] getFaceColors() {
    return faceColors;
  }

  @Override
  public void rotateFace(boolean clockwise) {
    Colors[][] rotated = new Colors[row][row];
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < row; c++) {
        if (clockwise) {
          rotated[c][row - 1 - r] = faceColors[r][c];
        } else {
          rotated[row - 1 - c][r] = faceColors[r][c];
        }
      }
    }
    faceColors = rotated;
  }
}
