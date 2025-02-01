import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * The Gravity class simulates gravity effect on the machine in the game.
 */
public class Gravity {

    /**
     * Retrieves the URL of the image displayed at the specified coordinates in the GridPane.
     *
     * @param root The GridPane containing the ImageView nodes.
     * @param x    The column index of the image within the GridPane.
     * @param y    The row index of the image within the GridPane.
     * @return The URL of the image at the specified coordinates, or null if no image is found.
     */
    public static String getImageUrlAt(GridPane root, int x, int y) {
        for (Node node : root.getChildren()) {
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y && node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                Image image = imageView.getImage();
                if (image != null) {
                    return image.impl_getUrl();
                }
            }
        }
        return null;
    }

    /**
     * Applies gravity effect to the machine by moving it downwards on the GridPane.
     *
     * @param scene      The Scene object associated with the stage.
     * @param root       The root GridPane of the scene.
     * @param imageView  The ImageView representing the machine.
     * @param machine    The Machine object representing the player's machine.
     */
    public void gravity(Scene scene, GridPane root, ImageView imageView, Machine machine) {
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private final long nanoInterval = 800000000L;

            @Override
            public void handle(long now) {
                if (!machine.isGameOver() && now - lastUpdate >= nanoInterval) { // Check if the game is still ongoing.
                    int currentIndexOfX = (int) ((imageView.getTranslateX() / 40) + 19);
                    int currentIndexOfY = (int) (imageView.getTranslateY() / 40);

                    int nextIndexOfX = currentIndexOfX;
                    int nextIndexOfY = currentIndexOfY + 1;

                    String nextImageUrl = getImageUrlAt(root, nextIndexOfX, nextIndexOfY);
                    if (nextImageUrl == null) {
                        imageView.setTranslateY(imageView.getTranslateY() + 40); // It moves down by 40 units.
                    }

                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }
}
