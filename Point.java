/**
 * Point class represents a point in 2D space with x and y coordinates.
 */
public class Point {
    /**
     * The x and y coordinates of the point.
     */
    private double x;
    private double y;

    /**
     * Constructor for the Point class, which takes in the x and y coordinates of
     * the point.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate of the point.
     *
     * @return the x coordinate of the point
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y coordinate of the point.
     *
     * @return the y coordinate of the point
     */
    public double getY() {
        return y;
    }

    /**
     * {@inheritDoc}
     *
     * @return a single line of text with all of the values of the point
     */
    @Override
    public String toString() {
        return "X-coordinate of the point: " + x + ", Y-coordinate of the point: " + y;
    }
}
