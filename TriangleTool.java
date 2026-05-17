/**
 * Represents a triangle drawing tool that extends TurtleDesigner
 * Draws an equilateral triangle using Turtle graphics
 *
 * @author Mahrukh Ansari
 */
public class TriangleTool extends TurtleDesigner {

    /**
     * Constructs a TriangleTool object according to
     * center position, size, and color
     *
     * @param centerX the x-coordinate of the center
     * @param centerY the y-coordinate of the center
     * @param size    the size of the triangle
     * @param color   the color of the triangle
     */
    public TriangleTool(double centerX, double centerY, double size, String color) {
        super(centerX, centerY, size, color);
    }

    /**
     * Draws the triangle shape using Turtle graphics.
     *
     * @param turtle the turtle used for drawing
     */
    @Override
    protected void drawShape(Turtle turtle) {

        double sideLength = getSize();

        double height = sideLength * Math.sqrt(3.0) / 2.0;

        placeTurtle(turtle, getX() - (sideLength / 2.0), getY() - (height / 3.0), 0);

        for (int i = 0; i < 3; i++) {
            turtle.forward(sideLength);
            turtle.left(120);
        }
    }

    /**
     * Returns a string consisting of a description of this SquareTool object.
     * 
     * @return a string containing the square's center, size, color of outline and
     *         fill color.
     */
    @Override
    public String toString() {
        return ("Triangle: " +
                "X-coordinate: " + getX()
                + ", Y-coordinate: " + getY()
                + ", Shape size: " + getSize()
                + ", Shape color: " + getColor());
    }
}
