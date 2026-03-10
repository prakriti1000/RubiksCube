package Moves;

import Model.RubiksCube;

public class Right implements Command {
  private RubiksCube cube;

  public Right(RubiksCube cube) {
    this.cube = cube;
  }

  @Override
  public void execute(int row) {
    cube.rotateRight(row);
  }

  @Override
  public void undo(int row) {
    cube.rotateLeft(row);
  }
}
