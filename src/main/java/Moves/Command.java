package Moves;

public interface Command {
    void execute(int executionParameter);
    void undo(int parameter);
}
