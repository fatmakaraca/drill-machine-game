/**
 * The Valuable class represents valuable items that the machine can collect in the game.
 * Each valuable item has a weight and a worth.
 */
public class Valuable {
    private final int weight;
    private final int worth;

    /**
     * Constructs a new Valuable object with the specified weight and worth.
     *
     * @param weight The weight of the valuable item.
     * @param worth  The worth of the valuable item.
     */
    public Valuable(int weight, int worth) {
        this.weight = weight;
        this.worth = worth;
    }

    /**
     * Retrieves the weight of the valuable item.
     *
     * @return The weight of the valuable item.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Retrieves the worth of the valuable item.
     *
     * @return The worth of the valuable item.
     */
    public int getWorth() {
        return worth;
    }
}
