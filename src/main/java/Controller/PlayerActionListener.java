package Controller;

import Moves.Command;

public interface PlayerActionListener {

    void rowSelected(int row);

    void columnSelected(int column);

    void choseMove(Command move);

    void confirmMove();

    void undoMove();

}
