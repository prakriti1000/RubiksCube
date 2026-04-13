package View;

import Model.Colors;
import Model.RubiksCube;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * CubeView class is responsible for creating and managing the visual
 * representation of a single cubie in the Rubik's Cube.
 * Each cubie is a dark box with colored stickers on its exposed faces.
 */

public final class CubeView {

  private final Group group; // Contains the body + stickers
  private final RubiksCube model;
  private final int x, y, z; // Grid position (0-2 each)

  // Sticker references (null if that face isn't exposed)
  private Box frontSticker;
  private Box backSticker;
  private Box leftSticker;
  private Box rightSticker;
  private Box topSticker;
  private Box bottomSticker;

  // Face indices matching the model
  private static final int FRONT = 0;
  private static final int BACK = 1;
  private static final int LEFT = 2;
  private static final int RIGHT = 3;
  private static final int TOP = 4;
  private static final int BOTTOM = 5;

  private static final double CUBIE_SIZE = 0.9;
  private static final double STICKER_SIZE = 0.75;
  private static final double STICKER_THICKNESS = 0.02;
  private static final double GAP = 1.05; // spacing between cubies

  public CubeView(int x, int y, int z, RubiksCube model) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.model = model;
    this.group = new Group();

    // Dark body of the cubie
    Box body = new Box(CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
    body.setMaterial(new PhongMaterial(Color.rgb(30, 30, 30)));
    group.getChildren().add(body);

    // Position the whole group in 3D space
    // Center the cube around origin: offset by -1 so positions map to -1, 0, +1
    group.setTranslateX((x - 1) * GAP);
    group.setTranslateY(-(y - 1) * GAP); // flip Y so row 0 (top) is up
    group.setTranslateZ(-(z - 1) * GAP); // flip Z so front face faces camera

    // Create stickers only on exposed faces
    if (z == 2) { // FRONT face
      frontSticker = makeSticker();
      frontSticker.setTranslateZ(-(CUBIE_SIZE / 2));
      group.getChildren().add(frontSticker);
    }
    if (z == 0) { // BACK face
      backSticker = makeSticker();
      backSticker.setTranslateZ(CUBIE_SIZE / 2);
      group.getChildren().add(backSticker);
    }
    if (x == 0) { // LEFT face
      leftSticker = makeStickerSide();
      leftSticker.setTranslateX(-(CUBIE_SIZE / 2));
      group.getChildren().add(leftSticker);
    }
    if (x == 2) { // RIGHT face
      rightSticker = makeStickerSide();
      rightSticker.setTranslateX(CUBIE_SIZE / 2);
      group.getChildren().add(rightSticker);
    }
    if (y == 2) { // TOP face
      topSticker = makeStickerTop();
      topSticker.setTranslateY(-(CUBIE_SIZE / 2));
      group.getChildren().add(topSticker);
    }
    if (y == 0) { // BOTTOM face
      bottomSticker = makeStickerTop();
      bottomSticker.setTranslateY(CUBIE_SIZE / 2);
      group.getChildren().add(bottomSticker);
    }

    // Set initial colors
    updateColors();
  }

  /**
   * Reads the current state from the model and updates sticker colors.
   */
  public void updateColors() {
    if (frontSticker != null) {
      // FRONT face: row = 2 - y, col = x
      Colors c = model.getFace(FRONT).getColorAt(2 - y, x);
      setMaterial(frontSticker, toFxColor(c));
    }
    if (backSticker != null) {
      // BACK face: row = 2 - y, col = 2 - x
      Colors c = model.getFace(BACK).getColorAt(2 - y, 2 - x);
      setMaterial(backSticker, toFxColor(c));
    }
    if (leftSticker != null) {
      // LEFT face: row = 2 - y, col = 2 - z
      Colors c = model.getFace(LEFT).getColorAt(2 - y, 2 - z);
      setMaterial(leftSticker, toFxColor(c));
    }
    if (rightSticker != null) {
      // RIGHT face: row = 2 - y, col = z
      Colors c = model.getFace(RIGHT).getColorAt(2 - y, z);
      setMaterial(rightSticker, toFxColor(c));
    }
    if (topSticker != null) {
      // TOP face: row = 2 - z, col = x
      Colors c = model.getFace(TOP).getColorAt(2 - z, x);
      setMaterial(topSticker, toFxColor(c));
    }
    if (bottomSticker != null) {
      // BOTTOM face: row = z, col = x
      Colors c = model.getFace(BOTTOM).getColorAt(z, x);
      setMaterial(bottomSticker, toFxColor(c));
    }
  }

  /** Returns the Group node to add to the scene. */
  public Group getNode() {
    return group;
  }

  // -- Helper methods --

  /** Sticker facing +Z or -Z (front/back) */
  private Box makeSticker() {
    return new Box(STICKER_SIZE, STICKER_SIZE, STICKER_THICKNESS);
  }

  /** Sticker facing +X or -X (left/right) */
  private Box makeStickerSide() {
    return new Box(STICKER_THICKNESS, STICKER_SIZE, STICKER_SIZE);
  }

  /** Sticker facing +Y or -Y (top/bottom) */
  private Box makeStickerTop() {
    return new Box(STICKER_SIZE, STICKER_THICKNESS, STICKER_SIZE);
  }

  private void setMaterial(Box sticker, Color color) {
    sticker.setMaterial(new PhongMaterial(color));
  }

  /** Converts a model Color enum to a JavaFX Color. */
  private Color toFxColor(Colors c) {
    return switch (c) {
      case RED    -> Color.web("#E03C3C");
      case BLUE   -> Color.web("#3C6FE0");
      case PURPLE -> Color.web("#8B3CE0");
      case PINK   -> Color.web("#E03CA0");
      case WHITE  -> Color.WHITE;
      case BLACK  -> Color.web("#2A2A2A");
    };
  }
}