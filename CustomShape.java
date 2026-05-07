import java.util.ArrayList;

public class CustomShape extends TurtleDesigner {

    /**
     * An array of points which define the custom shape.
     */
    private ArrayList<Point> points;

    /**
     * Constructor for the CustomShape class, which takes in the center coordinates,
     * size, color, and list of points that define the shape.
     * 
     * @param centerX
     * @param centerY
     * @param size
     * @param color
     * @param points
     */
    public CustomShape(double centerX, double centerY, double size, String color, ArrayList<Point> points) {
        super(centerX, centerY, size, color);
        this.points = points;
    }

    /**
     * Returns the list of points that define the custom shape.
     * 
     * @return
     */
    public ArrayList<Point> getPoints() {
        return this.points;
    }

    /**
     * Draws the custom shape by going to each point in the array by moing 
     * the turtle to the first point, then putting the pen down and
     * moving to each subsequent point in the array. 
     * If there are less than 2 points, the shape cannot be drawn and a message is printed to the console.
     */
    @Override
    protected void drawShape(Turtle turtle) {
        if (points.size() < 2) {
            System.out.println("Custom shape requires at least 2 points");
            return;
        }
        Point firstPoint = points.get(0);
        turtle.up();
        turtle.setPosition(firstPoint.getX(), firstPoint.getY());
        turtle.down();
        for (int i = 1; i < points.size(); i++) {
            Point p = points.get(i);
            turtle.setPosition(p.getX(), p.getY());
        }
    }

}
