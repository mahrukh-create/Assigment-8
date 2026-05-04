/**
 * Shared base class for turtle shape tools.
 */
public abstract class TurtleDesigner {
    private final double centerX;
    private final double centerY;
    private final double size;
    private final String color;
    private final double lineWidth;
    private String selectedShape = "circle";

    /**
     * Constructs a new TurtleDesigner with the specified parameters.
     *
     * @param centerX the x-coordinate of the center of the shape
     * @param centerY the y-coordinate of the center of the shape
     * @param size    the size of the shape
     * @param color   the color of the shape
     */
    protected TurtleDesigner(double centerX, double centerY, double size, String color) {
        this(centerX, centerY, size, color, 3.0);
    }

    /**
     * Constructs a new TurtleDesigner with the specified parameters, including line
     * width.
     * 
     * @param centerX   the x-coordinate of the center of the shape
     * @param centerY   the y-coordinate of the center of the shape
     * @param size      the size of the shape
     * @param color     the color of the shape
     * @param lineWidth the width of the lines used to draw the shape
     */
    protected TurtleDesigner(double centerX, double centerY, double size, String color, double lineWidth) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.size = size;
        this.color = color;
        this.lineWidth = lineWidth;
    }

    /**
     * Key listener method to handle key events. Subclasses can override this method to
     * implement specific key handling behavior for their shapes. By default, this method 
     * checks for the number keys (1, 2, 3) to change the shape from circle, square, or triangle. Subclasses can extend this
     * 
     */
    public void handleKeyEvent(int keyCode) {
        if (keyCode == '1') {
            // Change to circle
        } else if (keyCode == '2') {
            // Change to square
        } else if (keyCode == '3') {
            // Change to triangle
        }
    }

    /**
     * Draws the shape using the turtle library and returns the Turtle instance used
     * for drawing.
     * 
     * @return the Turtle instance used for drawing
     */
    public final Turtle draw() {
        Turtle turtle = new Turtle();
        turtle.speed(0);
        turtle.hide();
        turtle.up();
        turtle.setPosition(centerX, centerY);
        turtle.penColor(color);
        turtle.width(lineWidth);
        drawShape(turtle);
        return turtle;
    }

    /**
     * Draws the specific shape using the provided Turtle instance. Subclasses must
     * implement this method to define how the shape is drawn.
     * 
     * @param turtle the Turtle instance to use for drawing the shape
     */
    protected abstract void drawShape(Turtle turtle);

    /**
     * Gets the x-coordinate of the center of the shape.
     * 
     * @return the x-coordinate of the center
     */
    protected final double getCenterX() {
        return centerX;
    }

    /**
     * Gets the y-coordinate of the center of the shape.
     * 
     * @return the y-coordinate of the center
     */
    protected final double getCenterY() {
        return centerY;
    }

    /**
     * Gets the size of the shape.
     * 
     * @return
     */
    protected final double getSize() {
        return size;
    }

    /**
     * Gets the color of the shape.
     * 
     * @return the color of the shape
     */
    protected final String getColor() {
        return color;
    }

    /**
     * Gets the line width used for drawing the shape.
     * 
     * @return the line width
     */
    protected final double getLineWidth() {
        return lineWidth;
    }

    /**
     * Utility method to position the turtle at a specific location and direction
     * before
     * drawing. This method lifts the pen, moves the turtle to the specified
     * coordinates,
     * sets the direction, and then lowers the pen to prepare for drawing.
     * 
     * @param turtle
     * @param x
     * @param y
     * @param direction
     */
    protected final void placeTurtle(Turtle turtle, double x, double y, double direction) {
        turtle.up();
        turtle.setPosition(x, y, direction);
        turtle.down();
    }

    /**
     * Utility method to draw a circle with the turtle library. This method
     * calculates the number of steps needed to create a smooth circle based on the
     * radius, and then moves the turtle in small increments to approximate the
     * 
     * @param turtle
     * @param radius
     */
    protected final void drawCircle(Turtle turtle, double radius) {
        int steps = Math.max(60, (int) Math.ceil(radius * 8));
        double stepLength = (2.0 * Math.PI * radius) / steps;
        placeTurtle(turtle, centerX - radius, centerY, 90);
        for (int i = 0; i < steps; i++) {
            turtle.forward(stepLength);
            turtle.right(360.0 / steps);
        }
    }
}