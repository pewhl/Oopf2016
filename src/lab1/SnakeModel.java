package lab1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    /** A list containing the positions of all coins. */
    private final List<Position> coins = new ArrayList<Position>();

    private final List<Position> snake = new LinkedList<Position>();

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
        addCoin();

    }

    /**
     * Insert another coin into the gameboard.
     */
    private void addCoin() {
        Position newCoinPos;
        Dimension size = getGameboardSize();
        // Loop until a blank position is found and ...
        do {
            newCoinPos = new Position((int) (Math.random() * size.width),
                    (int) (Math.random() * size.height));
        } while (!isPositionEmpty(newCoinPos));

        // ... add a new coin to the empty tile.
        setGameboardState(newCoinPos, COIN_TILE);
        this.coins.add(newCoinPos);
    }

    /**
     * Return whether the specified position is empty.
     *
     * @param pos
     *            The position to test.
     * @return true if position is empty.
     */
    private boolean isPositionEmpty(final Position pos) {
        return (getGameboardState(pos) == BLANK_TILE);
    }

    private boolean isPositionSnake(final Position pos) { return (getGameboardState(pos) == SNAKEBODY_TILE || getGameboardState(pos) == SNAKETAIL_TILE);}

    /**
     * Update the direction of the collector
     * according to the user's keypress.
     */
    private void updateDirection(final int key) {
        if (key == KeyEvent.VK_LEFT && direction != Directions.EAST) {
            this.direction = Directions.WEST;
        } else if (key == KeyEvent.VK_UP && direction != Directions.SOUTH) {
            this.direction = Directions.NORTH;
        } else if (key == KeyEvent.VK_RIGHT && direction != Directions.WEST) {
            this.direction = Directions.EAST;
        } else if (key == KeyEvent.VK_DOWN && direction != Directions.NORTH) {
            this.direction = Directions.SOUTH;
        }
    }

    /**
     * Get next position of the collector.
     */
    private Position getNextsnakeHeadPos() {
        return new Position(
                this.snakeHeadPos.getX() + this.direction.getXDelta(),
                this.snakeHeadPos.getY() + this.direction.getYDelta());
    }

    /**
     * This method is called repeatedly so that the
     * game can update its state.
     *
     * @param lastKey
     *            The most recent keystroke.
     */
    @Override
    public void gameUpdate(final int lastKey) throws GameOverException {
        updateDirection(lastKey);

        // Erase the previous position.
        setGameboardState(this.snakeHeadPos, SNAKEBODY_TILE);
        // Change collector position.
        this.snakeHeadPos = getNextsnakeHeadPos();

        if (isOutOfBounds(this.snakeHeadPos)) {
            throw new GameOverException(this.score);
        }
        if (isPositionSnake(this.snakeHeadPos)) {
            throw new GameOverException(this.score);
        }

        // Draw collector at new position.
        setGameboardState(this.snakeHeadPos, SNAKEHEAD_TILE);

        // Remove the coin at the new collector position (if any)
        if (this.coins.remove(this.snakeHeadPos)) {
            this.score++;
        }


    }

    /**
     *
     * @param pos The position to test.
     * @return <code>false</code> if the position is outside the playing field, <code>true</code> otherwise.
     */
    private boolean isOutOfBounds(Position pos) {
        return pos.getX() < 0 || pos.getX() >= getGameboardSize().width
                || pos.getY() < 0 || pos.getY() >= getGameboardSize().height;
    }

}
