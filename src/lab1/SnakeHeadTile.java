package lab1;

import java.awt.*;

/**
 * A round tile with eyes, used as a head tile.
 * Painted in a specified are of the screen.
 *
 * Whenever the object should paint itself,
 * it is told what size and position it should use.
 */
public class SnakeHeadTile extends GameTile {

    /**
     * The color of the circle.
     */
    private final Color strokeColor;
    private final Color fillColor;
    private final Stroke stroke;
    private final double scale;

    /**
     * Creates a circular head.
     *
     * @param fillColor
     *            the color of the head.
     */
    SnakeHeadTile(Color fillColor) {
        this(fillColor, fillColor);
    }

    /**
     * Creates a circular head with a stroke around it.
     *
     * @param fillColor
     *            the color of the interior.
     * @param strokeColor
     *            the color of the stroke.
     */
    SnakeHeadTile(Color fillColor, Color strokeColor) {
        this(fillColor, strokeColor, 1.0);
    }

    /**
     * Creates a circular head with a stroke around it.
     *
     * @param fillColor
     *            the color of the interior.
     * @param strokeColor
     *            the color of the stroke.
     * @param thickness
     *            the thickness of the stroke.
     */
    SnakeHeadTile(Color fillColor, Color strokeColor, double thickness) {
        this(fillColor, strokeColor, thickness, 1.0);
    }

    /**
     * Creates a circular head with a stroke around it.
     *
     * @param fillColor
     *            the color of the interior.
     * @param strokeColor
     *            the color of the stroke.
     * @param thickness
     *            the thickness of the stroke.
     * @param scale
     *            size of the head relative to the tile size.
     */
    SnakeHeadTile(Color fillColor, Color strokeColor, double thickness, double scale) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.stroke = new BasicStroke((float) thickness);
        this.scale = scale;
    }

    /**
     * Draws itself in a given graphics context, position and size.
     *
     * @param g
     *            graphics context to draw on.
     * @param x
     *            pixel x coordinate of the tile to be drawn.
     * @param y
     *            pixel y coordinate of the tile to be drawn.
     * @param d
     *            size of this object in pixels.
     */
    @Override
    public void draw(final Graphics g, final int x, final int y, final Dimension d) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(this.fillColor);
        double xOffset = (d.width * (1.0 - this.scale)) / 2.0;
        double yOffset = (d.height * (1.0 - this.scale)) / 2.0;
        int posX = (int)(d.width - xOffset * 2);
        int posY = (int)(d.height- yOffset * 2);
        g2.fillOval((int) (x + xOffset), (int) (y + yOffset), posX, posY);
        g2.setStroke(this.stroke);
        g2.setColor(this.strokeColor);
        g2.drawOval((int) (x + xOffset), (int) (y + yOffset), posX, posY);
        g2.setColor(Color.BLACK);

        g2.fillOval((int) (x + xOffset-(posX/3)), (int) (y + yOffset + posY/2), (posX/3), (posY/3));
        g2.fillOval((int) (x + xOffset + posX), (int) (y + yOffset + posY/2), (posX/3), (posY/3));
    }
}
