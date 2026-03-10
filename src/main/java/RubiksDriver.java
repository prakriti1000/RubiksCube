
import View.RubiksView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Model.RubiksCube;

public class RubiksDriver extends Application {

  @Override
  public void start(Stage stage) {
    RubiksCube model = new RubiksCube();
    model.initializeCube(false);

    RubiksView view = new RubiksView(model);

    Scene scene = new Scene(view.getRoot(), 800, 600);
    stage.setScene(scene);
    stage.setTitle("Rubik's Cube");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}