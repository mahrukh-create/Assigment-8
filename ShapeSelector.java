import java.util.ArrayList;
import java.util.Scanner;

/**
 * * Simple global selector helper for choosing shapes with keys and placing
 * them
 * with mouse clicks.
 *
 * Press 1 = circle, 2 = square, 3 = triangle. Left-click to place the selected
 * shape.
 */
public class ShapeSelector {
    private static String selected = "circle";
    private static double defaultSize = 90;
    private static String color = "black";
    private static boolean creatingCustomShape = false;

    private static ArrayList<TurtleDesigner> shapes = new ArrayList<>();

    private static ArrayList<Point> customShapePoints = new ArrayList<>();
    private static ArrayList<CircleTool> tempPoints = new ArrayList<>();

    private static boolean awaitingInput = false;

    private static boolean canUndo = true;

    public static void reset() {
        canUndo = true;
    }

    public static void mousePressed(double canvasX, double canvasY) {
        if (creatingCustomShape) {
            customShapePoints.add(new Point(canvasX, canvasY));
            tempPoints.add(new CircleTool(canvasX, canvasY, 5, color));
            tempPoints.get(tempPoints.size() - 1).draw();
            System.out.println("Added point (" + canvasX + ", " + canvasY + ") to custom shape");
        } else {
            placeAtCanvas(canvasX, canvasY);
        }
    }

    /**
     * Handles key presses to change the selected shape. This method is called by
     * the Turtle class when keys are pressed.
     * 
     * @param keyText
     */
    public static void keyPressed(String keyText) {
        if (keyText == null)
            return;
        switch (keyText) {
            case "1":
                if (awaitingInput) {
                    getChoice(1);
                    awaitingInput = false;
                    return;
                }
                selected = "circle";
                System.out.println("ShapeSelector: selected circle");
                break;
            case "2":
                if (awaitingInput) {
                    getChoice(2);
                    awaitingInput = false;
                    return;
                }
                selected = "square";
                System.out.println("ShapeSelector: selected square");
                break;
            case "3":
                if (awaitingInput) {
                    getChoice(3);
                    awaitingInput = false;
                    return;
                }
                selected = "triangle";
                System.out.println("ShapeSelector: selected triangle");
                break;
            case "4":
                if (awaitingInput) {
                    getChoice(4);
                    awaitingInput = false;
                    return;
                }
                break;
            case "5":
                if (awaitingInput) {
                    getChoice(5);
                    awaitingInput = false;
                    return;
                }
                break;
            case "r":
                color = "red";
                break;
            case "b":
                color = "blue";
                break;
            case "g":
                color = "green";
                break;
            case "u":
                if (creatingCustomShape && canUndo) {
                    if (!customShapePoints.isEmpty()) {
                        Point removedPoint = customShapePoints.remove(customShapePoints.size() - 1);
                        System.out.println("Removed last point (" + removedPoint.getX() + ", " + removedPoint.getY()
                                + ") from custom shape");
                        tempPoints.get(tempPoints.size() - 1).clear();
                        tempPoints.remove(tempPoints.size() - 1);
                        canUndo = false;
                    } else {
                        System.out.println("No points to remove from custom shape");
                    }
                } else if (!shapes.isEmpty() && canUndo) {
                    shapes.get(shapes.size() - 1).clear();
                    shapes.remove(shapes.size() - 1);
                    canUndo = false;
                }
                break;

            case "equals": // up arrow
                defaultSize += 10;
                if (defaultSize > 100) {
                    defaultSize = 100;
                }
                System.out.println("ShapeSelector: increased size to " + defaultSize);
                break;
            case "minus": // down arrow
                defaultSize -= 10;
                if (defaultSize < 10) {
                    defaultSize = 10;
                }
                System.out.println("ShapeSelector: decreased size to " + defaultSize);
                break;
            case "c": // up arrow
                if (!awaitingInput) {
                    System.out.println("Editing Menu: ");
                    System.out.println("1. Change Size (10-100)");
                    System.out.println("2. Change Color");
                    System.out.println("3. Create custom shape");
                }
                awaitingInput = true;

                break;
            case "enter":
                if (creatingCustomShape) {

                    if (customShapePoints.size() >= 2) {
                        CustomShape customShape = new CustomShape(0, 0, 0, color, new ArrayList<>(customShapePoints));
                        shapes.add(customShape);
                        customShape.draw();
                    } else {
                        System.out.println("Custom shape requires at least 2 points. Shape creation cancelled.");
                    }

                    creatingCustomShape = false;
                    customShapePoints.clear();
                    for (int i = tempPoints.size(); i > 0; i--) {
                        tempPoints.get(i - 1).clear();
                        tempPoints.remove(i - 1);
                    }
                }
                break;
            case "escape":
                creatingCustomShape = false;
                customShapePoints.clear();
                break;
            default:
                break;
        }
    }

