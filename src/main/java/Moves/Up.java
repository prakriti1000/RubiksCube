package Moves;

import Model.RubiksCube;

public class Up implements Command {
  private final RubiksCube cube;

  public Up(RubiksCube cube) {
    this.cube = cube;
  }

  @Override
  public void execute(int row) {
    cube.rotateUp(row);
  }

  @Override
  public void undo(int row) {
    cube.rotateDown(row);
  }
}
