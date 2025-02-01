import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * The GameOverScreen class provides methods for displaying game over screens with different backgrounds and messages.
 */
public class GameOverScreen {

    /**
     * Displays a game over screen with a red background and a simple "GAME OVER" message.
     *
     * @param root The root GridPane where the game over screen will be displayed.
     */
    public void gameOverRed(GridPane root) {
        root.getChildren().clear();  // Clearing the root to remove any existing content

        Rectangle redBackground = new Rectangle(800, 800, Color.DARKRED);

        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        gameOverText.setFill(Color.WHITE);

        StackPane gameOverPane = new StackPane();
        gameOverPane.getChildren().addAll(redBackground, gameOverText);

        root.add(gameOverPane, 0, 0);  // Adding the gameOver screen to the root

        root.getScene().setOnKeyPressed(null);  // Disabling key events on the scene
    }

    /**
     * Displays a game over screen with a green background and includes the amount of money collected by the machine.
     *
     * @param root    The root GridPane where the game over screen will be displayed.
     * @param machine The Machine object representing the machine in the game.
     */
    public void gameOverGreen(GridPane root, Machine machine) {
        root.getChildren().clear();

        Rectangle greenBackground = new Rectangle(800, 800, Color.GREEN);

        StackPane textPane = new StackPane();
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        gameOverText.setFill(Color.WHITE);
        StackPane.setAlignment(gameOverText, Pos.CENTER); // The text was centered
        textPane.getChildren().add(gameOverText);

        StackPane textPane2 = new StackPane();
        Text gameOverText2 = new Text("Collected Money: " + machine.getMoney());
        gameOverText2.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        gameOverText2.setFill(Color.WHITE);
        StackPane.setAlignment(gameOverText2, Pos.CENTER); // The text was centered
        textPane2.getChildren().add(gameOverText2);

        // A VBox containing both texts was created.
        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(textPane, textPane2);
        vbox.setAlignment(Pos.CENTER);

        // The VBox was added to the root pane.
        root.getChildren().addAll(greenBackground, vbox);
        root.getScene().setOnKeyPressed(null);  // Disabling key events on the scene
    }
}
