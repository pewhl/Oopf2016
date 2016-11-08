package lab1;

import java.awt.*;

/**
 * Created by paula on 2016-11-08.
 */
public class SnakeModel extends GameModel{
    public enum Directions {
        EAST(1, 0),
        WEST(-1, 0),
        NORTH(0, -1),
        SOUTH(0, 1),
        NONE(0, 0);

        private final int xDelta;
        private final int yDelta;

        Directions(final int xDelta, final int yDelta) {
            this.xDelta = xDelta;
            this.yDelta = yDelta;
        }

        public int getXDelta() {
            return this.xDelta;
        }

        public int getYDelta() {
            return this.yDelta;
        }
    }

    /** Graphical representation of a coin. */
    private static final GameTile COIN_TILE = new RoundTile(new Color(255, 215,
            0),
            new Color(255, 255, 0), 2.0);
    /** Graphical representation of the collector */
    private static final GameTile SNAKEHEAD_TILE = new RoundTile(Color.BLACK,
            Color.GREEN, 2.0);
    /** Graphical representation of the collector */
    private static final GameTile SNAKEBODY_TILE = new RoundTile(Color.BLACK,
            Color.GREEN, 2.0);
    /** Graphical representation of the collector */
    private static final GameTile SNAKETAIL_TILE = new RoundTile(Color.BLACK,
            Color.GREEN, 2.0);

    /** Graphical representation of a blank tile. */
    private static final GameTile BLANK_TILE = new GameTile();

    /** The position of the collector. */
    private Position snakeHeadPos;

    /** The direction of the collector. */
    private SnakeModel.Directions direction = SnakeModel.Directions.NORTH;

    /** The number of coins found. */
    private int score;

    /**
     * Create a new model for the gold game.
     */
    public SnakeModel() {
        Dimension size = getGameboardSize();

        // Blank out the whole gameboard
        for (int i = 0; i < size.width; i++) {
            for (int j = 0; j < size.height; j++) {
                setGameboardState(i, j, BLANK_TILE);
            }
        }

        // Insert the collector in the middle of the gameboard.
        this.snakeHeadPos = new Position(size.width / 2, size.height / 2);
        setGameboardState(this.snakeHeadPos, SNAKEHEAD_TILE);
//Ändrat fram tills hit---------------------------------------------------
        // Insert coins into the gameboard.
        for (int i = 0; i < COIN_START_AMOUNT; i++) {
            addCoin();
        }
    }

}
