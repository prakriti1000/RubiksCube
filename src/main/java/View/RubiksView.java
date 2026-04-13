package View;

import Controller.RubiksController;
import Model.RubiksCube;
import Moves.Command;
import Moves.Down;
import Moves.Left;
import Moves.Right;
import Moves.Up;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class RubiksView {

    private final SubScene subScene;
    private final Group root3D;
    private final BorderPane rootPane;
    private final VBox mainLayout;
    private final CubeView[][][] cubies;
    private final RubiksCube model;

    // For mouse-drag orbit
    private double anchorX, anchorY;
    private double anchorAngleX, anchorAngleY;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    public RubiksView(RubiksCube model) {
        this.model = model;
        root3D = new Group();
        rootPane = new BorderPane();

        // ── Build the 3x3x3 cubies ──
        cubies = new CubeView[3][3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    CubeView cv = new CubeView(x, y, z, model);
                    cubies[x][y][z] = cv;
                    root3D.getChildren().add(cv.getNode());
                }
            }
        }

        // ── Camera ──
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-10);
        camera.setNearClip(0.1);
        camera.setFarClip(100);

        // ── Lighting ──
        AmbientLight ambient = new AmbientLight(Color.rgb(180, 180, 180));
        PointLight point = new PointLight(Color.WHITE);
        point.setTranslateX(5);
        point.setTranslateY(-8);
        point.setTranslateZ(-10);
        root3D.getChildren().addAll(ambient, point);

        // ── Orbit transforms ──
        // Start slightly tilted so 3 faces are visible
        rotateX.setAngle(-25);
        rotateY.setAngle(35);
        root3D.getTransforms().addAll(rotateX, rotateY);

        // ── SubScene ──
        subScene = new SubScene(root3D, 600, 450, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.rgb(20, 20, 28));
        subScene.setCamera(camera);

        // Mouse drag to orbit
        subScene.setOnMousePressed(e -> {
            anchorX = e.getSceneX();
            anchorY = e.getSceneY();
            anchorAngleX = rotateX.getAngle();
            anchorAngleY = rotateY.getAngle();
        });
        subScene.setOnMouseDragged(e -> {
            rotateX.setAngle(anchorAngleX - (e.getSceneY() - anchorY) * 0.3);
            rotateY.setAngle(anchorAngleY + (e.getSceneX() - anchorX) * 0.3);
        });

        // ── Assemble layout ──
        // Wrap the SubScene in a Pane so it can resize freely
        javafx.scene.layout.Pane subScenePane = new javafx.scene.layout.Pane(subScene);
        subScene.widthProperty().bind(subScenePane.widthProperty());
        subScene.heightProperty().bind(subScenePane.heightProperty());

        // Use a VBox as the real root: 3D view grows, controls stay fixed at bottom
        mainLayout = new javafx.scene.layout.VBox(subScenePane);
        javafx.scene.layout.VBox.setVgrow(subScenePane, javafx.scene.layout.Priority.ALWAYS);
        mainLayout.setStyle("-fx-background-color: #14141C;");

        rootPane.setCenter(mainLayout);
        rootPane.setStyle("-fx-background-color: #14141C;");
    }

    /**
     * Builds and attaches the control panel using the given controller.
     * Called from the driver after both view and controller are created.
     */
    public void attachControls(RubiksController controller) {
        // Create the four move commands
        Command leftCmd  = new Left(model);
        Command rightCmd = new Right(model);
        Command upCmd    = new Up(model);
        Command downCmd  = new Down(model);

        Label statusLabel = new Label("Select a row or column, then a move.");
        statusLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 12;");

        // ── Row selection ──
        HBox rowBox = new HBox(6);
        rowBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < 3; i++) {
            final int row = i;
            String name = (i == 0) ? "Top" : (i == 1) ? "Mid" : "Bot";
            Button btn = makeButton("Row " + name);
            btn.setOnAction(e -> {
                controller.rowSelected(row);
                statusLabel.setText("Row " + name + " selected – pick Left or Right");
            });
            rowBox.getChildren().add(btn);
        }

        // ── Column selection ──
        HBox colBox = new HBox(6);
        colBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < 3; i++) {
            final int col = i;
            String name = (i == 0) ? "Left" : (i == 1) ? "Mid" : "Right";
            Button btn = makeButton("Col " + name);
            btn.setOnAction(e -> {
                controller.columnSelected(col);
                statusLabel.setText("Col " + name + " selected – pick Up or Down");
            });
            colBox.getChildren().add(btn);
        }

        // ── Move buttons ──
        HBox moveBox = new HBox(6);
        moveBox.setAlignment(Pos.CENTER);

        Button leftBtn = makeButton("\u2190 Left");
        leftBtn.setOnAction(e -> {
            controller.choseMove(leftCmd);
            controller.confirmMove();
            refreshCubies();
            checkSolved(statusLabel);
        });

        Button rightBtn = makeButton("Right \u2192");
        rightBtn.setOnAction(e -> {
            controller.choseMove(rightCmd);
            controller.confirmMove();
            refreshCubies();
            checkSolved(statusLabel);
        });

        Button upBtn = makeButton("\u2191 Up");
        upBtn.setOnAction(e -> {
            controller.choseMove(upCmd);
            controller.confirmMove();
            refreshCubies();
            checkSolved(statusLabel);
        });

        Button downBtn = makeButton("Down \u2193");
        downBtn.setOnAction(e -> {
            controller.choseMove(downCmd);
            controller.confirmMove();
            refreshCubies();
            checkSolved(statusLabel);
        });

        moveBox.getChildren().addAll(leftBtn, rightBtn, upBtn, downBtn);

        // ── Utility buttons ──
        HBox utilBox = new HBox(6);
        utilBox.setAlignment(Pos.CENTER);

        Button undoBtn = makeButton("Undo");
        undoBtn.setOnAction(e -> {
            controller.undoMove();
            refreshCubies();
            statusLabel.setText("Undid last move.");
        });

        Button scrambleBtn = makeButton("Scramble");
        scrambleBtn.setOnAction(e -> {
            model.initializeCube(true);
            refreshCubies();
            statusLabel.setText("Scrambled! Good luck.");
        });

        Button resetBtn = makeButton("Reset");
        resetBtn.setOnAction(e -> {
            model.initializeCube(false);
            refreshCubies();
            statusLabel.setText("Cube reset to solved state.");
        });

        utilBox.getChildren().addAll(undoBtn, scrambleBtn, resetBtn);

        // ── Panel assembly ──
        VBox controls = new VBox(8, rowBox, colBox, moveBox, utilBox, statusLabel);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));
        controls.setStyle("-fx-background-color: #1a1a24;");

        mainLayout.getChildren().add(controls);
    }

    /** Re-reads every sticker color from the model. */
    public void refreshCubies() {
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                for (int z = 0; z < 3; z++)
                    cubies[x][y][z].updateColors();
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

    // ── Private helpers ──

    private void checkSolved(Label statusLabel) {
        if (model.isSolved()) {
            statusLabel.setText("Congratulations – the cube is SOLVED!");
        }
    }

    private Button makeButton(String text) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: #2a2a3a; " +
            "-fx-text-fill: #dddddd; " +
            "-fx-font-size: 12; " +
            "-fx-padding: 6 14; " +
            "-fx-background-radius: 6; " +
            "-fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e ->
            btn.setStyle(
                "-fx-background-color: #3a3a5a; " +
                "-fx-text-fill: #ffffff; " +
                "-fx-font-size: 12; " +
                "-fx-padding: 6 14; " +
                "-fx-background-radius: 6; " +
                "-fx-cursor: hand;"
            )
        );
        btn.setOnMouseExited(e ->
            btn.setStyle(
                "-fx-background-color: #2a2a3a; " +
                "-fx-text-fill: #dddddd; " +
                "-fx-font-size: 12; " +
                "-fx-padding: 6 14; " +
                "-fx-background-radius: 6; " +
                "-fx-cursor: hand;"
            )
        );
        return btn;
    }
}