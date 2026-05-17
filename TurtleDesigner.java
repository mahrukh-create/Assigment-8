/**
 * Shared base class for turtle shape tools.
 */
public abstract class TurtleDesigner {
    private final double centerX;
    private final double centerY;
    private final double size;
    private final String color;
    // private String selectedShape = "circle";

    private Turtle turtle;

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
    protected TurtleDesigner(double centerX, double centerY, double size, String color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.size = size;
        this.color = color;
        this.turtle = new Turtle();
        this.turtle.hide();
    }

    /**
     * Draws the shape using the turtle library and returns the Turtle instance used
     * for drawing.
     * 
     * @return the Turtle instance used for drawing
     */
    public final void draw() {
        this.turtle.speed(0);
        this.turtle.hide();
        this.turtle.up();
        this.turtle.setPosition(centerX, centerY);
        this.turtle.penColor(color);
        drawShape(this.turtle);
    }

    public void clear() {
        this.turtle.clear();
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
    protected final double getX() {
        return centerX;
    }

    /**
     * Gets the y-coordinate of the center of the shape.
     * 
     * @return the y-coordinate of the center
     */
    protected final double getY() {
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
     * {@inheritDoc}
     * 
     * @return a single line of text with all of the values of the shape
     */
    @Override
    public String toString() {
        return ("Shape: " +
                "X-coordinate: " + getX()
                + ", Y-coordinate: " + getY()
                + ", Shape size: " + getSize()
                + ", Shape color: " + getColor());
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
