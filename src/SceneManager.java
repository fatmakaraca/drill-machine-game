import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.*;

/**
 * The SceneManager class manages scenes and provides methods for creating backgrounds.
 */
public class SceneManager {

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
     * Creates the background for the game scene.
     *
     * @param primaryStage The primary stage of the application.
     * @param root         The GridPane where the background will be added.
     * @return The created Scene object.
     */
    public Scene createBackground(Stage primaryStage, GridPane root) {

        Image top1 = new Image("file:assets/underground/top_01.png");
        Image obstacle1 = new Image("file:assets/underground/obstacle_01.png");

        Image[] elements = {
                new Image("file:assets/underground/valuable_amazonite.png"),
                new Image("file:assets/underground/valuable_diamond.png"),
                new Image("file:assets/underground/lava_01.png"),
                new Image("file:assets/underground/valuable_goldium.png"),
                new Image("file:assets/underground/obstacle_01.png")
        };

        Random random = new Random();

        int numColumns = 800 / 40;  //There are 20 columns in total.
        int numRows = 800 / 40;  //There are 20 rows in total.

        Map<String, Integer> imageCounts = new HashMap<>();  //A Map was created to keep track of how many of each image type were added.
        for (Image image : elements) {
            imageCounts.put(image.impl_getUrl(), 0); // At the beginning, the count was set to 0 for each type of image.
        }

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {  //Blue sky was added to the first three rows.
                if (j == 0 || j == 1 || j == 2) {
                    Rectangle blue = new Rectangle(40, 40, Color.MIDNIGHTBLUE);
                    root.add(blue, i, j);

                } else if (j == 3) {
                    Rectangle blue = new Rectangle(40, 40, Color.MIDNIGHTBLUE);
                    root.add(blue, i, j);
                    ImageView imageView1 = new ImageView(top1);  //Grass was added on top of the soil.
                    imageView1.setFitWidth(40);
                    imageView1.setFitHeight(40);
                    root.add(imageView1, i, j);

                } else if (i == 0 || i == 19 || j == 19) {
                    ImageView imageView = new ImageView(obstacle1);  // Obstacle images were added to the borders.
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(40);
                    root.add(imageView, i, j);
                }
            }
        }


        List<Integer> emptyCells = new ArrayList<>();  // An ArrayList was created to hold the empty cells.

        for (int i = 0; i < numColumns * numRows; i++) {  // The empty cells were added to the emptyCells ArrayList.
            int row = i / numColumns;
            int col = i % numColumns;
            if ((row != 0 && row != 1 && row != 2 && row != 3 && row != 19) && (col != 0 && col != 19)) {
                emptyCells.add(i);
            }
        }

        for (int i = 0; i < 25; i++) {  //Randomly placed elements were added to the remaining 25 spaces.
            int randomIndex = random.nextInt(emptyCells.size());
            int cellIndex = emptyCells.get(randomIndex);
            emptyCells.remove(randomIndex);

            int row = cellIndex / numColumns;
            int col = cellIndex % numColumns;

            Image image = elements[random.nextInt(elements.length)];
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            root.add(imageView, col, row);
        }


        for (int i = 0; i < numColumns; i++) {  // Filled the remaining cells with soil.
            for (int j = 0; j < numRows; j++) {

                if (j != 0 && j != 1 && j != 2 && j != 3) {
                    if (getImageUrlAt(root, i, j) == null){
                        ImageView soilView = new ImageView("file:assets/underground/soil_01.png");
                        soilView.setFitWidth(40);
                        soilView.setFitHeight(40);
                        root.add(soilView, i, j);
                    }
                }
            }
        }


        Scene scene = new Scene(root, 800, 800);

        primaryStage.setScene(scene);

        primaryStage.setTitle("HU-Load");

        primaryStage.show();
        return scene;

    }
}
