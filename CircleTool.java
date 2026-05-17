/**
 * Draws a circle
 * 
 * @author Amy Mathew
 */

public class CircleTool extends TurtleDesigner {
    /**
     * Constructs a circle with no fill color
     * 
     * @param centerX the x-coordinate of the center of the circle
     * @param centerY the y-coordinate of the center of the circle
     * @param size    the size of the shape
     * @param color   the color of the shape
     */

    public CircleTool(double centerX, double centerY, double size, String color) {
        super(centerX, centerY, size, color);
    }

    /**
     * {@inheritDoc}
     * 
     * @return returns true if both objects' attributes have the save values
     */
    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        if (other instanceof CircleTool) {
            CircleTool otherCircle = (CircleTool) other;
            return (otherCircle.getX() == this.getX()
                    && otherCircle.getY() == this.getY()
                    && otherCircle.getSize() == this.getSize()
                    && otherCircle.getColor().equals(this.getColor()));
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @return a single line of text with all of the values of the circle
     */
    @Override
    public String toString() {
        return ("Circle: " +
                "X-coordinate: " + getX()
                + ", Y-coordinate: " + getY()
                + ", Shape size: " + getSize()
                + ", Shape color: " + getColor());
    }

    /**
     * Draws a circle
     * 
     * @param turtle the Turtle instance to use for drawing the circle
     */
    @Override
    protected final void drawShape(Turtle turtle) {
        double radius = getSize() / 2;
        // Calculate number of steps needed to create smooth circle based on radius
        int steps = Math.max(60, (int) Math.ceil(radius * 8));
        double stepLength = (2.0 * Math.PI * radius) / steps;
        placeTurtle(turtle, getX() - radius, getY(), 90);
        for (int i = 0; i < steps; i++) {
            turtle.forward(stepLength);
            turtle.right(360.0 / steps);
        }
    }
}
