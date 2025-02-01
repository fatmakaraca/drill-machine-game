import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 * The Main class serves as the entry point for the JavaFX application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        SceneManager sceneManager = new SceneManager();
        Scene scene = sceneManager.createBackground(primaryStage, root);  //The background on which the drill machine will move has been created.
        Machine machine = new Machine();
        machine.startFuelConsumption(root);  //The method that decreases the fuel value over time was called
        machine.moving(primaryStage, root, scene, machine);  //The method responsible for enabling the movement of the drill machine was called
        showFuel(root, machine);  //The method that displays the fuel value on the screen was called
    }

    /**
     * The main method launches the JavaFX application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Displays the fuel level on the screen.
     *
     * @param root    The root GridPane of the scene.
     * @param machine The Machine object representing the machine in the scene.
     */
    public static void showFuel(GridPane root, Machine machine) {
        Text textFuel = new Text("Fuel: " + String.format("%.1f", machine.getFuel())); // It takes the fuel amount and displays it on the screen
        textFuel.setFill(Color.WHITE);
        textFuel.setFont(Font.font(15));
        textFuel.setTextAlignment(TextAlignment.LEFT);  //The text was aligned to the left

        StackPane fuelPane = new StackPane();
        fuelPane.getChildren().add(textFuel);
        GridPane.setConstraints(fuelPane, 0, 0, 2, 1);

        root.getChildren().add(fuelPane);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                textFuel.setText("Fuel: " + String.format("%.3f", machine.getFuel())); // It updates the fuel amount
            }
        };
        timer.start();
    }

}
