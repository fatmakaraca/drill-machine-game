import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.util.Objects;

/**
 * The Machine class represents the machine controlled by the player in the game.
 */
public class Machine {
    private double fuel = 150;
    private int haul;
    private int money;

    /**
     * Retrieves the current fuel level of the machine.
     *
     * @return The current fuel level.
     */
    public double getFuel() {
        return fuel;
    }

    /**
     * Retrieves the total haul collected by the machine.
     *
     * @return The total haul.
     */
    public int getHaul() {
        return haul;
    }

    /**
     * Sets the total haul collected by the machine.
     *
     * @param haul The total haul to set.
     */
    public void setHaul(int haul) {
        this.haul = haul;
    }

    /**
     * Retrieves the total money earned by the machine.
     *
     * @return The total money earned.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the total money earned by the machine.
     *
     * @param money The total money earned to set.
     */
    public void setMoney(int money) {
        this.money = money;
    }

    private boolean gameOver = false;

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Sets the game over state.
     *
     * @param gameOver True to set the game over, false otherwise.
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Starts the fuel consumption animation timer.
     *
     * @param root The root GridPane of the scene.
     */
    public void startFuelConsumption(GridPane root){
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000) { // Checking if one second has passed.
                    lastUpdate = now;
                    decreaseFuelWithTime(root); // It decreases the fuel value.
                    if (fuel <= 0 && !gameOver) {
                       gameOver = true;  // If the fuel runs out, it triggers game over.
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * Decreases the fuel level of the machine with time.
     *
     * @param root The root GridPane of the scene.
     */
    public void decreaseFuelWithTime(GridPane root) {
        fuel -= 0.01;
        if (fuel < 0) {
            fuel = 0;
            GameOverScreen gameOverScreen = new GameOverScreen();
            gameOverScreen.gameOverGreen(root, this);
            gameOver = true;
        }
    }

    /**
     * Decreases the fuel level of the machine.
     *
     * @param root The root GridPane of the scene.
     */
    public void decreaseFuel(GridPane root) {
        fuel -= 1.0;
        if (fuel < 0) {
            fuel = 0;
            GameOverScreen gameOverScreen = new GameOverScreen();
            gameOverScreen.gameOverGreen(root, this);
            gameOver = true;
        }
    }

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
     * Retrieves the node displayed at the specified coordinates in the GridPane.
     *
     * @param root The GridPane containing the nodes.
     * @param x    The column index of the node within the GridPane.
     * @param y    The row index of the node within the GridPane.
     * @return The node at the specified coordinates, or null if no node is found.
     */
    public static Node getNodeAt(GridPane root, int x, int y) {
        for (Node node : root.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex != null && rowIndex != null && columnIndex == x && rowIndex == y) {
                if (node instanceof ImageView) {
                    return node;
                }
            }
        }
        return null;
    }


    /**
     * Controls the movement of the machine based on user input.
     *
     * @param primaryStage The primary stage of the application.
     * @param root         The root GridPane of the scene.
     * @param scene        The Scene object associated with the stage.
     * @param machine      The Machine object representing the player's machine.
     */
    public void moving(Stage primaryStage, GridPane root, Scene scene, Machine machine) {

        Image drillLeft = new Image("file:assets/drill/drill_01.png");
        Image drillRight = new Image("file:assets/drill/drill_60.png");
        Image drillUp = new Image("file:assets/drill/drill_25.png");
        Image drillDown = new Image("file:assets/drill/drill_40.png");
        ImageView imageView = new ImageView(drillLeft);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        root.add(imageView, 19, 0);

        StackPane textPane2 = new StackPane();
        Text text2 = new Text("haul:" + machine.getHaul());
        text2.setFill(Color.WHITE);
        text2.setFont(Font.font(15));
        text2.setTextAlignment(TextAlignment.LEFT);
        textPane2.getChildren().add(text2);
        GridPane.setConstraints(textPane2, 0, 1, 2, 1);
        root.getChildren().add(textPane2);


        Text text3 = new Text("money:" + machine.getMoney());
        text3.setFill(Color.WHITE);
        text3.setFont(Font.font(15));
        text3.setTextAlignment(TextAlignment.LEFT);
        StackPane textPane3 = new StackPane();
        textPane3.getChildren().add(text3);
        GridPane.setConstraints(textPane3, 0, 2, 2, 1);
        root.getChildren().add(textPane3);


        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();

            double moveX = 0;
            double moveY = 0;

            int currentIndexOfX = (int) ((imageView.getTranslateX() / 40) + 19);  // The index is set to start from 0 and end at 19.
            int currentIndexOfY = (int) (imageView.getTranslateY() / 40);
            int nextIndexOfX = 0;
            int nextIndexOfY = 0;

            switch (keyCode) {
                case UP:
                    if (currentIndexOfY != 0) {
                        nextIndexOfX = currentIndexOfX;
                        nextIndexOfY = currentIndexOfY - 1;  // If moving upwards, the next Y index would be 1 less than the current Y index.

                        if (getImageUrlAt(root, nextIndexOfX, nextIndexOfY) != null) {
                            // The drill machine checks the occupancy of the cell because it cannot dig through the upper cell.
                            if (!Objects.equals(getImageUrlAt(root, nextIndexOfX, nextIndexOfY), "file:assets/drill/drill_25.png") &&
                                    !Objects.equals(getImageUrlAt(root, nextIndexOfX, nextIndexOfY), "file:assets/drill/drill_60.png") &&
                                    !Objects.equals(getImageUrlAt(root, nextIndexOfX, nextIndexOfY), "file:assets/drill/drill_40.png") &&
                                    !Objects.equals(getImageUrlAt(root, nextIndexOfX, nextIndexOfY), "file:assets/drill/drill_01.png")) {
                                imageView.setImage(drillUp);
                                break;
                            }
                        }

                        moveY -= 40;
                        imageView.setImage(drillUp);  // The drill machine moved up by one cell.
                    }
                    break;

                case DOWN:
                    nextIndexOfX = currentIndexOfX;
                    nextIndexOfY = currentIndexOfY + 1;  // If moving downwards, the next y index will be one greater than the current y index.

                    String nextImageUrl2 = getImageUrlAt(root, nextIndexOfX, nextIndexOfY);
                    if (Objects.equals(nextImageUrl2, "file:assets/underground/obstacle_01.png")) { // If there is an obstacle in the next cell, it cannot move.
                        imageView.setImage(drillDown);
                        break;
                    } else if (Objects.equals(nextImageUrl2, "file:assets/underground/lava_01.png")) { // If there is lava in the next cell, it triggers game over.
                        GameOverScreen gameOverScreen = new GameOverScreen();
                        gameOverScreen.gameOverRed(root);
                        machine.setGameOver(true);
                        break;
                    } else {
                        Valuable valuable = null;
                        if (Objects.equals(nextImageUrl2, "file:assets/underground/valuable_amazonite.png")) {
                            valuable = new Amazonite();

                        } else if (Objects.equals(nextImageUrl2, "file:assets/underground/valuable_diamond.png")) {
                            valuable = new Diamond();

                        } else if (Objects.equals(nextImageUrl2, "file:assets/underground/valuable_goldium.png")) {
                            valuable = new Goldium();
                        }
                        if (valuable != null) {
                            int totalHaul = machine.getHaul();
                            machine.setHaul(totalHaul + valuable.getWeight());

                            int totalMoney = machine.getMoney();
                            machine.setMoney(totalMoney + valuable.getWorth());
                        }
                    }

                    if (getImageUrlAt(root, nextIndexOfX, nextIndexOfY) != null) {
                        Node currentNode = getNodeAt(root, nextIndexOfX, nextIndexOfY);
                        if (currentNode instanceof ImageView) {
                            root.getChildren().remove(currentNode);
                        } else if (currentNode instanceof Rectangle) {
                            root.getChildren().remove(currentNode);
                        }

                        Rectangle brown = new Rectangle(40, 40, Color.SADDLEBROWN);
                        root.getChildren().add(root.getChildren().indexOf(imageView), brown);
                        GridPane.setConstraints(brown, nextIndexOfX, nextIndexOfY);  // Brown rectangles were added to the cells dug by the drill machine.
                    }
                    imageView.setImage(drillDown);
                    moveY += 40;
                    break;

                case LEFT:
                    if (currentIndexOfX != 0) {
                        nextIndexOfX = currentIndexOfX - 1;
                        nextIndexOfY = currentIndexOfY;

                        String nextImageUrl3 = getImageUrlAt(root, nextIndexOfX, nextIndexOfY);
                        if (Objects.equals(nextImageUrl3, "file:assets/underground/obstacle_01.png")) { // If there is an obstacle in the next cell, it cannot move.
                            imageView.setImage(drillLeft);
                            break;
                        } else if (Objects.equals(nextImageUrl3, "file:assets/underground/lava_01.png")) { // If there is lava in the next cell, it triggers game over.
                            GameOverScreen gameOverScreen = new GameOverScreen();
                            gameOverScreen.gameOverRed(root);
                            machine.setGameOver(true);
                            break;
                        } else {
                            Valuable valuable = null;
                            if (Objects.equals(nextImageUrl3, "file:assets/underground/valuable_amazonite.png")) {
                                valuable = new Amazonite();

                            } else if (Objects.equals(nextImageUrl3, "file:assets/underground/valuable_diamond.png")) {
                                valuable = new Diamond();

                            } else if (Objects.equals(nextImageUrl3, "file:assets/underground/valuable_goldium.png")) {
                                valuable = new Goldium();
                            }
                            if (valuable != null) {
                                int totalHaul = machine.getHaul();
                                machine.setHaul(totalHaul + valuable.getWeight());

                                int totalMoney = machine.getMoney();
                                machine.setMoney(totalMoney + valuable.getWorth());
                            }
                        }

                        if (getImageUrlAt(root, nextIndexOfX, nextIndexOfY) != null) {
                            Node currentNode = getNodeAt(root, nextIndexOfX, nextIndexOfY);
                            if (currentNode instanceof ImageView) {
                                root.getChildren().remove(currentNode);
                            }
                            Rectangle brown = new Rectangle(40, 40, Color.SADDLEBROWN);
                            root.getChildren().add(root.getChildren().indexOf(imageView), brown);
                            GridPane.setConstraints(brown, nextIndexOfX, nextIndexOfY);  // Brown rectangles were added to the cells dug by the drill machine.
                        }

                        moveX -= 40;
                        imageView.setImage(drillLeft);
                    }
                    break;

                case RIGHT:
                    if (currentIndexOfX != 19) {
                        nextIndexOfX = currentIndexOfX + 1;
                        nextIndexOfY = currentIndexOfY;

                        String nextImageUrl4 = getImageUrlAt(root, nextIndexOfX, nextIndexOfY);
                        if (Objects.equals(nextImageUrl4, "file:assets/underground/obstacle_01.png")) { // If there is an obstacle in the next cell, it cannot move.
                            imageView.setImage(drillRight);
                            break;

                        } else if (Objects.equals(nextImageUrl4, "file:assets/underground/lava_01.png")) { // If there is lava in the next cell, it triggers game over.
                            GameOverScreen gameOverScreen = new GameOverScreen();
                            gameOverScreen.gameOverRed(root);
                            machine.setGameOver(true);
                            break;

                        } else {
                            Valuable valuable = null;
                            if (Objects.equals(nextImageUrl4, "file:assets/underground/valuable_amazonite.png")) {
                                valuable = new Amazonite();

                            } else if (Objects.equals(nextImageUrl4, "file:assets/underground/valuable_diamond.png")) {
                                valuable = new Diamond();

                            } else if (Objects.equals(nextImageUrl4, "file:assets/underground/valuable_goldium.png")) {
                                valuable = new Goldium();
                            }
                            if (valuable != null) {
                                int totalHaul = machine.getHaul();
                                machine.setHaul(totalHaul + valuable.getWeight());

                                int totalMoney = machine.getMoney();
                                machine.setMoney(totalMoney + valuable.getWorth());
                            }
                        }

                        if (getImageUrlAt(root, nextIndexOfX, nextIndexOfY) != null && nextIndexOfY != 0) {
                            Node currentNode = getNodeAt(root, nextIndexOfX, nextIndexOfY);
                            if (currentNode instanceof ImageView) {
                                root.getChildren().remove(currentNode);
                            }
                            Rectangle brown = new Rectangle(40, 40, Color.SADDLEBROWN);
                            root.getChildren().add(root.getChildren().indexOf(imageView), brown);
                            GridPane.setConstraints(brown, nextIndexOfX, nextIndexOfY);  // Brown rectangles were added to the cells dug by the drill machine.
                        }

                        moveX += 40;
                        imageView.setImage(drillRight);
                    }
                    break;
            }

            text2.setText("haul: " + machine.getHaul());
            text3.setText("money: " + machine.getMoney());

            imageView.setTranslateX(imageView.getTranslateX() + moveX);
            imageView.setTranslateY(imageView.getTranslateY() + moveY);
            //The drill machine was moved forward.

            machine.decreaseFuel(root);  // The fuel was decreased with each movement.

            if (machine.getMoney() > 2000000) {  // If the total collected money exceeds a certain value, the game ends.
                GameOverScreen gameOverScreen = new GameOverScreen();
                gameOverScreen.gameOverGreen(root, machine);
                machine.setGameOver(true);
            }
        });

        Gravity gravity = new Gravity();
        gravity.gravity(scene, root, imageView, machine);

    }




}