    public static void getChoice(int choice) {
        switch (choice) {
            case 1:
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter new size (10-100): ");
                double newSize = sc.nextDouble();
                if (newSize >= 10 && newSize <= 100) {
                    defaultSize = newSize;
                    System.out.println("Size changed to: " + defaultSize);
                } else {
                    System.out.println("Invalid size. Please enter a value between 10 and 100.");
                }
                break;
            case 2:
                Scanner sc2 = new Scanner(System.in);
                System.out.println("Current color: " + color);
                System.out.println("Available colors: red, blue, green");
                System.out.print("Enter new color: ");
                String newColor = sc2.nextLine();
                if ("red".equals(newColor) || "blue".equals(newColor) || "green".equals(newColor)) {
                    color = newColor;
                    System.out.println("Color changed to: " + color);
                } else {
                    System.out.println("Invalid color. Please enter a valid color.");
                }
                break;
            case 3:
                creatingCustomShape = true;
                break;
            case 4:
                // Clear everything from the screen and reset all variables
                for(int i = shapes.size(); i > 0; i--) {
                    shapes.get(i-1).clear();
                    shapes.remove(i-1);
                }
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid choice. Please enter 1, 2, 3, 4, or 5.");
        }
    }

    /**
     * Places the currently selected shape at the specified canvas coordinates. This
     * method is called by the App class when the mouse is clicked.
     * 
     * @param canvasX
     * @param canvasY
     */
    public static void placeAtCanvas(double canvasX, double canvasY) {
        try {
            switch (selected) {
                case "circle":
                    CircleTool c = new CircleTool(canvasX, canvasY, defaultSize, color);
                    c.draw();
                    shapes.add(c);
                    break;
                case "square":
                    SquareTool s = new SquareTool(canvasX, canvasY, defaultSize, color);
                    s.draw();
                    shapes.add(s);
                    break;
                case "triangle":
                    TriangleTool t = new TriangleTool(canvasX, canvasY, defaultSize, color);
                    t.draw();
                    shapes.add(t);
                    break;
                default:
                    break;
            }
        } catch (Throwable t) {
            System.out.println("ShapeSelector: failed to place shape: " + t.getMessage());
            t.printStackTrace();
        }
    }

    public static void getStatus() {
        if (awaitingInput) {
            System.out.println("Editing Menu: ");
            System.out.println("1. Change Size (10-100)");
            System.out.println("2. Change Color");
            System.out.println("3. Create custom shape");
            System.out.println("Awaiting input for editing...");
            return;
        }
        if (creatingCustomShape) {
            System.out.println("Creating custom shape: " + customShapePoints.size() + " points defined");
            return;
        }

        // show all keybinds
        System.out.println(
                "Keybinds:\nr = red, b = blue, g = green\nc = open menu, u = undo last point\n1 = circle, 2 = square, 3 = triangle\nEquals (=) = increase size, Minus (-) = decrease size\nLeft-click to place shape, Enter to finalize custom shape, Escape to cancel custom shape\n");

    }
}
