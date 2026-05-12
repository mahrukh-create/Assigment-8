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
    * @param size the size of the triangle
    * @param color the color of the triangle
    */
    public TriangleTool(double centerX, double centerY,
    double size, String color) {

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

    double height =
    sideLength * Math.sqrt(3.0) / 2.0;

    placeTurtle(
    turtle,
    getCenterX() - (sideLength / 2.0),
    getCenterY() - (height / 3.0),
    0
    );
   
    turtle.show();

    for (int i = 0; i < 3; i++) {
    turtle.forward(sideLength);
    turtle.left(120);
    }
    }

    }
