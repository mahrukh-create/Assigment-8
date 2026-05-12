/**
 * Draws a circle
 * @author Amy Mathew
 */

public class CircleTool extends TurtleDesigner {
    /**
     * The color filling the circle
     */
    private final String fillColor;

    /**
     * Constructs a circle with no fill color
     * @param centerX the x-coordinate of the center of the circle
     * @param centerY  the y-coordinate of the center of the circle
     * @param size the size of the shape
     * @param color the color of the shape
     */

    public CircleTool (double centerX, double centerY, double size, String color) {
        super(centerX, centerY, size, color);
        this.fillColor = null;
    }
    
    /**
     * Constructs a circle with the specified fill color
     * @param centerX the x-coordinate of the center of the circle
     * @param centerY  the y-coordinate of the center of the circle
     * @param size the size of the shape
     * @param color the color of the shape
     * @param fillColor the color to fill the shape with
     */
    public CircleTool (double centerX, double centerY, double size, String color, String fillColor) {
        super(centerX, centerY, size, color);
        this.fillColor = fillColor;
    }

    
    
    /**
     * {@inheritDoc}
     * @return returns true if both objects' attributes have the save values
     */
    @Override
    public boolean equals (Object other) {
        if (!super.equals(other)) {
            return false;
        }
        if (other instanceof CircleTool) {
            CircleTool otherCircle = (CircleTool) other;
            return (
                otherCircle.fillColor.equals(this.fillColor)
            );
        }
        return false;
    }
    /**
     * {@inheritDoc}
     * @return a single line of text with all of the values of the circle
     */
    @Override
    public String toString() {
        return (
            "X-coordinate of the center: " + getCenterX()
            + ", Y-coordinate of the center: " + getCenterY()
            + ", Shape size: " + getSize()
            + ", Shape color: " + getColor()
            + ", Line width: " + getLineWidth()
            + ", Fill color: " + fillColor
        );
    }
    /**
     * Draws a circle 
     * @param turtle the Turtle instance to use for drawing the circle
     */
    @Override
    protected final void drawShape (Turtle turtle) {
        double radius = getSize()/2;
        // Calculate number of steps needed to create smooth circle based on radius
        int steps = Math.max(60, (int) Math.ceil(radius * 8));
        double stepLength = (2.0 * Math.PI * radius) / steps;
        placeTurtle(turtle, getCenterX() - radius, getCenterY(), 90);
        for (int i = 0; i < steps; i++) {
            turtle.forward(stepLength);
            turtle.right(360.0 / steps);
        }
        if (fillColor != null) {
            turtle.fillColor(fillColor);
        }
    }


}

