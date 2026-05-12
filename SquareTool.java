/**
 * Represents a square drawing tool that extends TurtleDesigner.
 * Draws squares centered around a given x,y coordinate using Turtle graphics.
 * 
 * @author Bruce Zamora
 */
public class SquareTool extends TurtleDesigner {
    /** Stores an optional fill color used to color the inside of the square. */
    private final String fillColor;

    /**
     * Creates a square without a fill color
     * 
     * @param centerX the X coordinate used as the square's center.
     * @param centerY the Y coordinate used as the square's center.
     * @param size    the length of the side of the square.
     * @param color   color used in the outline of the square.
     */
    public SquareTool(double centerX, double centerY, double size, String color) {
        super(centerX, centerY, size, color);
        this.fillColor = null;

    }

    /**
     * Creates a square with a fill color
     * 
     * @param centerX   the X coordinate used as the square's center.
     * @param centerY   the Y coordinate used as the square's center.
     * @param size      the length of the side of the square.
     * @param color     color used in the outline of the square.
     * @param fillColor the color used to fill the square
     */
    public SquareTool(double centerX, double centerY, double size, String color, String fillColor) {
        super(centerX, centerY, size, color);
        this.fillColor = fillColor;
    }

    /**
     * Returns the fill color of the square.
     * 
     * @return the fill color, or null if there was no fill color set.
     */
    public String getFillColor() {
        return this.fillColor;
    }

    /**
     * Draws centered square using a loop to create 4 sides by rotating the turtle
     * 90 degrees each iteration.
     * 
     * @param turtle the Turtle object used to draw the square.
     */
    @Override
    protected void drawShape(Turtle turtle) {
        double halfSize = getSize() / 2.0;
        placeTurtle(turtle, getCenterX() - halfSize, getCenterY() - halfSize, 0);

        for (int i = 0; i < 4; i++) {
            turtle.forward(getSize());
            turtle.left(90);
        }

        if (fillColor != null) {
            turtle.fillColor(fillColor);
        }
    }

    /**
     * Compares the current SquareTool to another SquareTool object to
     * determine whether they are equal. An equality can be achieved by having identical
     * size, center coordinates, colors, and fill colors.
     * 
     * @param obj the object being compared to this SquareTool object.
     * @return true if the objects being compared are equal or
     *         false otherwise.
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof SquareTool) {
            SquareTool otherSquare = (SquareTool) obj;

            boolean sameFillColor;

            if (this.getFillColor() == null) {
                sameFillColor = otherSquare.getFillColor() == null;
            } else {
                sameFillColor = this.getFillColor().equals(otherSquare.getFillColor());
            }

            return this.getCenterX() == (otherSquare.getCenterX()) && this.getCenterY() == (otherSquare.getCenterY())
                    && this.getSize() == otherSquare.getSize() && this.getColor().equals(otherSquare.getColor())
                    && sameFillColor;
        }
        return false;
    }

    /**
     * Returns a string consisting of a description of this SquareTool object.
     * 
     * @return a string containing the square's center, size, color of outline and
     *         fill color.
     */
    @Override
    public String toString() {
        return "The square is centered around: " + getCenterX() + ", " + getCenterY() + "." + " The size is: "
                + getSize()
                + "." + " The outline color is: " + getColor() + ". " + "The color used to fill this square is: "
                + getFillColor() + ".";
    }

}
