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
     * @param x
     * @param y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter methods for the x and y coordinates of the point.
     * @return the x coordinate of the point
     */
    public double getX() {
        return x;
    }
    /**
     * Getter method for the y coordinate of the point.
     * @return the y coordinate of the point
     */
    public double getY() {
        return y;
    }
}
