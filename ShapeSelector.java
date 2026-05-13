import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Controls how the user interacts with the app
 */
public class ShapeSelector {
    private static String selected = "circle";
    private static double defaultSize = 90;
    private static String color = "black";
    private static boolean creatingCustomShape = false;

    /**
     * ArrayList to hold all shapes drawn on the screen,
     */
    private static ArrayList<TurtleDesigner> shapes = new ArrayList<>();

    /**
     * ArrayList to hold the points for the custom shape being created.
     */
    private static ArrayList<Point> customShapePoints = new ArrayList<>();
    /**
     * ArrayList to hold the temporary CircleTool instances used to show the points
     * of the custom shape being created.
     */
    private static ArrayList<CircleTool> tempPoints = new ArrayList<>();

    /**
     * Variable to track whether the program is currently awaiting input for the
     * menu.
     */
    private static boolean awaitingInput = false;

    /**
     * Variable to track whether the user can undo or not.
     */
    private static boolean canUndo = true;

    /**
     * Resets the undo variable to allow for another undo action.
     */
    public static void reset() {
        canUndo = true;
    }

    /**
     * Handles mouse clicks to place the currently selected shape at the clicked
     * canvas coordinates.
     * If the user is currently creating a custom shape, it will add points to the
     * custom shape instead of placing a shape.
     * 
     * @param canvasX
     * @param canvasY
     */
    public void mousePressed(double canvasX, double canvasY) {
        if (creatingCustomShape) {
            customShapePoints.add(new Point(canvasX, canvasY));
            tempPoints.add(new CircleTool(canvasX, canvasY, 5, color));
            tempPoints.get(tempPoints.size() - 1).draw();
            clearConsole();
            getStatus();
            System.out.println("Added point (" + canvasX + ", " + canvasY + ") to custom shape");
        } else {
            placeAtCanvas(canvasX, canvasY);
        }
    }

    /**
     * Clears the console using ANSI escape codes.
     */
    public void clearConsole() {

        System.out.print("\033[2J\033[H");
        System.out.flush();
    }

    /**
     * Handles key presses to change the selected shape. This method is called by
     * the Turtle class when keys are pressed.
     * 
     * @param keyText
     */
    public void checkKeys(HashMap<String, Boolean> keysDown, HashMap<String, Boolean> keysUp) {
        if (keysDown == null || keysUp == null)
            return;

        if (keysUp.getOrDefault("u", false)) {
            reset();
        }

        // Switch statement to handle different key presses for shape selection, and
        // other actions
        if (keysDown.getOrDefault("1", false)) {
            if (creatingCustomShape)
                return;
            if (awaitingInput) {
                clearConsole();
                getChoice(1);
                awaitingInput = false;
                getStatus();
                return;
            }
            selected = "circle";
            clearConsole();
            getStatus();
        }
        if (keysDown.getOrDefault("2", false)) {
            if (creatingCustomShape)
                return;
            if (awaitingInput) {
                clearConsole();
                getChoice(2);
                awaitingInput = false;
                getStatus();
                return;
            }
            selected = "square";
            clearConsole();
            getStatus();
        }
        if (keysDown.getOrDefault("3", false)) {
            if (creatingCustomShape)
                return;
            if (awaitingInput) {
                clearConsole();
                awaitingInput = false;
                getChoice(3);
                return;
            }
            selected = "triangle";
            clearConsole();
            getStatus();
        }
        if (keysDown.getOrDefault("4", false)) {
            if (creatingCustomShape)
                return;
            if (awaitingInput) {
                clearConsole();
                getChoice(4);
                awaitingInput = false;
                return;
            }

        }
        if (keysDown.getOrDefault("5", false)) {

            if (awaitingInput) {
                clearConsole();
                getChoice(5);
                awaitingInput = false;
                return;
            }
        }
        if (keysDown.getOrDefault("r", false)) {
            color = "red";
        }
        if (keysDown.getOrDefault("b", false)) {
            color = "blue";
        }
        if (keysDown.getOrDefault("g", false)) {
            color = "green";
        }
        if (keysDown.getOrDefault("u", false)) {
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
        }

        if (keysDown.getOrDefault("equals", false)) {
            defaultSize += 10;
            if (defaultSize > 100) {
                defaultSize = 100;
            }
            clearConsole();
            System.out.println("ShapeSelector: increased size to " + defaultSize);

        } // up arrow
        if (keysDown.getOrDefault("minus", false)) {
            defaultSize -= 10;
            if (defaultSize < 10) {
                defaultSize = 10;
            }
            clearConsole();
            System.out.println("ShapeSelector: decreased size to " + defaultSize);

        } // down arrow
        if (keysDown.getOrDefault("c", false)) {
            if (!awaitingInput) {
                clearConsole();
                System.out.println("Editing Menu: ");
                System.out.println("1. Change Size (10-100)");
                System.out.println("2. Change Color");
                System.out.println("3. Create custom shape");
                System.out.println("Awaiting input for editing...");
                awaitingInput = true;
                return;
            }

            System.out.println("Awaiting input for editing...");
        } // up arrow
        if (keysDown.getOrDefault("enter", false)) {
            if (creatingCustomShape) {

                if (customShapePoints.size() >= 2) {
                    CustomShape customShape = new CustomShape(0, 0, 0, color, new ArrayList<>(customShapePoints));
                    shapes.add(customShape);
                    customShape.draw();
                    System.out.println(customShape);
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

            clearConsole();
            getStatus();
        }
        if (keysDown.getOrDefault("escape", false)) {
            creatingCustomShape = false;
            customShapePoints.clear();
        }

    }

    /**
     * Handles the choices for the menu options.
     * 
     * @param choice
     */
    public void getChoice(int choice) {
        switch (choice) {
            case 1:
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter new size (10-100): ");
                double newSize = sc.nextDouble();
                if (newSize >= 10 && newSize <= 100) {
                    defaultSize = newSize;
                    clearConsole();
                    System.out.println("Size changed to: " + defaultSize);
                    getStatus();
                } else {
                    clearConsole();
                    System.out.println("Invalid size. Please enter a value between 10 and 100.");
                    getChoice(choice);
                }
                break;
            case 2:
                Scanner sc2 = new Scanner(System.in);
                clearConsole();
                System.out.println("Current color: " + color);
                System.out.println("Available colors: red, blue, green");
                System.out.print("Enter new color: ");
                String newColor = sc2.nextLine();
                if ("red".equals(newColor) || "blue".equals(newColor) || "green".equals(newColor)) {
                    color = newColor;
                    clearConsole();
                    System.out.println("Color changed to: " + color);
                    getStatus();
                } else {
                    clearConsole();
                    System.out.println("Invalid color. Please enter a valid color.");
                    getChoice(choice);
                }
                break;
            case 3:
                clearConsole();
                creatingCustomShape = true;
                getStatus();
                break;
            case 4:
                // Clear everything from the screen and reset all variables
                for (int i = shapes.size(); i > 0; i--) {
                    shapes.get(i - 1).clear();
                    shapes.remove(i - 1);
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

    /**
     * Prints the status of the shape selector to the console
     * Changes whether the menu is open, whether the user is creating a custom
     * shape, and the keybinds for the app.
     */
    public void getStatus() {
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

    /**
     * {@inheritDoc}
     * 
     * @return a single line of text with all of the values of the shape selector
     */
    @Override
    public String toString() {
        return ("Selected shape: " + selected + ", Size: " + defaultSize + ", Color: " + color);
    }

}
