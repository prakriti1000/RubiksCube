package Moves;

import Model.RubiksCube;

public class Down implements Command {
  private final RubiksCube cube;

  public Down(RubiksCube cube) {
    this.cube = cube;
  }

  @Override
  public void execute(int row) {
    cube.rotateDown(row);
  }

  @Override
  public void undo(int row) {
    cube.rotateUp(row);
  }
}
