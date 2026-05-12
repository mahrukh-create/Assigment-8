/**
* Represents a triangle drawing tool that extends TurtleDesigner
* Draws an equilateral triangle along with an inner pattern.
*
* @author Mahrukh Ansari
*/

public class TriangleTool extends TurtleDesigner {

    /**
    * Constructs a TriangleTool object according to
    * center position, size, and color.
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
    * Draws the triangle shape using Turtle graphics
    * Also adds a smaller inner triangle pattern
    *
    * @param turtle the turtle used for drawing
    */
    @Override
    protected void drawShape(Turtle turtle) {
    
    double sideLength = getSize();
    
    double height =
    sideLength * Math.sqrt(3.0) / 2.0;
    
    // place turtle at starting position
    placeTurtle(
    turtle,
    getCenterX() - sideLength / 2.0,
    getCenterY() - height / 3.0,
    0
    );
    
    turtle.show();
    

    for (int i = 0; i < 3; i++) {
    turtle.forward(sideLength);
    turtle.left(120);
    }
    
    placeTurtle(
    turtle,
    getCenterX() - sideLength / 4.0,
    getCenterY() - height / 6.0,
    0
    );
    
    double innerSize = sideLength / 2.0;
    
    // draw inner triangle
    for (int i = 0; i < 3; i++) {
    turtle.forward(innerSize);
    turtle.left(120);
    }
    

    for (int i = 0; i < 18; i++) {
    turtle.forward(i * 2);
    turtle.left(20);
    }
    }
    }
