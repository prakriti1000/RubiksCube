package Controller;

import Model.RubiksCube;
import Moves.Command;

public class RubiksController implements PlayerActionListener {

  private final RubiksCube cube;
  private int selectedIndex;   // stores the selected row or column
  private Command pendingMove;
  private Command lastMove;
  private int lastIndex;

  public RubiksController(RubiksCube cube) {
    this.cube = cube;
    selectedIndex = -1; //nothing selected yet
    lastIndex = -1; //last move didn't happen yet
    pendingMove = null;
  }

  @Override
    public void rowSelected(int row) {
    selectedIndex = row;
    }

    @Override
    public void columnSelected(int column) {
    selectedIndex = column;
    }

    @Override
    public void choseMove(Command move) {
    pendingMove = move;
    }

    @Override
    public void confirmMove() {
      if (pendingMove == null || selectedIndex == -1) return;
      pendingMove.execute(selectedIndex);
      lastMove = pendingMove;
      lastIndex = selectedIndex;
      pendingMove = null;
      selectedIndex = -1;
    }

  @Override
  public void undoMove() {
    if (lastMove == null)
      return; //Do nothing if the last move didn't exist
    lastMove.undo(lastIndex);
    lastMove = null;
    lastIndex = -1;
  }
}
