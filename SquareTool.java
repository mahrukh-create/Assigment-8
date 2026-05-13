/**
 * Represents a square drawing tool that extends TurtleDesigner.
 * Draws squares centered around a given x,y coordinate using Turtle graphics.
 * 
 * @author Bruce Zamora
 */
public class SquareTool extends TurtleDesigner {
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
        placeTurtle(turtle, getX() - halfSize, getY() - halfSize, 0);

        for (int i = 0; i < 4; i++) {
            turtle.forward(getSize());
            turtle.left(90);
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


            return getX() == (otherSquare.getX()) && getY() == (otherSquare.getY())
                    && getSize() == otherSquare.getSize() && getColor().equals(otherSquare.getColor());
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
        return "The square is placed at: " + getX() + ", " + getY() + "." + " The size is: "
                + getSize()
                + "." + " The outline color is: " + getColor() + ". ";
    }

}
