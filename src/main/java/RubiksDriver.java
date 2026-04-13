import Controller.RubiksController;
import View.RubiksView;
import Model.RubiksCube;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RubiksDriver extends Application {

  @Override
  public void start(Stage stage) {
    // Model
    RubiksCube model = new RubiksCube();
    model.initializeCube(false); // start solved

    // View
    RubiksView view = new RubiksView(model);

    // Controller
    RubiksController controller = new RubiksController(model);

    // Wire the controls to the controller
    view.attachControls(controller);

    // Scene & Stage
    Scene scene = new Scene(view.getRoot(), 800, 650);
    scene.setFill(javafx.scene.paint.Color.rgb(20, 20, 28));
    stage.setScene(scene);
    stage.setTitle("Rubik's Cube");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}