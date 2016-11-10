package lab1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A snake game. The snake length increases as it feeds.
 * The game is over when the snake collides with itself or the game border.
 * The player gains one point per apple that is eaten by the snake.
 *
 * A new apple appears randomly on the board when the previous is eaten.
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

    /** Graphical representation of an apple */
    private static final GameTile APPLE_TILE = new RoundTile(Color.GREEN,
            Color.RED, 2.0);
    /** Graphical representation of the snake head */
    private static final GameTile SNAKEHEAD_TILE = new SnakeHeadTile(Color.GREEN, Color.BLACK,
            1.0, 1.0);
    /** Graphical representation of the snake body */
    private static final GameTile SNAKEBODY_TILE = new RectangularTile(Color.BLACK);
    /** Graphical representation of the snake tail */
    private static final GameTile SNAKETAIL_TILE = new RectangularTile(Color.MAGENTA);

    /** Graphical representation of a blank tile. */
    private static final GameTile BLANK_TILE = new GameTile();

    /** A list containing the positions of all coins. */
    private final List<Position> coins = new ArrayList<Position>();

    /** A list containing the positions of all snake parts. */
    private final LinkedList<Position> snake = new LinkedList<Position>();

    /** The position of the snake head. */
    private Position snakeHeadPos;

    /** The direction of the snake head. */
    private SnakeModel.Directions direction = SnakeModel.Directions.NORTH;

    /** The number of apples eaten. */
    private int score;

    /**
     * Create a new model for the snake game.
     */
    public SnakeModel() {
        Dimension size = getGameboardSize();

        // Blank out the whole gameboard
        for (int i = 0; i < size.width; i++) {
            for (int j = 0; j < size.height; j++) {
                setGameboardState(i, j, BLANK_TILE);
            }
        }

        // Insert the snake head in the middle of the gameboard.
        this.snakeHeadPos = new Position(size.width / 2, size.height / 2);
        snake.addFirst(snakeHeadPos);
        setGameboardState(this.snakeHeadPos, SNAKEHEAD_TILE);
        // Insert an apple into the gameboard.
        addApple();

    }

    /**
     * Insert another apple into the gameboard.
     */
    private void addApple() {
        Position newApplePos;
        Dimension size = getGameboardSize();
        // Loop until a blank position is found and ...
        do {
            newApplePos = new Position((int) (Math.random() * size.width),
                    (int) (Math.random() * size.height));
        } while (!isPositionEmpty(newApplePos));

        // ... add a new apple to the empty tile.
        setGameboardState(newApplePos, APPLE_TILE);
        this.coins.add(newApplePos);
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

    /**
     * Return whether the specified position is part of the snake body.
     * @param pos
     *            The position to test.
     * @return true if the position is part of the snake.
     */
    private boolean isPositionSnake(final Position pos) { return (getGameboardState(pos) == SNAKEBODY_TILE);}

    /**
     * Update the direction of the snake head
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
     * Get next position of the snake head.
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

        // Change the previous snake head to a body part.
        setGameboardState(this.snakeHeadPos, SNAKEBODY_TILE);

        // Change snake head position.
        this.snakeHeadPos = getNextsnakeHeadPos();

        // Save the position in the list of snake parts.
        snake.addFirst(snakeHeadPos);

        // Check if the position is outside the board or on the snake.
        if (isOutOfBounds(this.snakeHeadPos) || isPositionSnake(this.snakeHeadPos)) {
            throw new GameOverException(this.score);
        }

        // Draw the snake head at new position.
        setGameboardState(this.snakeHeadPos, SNAKEHEAD_TILE);

        // Remove the apple at the new snake head position (if any), then add a new apple.
        // Else, remove the last snake part.
        if (this.coins.remove(this.snakeHeadPos)) {
            this.score++;
            addApple();
        } else {
            setGameboardState(snake.pollLast(), BLANK_TILE);
        }

        // Set the last body part to a tail, (if it has a body).
        if (snake.size() > 1) {
            setGameboardState(snake.getLast(), SNAKETAIL_TILE);
        }
    }

    /**
     * Test if the snake head is outside the game board.
     *
     * @param pos The position to test.
     * @return <code>false</code> if the position is outside the playing field, <code>true</code> otherwise.
     */
    private boolean isOutOfBounds(Position pos) {
        return pos.getX() < 0 || pos.getX() >= getGameboardSize().width
                || pos.getY() < 0 || pos.getY() >= getGameboardSize().height;
    }

}
