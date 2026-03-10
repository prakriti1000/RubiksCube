package View;

import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import Model.RubiksCube;

public class RubiksView {

  private final SubScene subScene;
  private final Group root3D;
  private final BorderPane rootPane; // holds subScene + UI controls

  public RubiksView(RubiksCube model) {
    this.subScene = subScene;
    root3D = new Group();
    rootPane = new BorderPane();
    rootPane.setCenter(subScene);
  }

  public BorderPane getRoot() {
    return rootPane;
  }

  public Group getRoot3D() {
    return root3D;
  }

  public SubScene getSubScene() {
    return subScene;
  }
}