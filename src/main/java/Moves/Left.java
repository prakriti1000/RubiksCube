package Moves;

import Model.RubiksCube;

public class Left implements Command {
  private RubiksCube cube;

  public Left(RubiksCube cube) {
    this.cube = cube;
  }

  @Override
  public void execute(int row) {
    cube.rotateLeft(row);
  }

  @Override
  public void undo(int row) {
    cube.rotateRight(row);
  }
}
