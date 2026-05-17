
/**
 * The ShapeSelector class is the controller for the app where it handles user inputs
 * to change the selected shape, size, and color, as well as to place shapes on the
 * canvas. It also manages the menu system for editing shape properties and saving/loading
 * creations. The ShapeSelector interacts with the TurtleDesigner subclasses to draw shapes
 * and with the FileIO class to manage saved creations.
 *
 * @author Joseph Jazwinski
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Controls how the user interacts with the app.
 */
public class ShapeSelector {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final double MIN_SIZE = 10;
    private static final double MAX_SIZE = 100;
    private static final String CANCEL_TOKEN = "cancel";
    private static final String[] MAIN_MENU_OPTIONS = {
            "Change Size (10-100)",
            "Change Color",
            "Create custom shape",
            "Save/Load/Delete Creations",
        "Cancel" };
    private static final String[] FILE_MENU_OPTIONS = {
            "Save current creation",
            "Load creation",
            "Delete creation",
        "Cancel" };
    private static final String[] KEYBIND_LINES = {
            "r = red, b = blue, g = green",
            "c = open menu, u = undo last point",
            "1 = circle, 2 = square, 3 = triangle",
            "Equals (=) = increase size, Minus (-) = decrease size",
            "Left-click to place shape, Enter to finalize custom shape, x to cancel custom shape",
            "Backspace = clear canvas, Escape = close app" };
    private static final String[] AVAILABLE_COLORS = { "red", "blue", "green" };

    private static String selected = "circle";
    private static double defaultSize = 90;
    private static String color = "black";
    private static boolean creatingCustomShape = false;
    private static boolean editingFiles = false;
    private static boolean loadingFile = false;

    /**
     * ArrayList to hold all shapes drawn on the screen.
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
     * Base contructor for ShapeSelector
     */
    public ShapeSelector() {
        getStatus();
    }

    /**
     * Resets the undo variable to allow for another undo action.
     */
    public static void reset() {
        canUndo = true;
    }

    /**
     * Opens the main editing menu.
     */
    private void openMainMenu() {
        editingFiles = false;
        awaitingInput = true;
        clearConsole();
        getStatus();
    }

    /**
     * Opens the file editing menu.
     */
    private void openFileMenu() {
        editingFiles = true;
        awaitingInput = true;
        clearConsole();
        getStatus();
    }

    /**
     * Closes any active menu state.
     */
    private void closeMenus() {
        editingFiles = false;
        loadingFile = false;
        awaitingInput = false;
    }

    /**
     * Cancels the current custom shape creation and removes any preview points.
     */
    private void cancelCustomShape() {
        creatingCustomShape = false;
        customShapePoints.clear();
        for (int i = tempPoints.size(); i > 0; i--) {
            tempPoints.get(i - 1).clear();
            tempPoints.remove(i - 1);
        }
    }

    /**
     * Clears all drawn shapes and any in-progress custom shape preview.
     */
    private void clearCanvas() {
        for (TurtleDesigner shape : shapes) {
            shape.clear();
        }
        shapes.clear();
        cancelCustomShape();
        canUndo = true;
    }

    /**
     * Prints a numbered menu from a shared option list.
     *
     * @param title           the menu title
     * @param awaitingMessage the message shown after the options
     * @param options         the menu options to display
     */
    private void printMenu(String title, String awaitingMessage, String[] options) {
        System.out.println(title);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.println(awaitingMessage);
    }

    /**
     * Prints the static keybind help text.
     */
    private void printKeybinds() {
        System.out.println("Keybinds:");
        for (String line : KEYBIND_LINES) {
            System.out.println(line);
        }
    }

    /**
     * Returns the first active menu key pressed by the user.
     *
     * @param keysDown  the current pressed key map
     * @param maxChoice the number of options in the current menu
     * @return the selected menu number or null if no menu key was pressed
     */
    private Integer getPressedChoice(HashMap<String, Boolean> keysDown, int maxChoice) {
        for (int i = 1; i <= maxChoice; i++) {
            if (keysDown.getOrDefault(Integer.toString(i), false)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Reads a trimmed line of input from the console.
     *
     * @param prompt the prompt to display
     * @return the user's input
     */
    private String promptText(String prompt) {
        System.out.print(prompt);
        String text = INPUT.nextLine().trim();
        if (CANCEL_TOKEN.equalsIgnoreCase(text)) {
            return null;
        }
        return text;
    }

    /**
     * Reads a bounded double from the console.
     *
     * @param prompt the prompt to display
     * @param min    the minimum allowed value
     * @param max    the maximum allowed value
     * @return the validated numeric value
     */
    private Double promptDouble(String prompt, double min, double max) {
        while (true) {
            try {
                String text = promptText(prompt + " (type 'cancel' to return): ");
                if (text == null) {
                    return null;
                }
                double value = Double.parseDouble(text);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Retry with the validation message below.
            }
            System.out.println("Please enter a value between " + min + " and " + max + ".");
        }
    }

    /**
     * Reads a bounded integer from the console.
     *
     * @param prompt the prompt to display
     * @param min    the minimum allowed value
     * @param max    the maximum allowed value
     * @return the validated integer value
     */
    private Integer promptInt(String prompt, int min, int max) {
        while (true) {
            try {
                String text = promptText(prompt + " (type 'cancel' to return): ");
                if (text == null) {
                    return null;
                }
                int value = Integer.parseInt(text);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Retry with the validation message below.
            }
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    /**
     * Reads and validates a color name.
     *
     * @param prompt the prompt to display
     * @return a supported color name
     */
    private String promptColor(String prompt) {
        while (true) {
            String text = promptText(prompt + " (type 'cancel' to return): ");
            if (text == null) {
                return null;
            }

            String newColor = text.toLowerCase();
            for (String allowedColor : AVAILABLE_COLORS) {
                if (allowedColor.equals(newColor)) {
                    return newColor;
                }
            }
            System.out.println("Please enter one of: red, blue, green.");
        }
    }

    /**
     * Returns the selected saved creation name.
     *
     * @param fileNames the available saved creations
     * @param prompt    the prompt to display for the selection
     * @return the selected creation name, or null if none are available
     */
    private String pickCreation(ArrayList<String> fileNames, String prompt) {
        if (fileNames.isEmpty()) {
            System.out.println("No saved creations found. Maybe change your working directory?");
            return null;
        }

        System.out.println("Saved creations:");
        for (int i = 0; i < fileNames.size(); i++) {
            System.out.println((i + 1) + ". " + fileNames.get(i));
        }

        Integer fileChoice = promptInt(prompt, 1, fileNames.size());
        if (fileChoice == null) {
            return null;
        }
        return fileNames.get(fileChoice - 1);
    }

    /**
     * Handles mouse clicks to place the currently selected shape at the clicked
     * canvas coordinates.
     * If the user is currently creating a custom shape, it will add points to the
     * custom shape instead of placing a shape.
     *
     * @param canvasX the canvas x coordinate
     * @param canvasY the canvas y coordinate
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
     * @param keysDown the pressed key state
     * @param keysUp   the released key state
     */
    public void checkKeys(HashMap<String, Boolean> keysDown, HashMap<String, Boolean> keysUp) {
        if (keysDown == null || keysUp == null) {
            return;
        }

        if (keysUp.getOrDefault("u", false)) {
            reset();
        }

        if (keysDown.getOrDefault("escape", false)) {
            System.exit(0);
        }

        if (keysDown.getOrDefault("backspace", false)) {
            clearCanvas();
            clearConsole();
            System.out.println("Canvas cleared.");
            getStatus();
            return;
        }

        if (awaitingInput) {
            int maxChoice = editingFiles ? FILE_MENU_OPTIONS.length : MAIN_MENU_OPTIONS.length;
            Integer choice = getPressedChoice(keysDown, maxChoice);
            if (choice != null) {
                clearConsole();
                getChoice(choice);
                clearConsole();
                getStatus();
            }
            return;
        }

        if (keysDown.getOrDefault("1", false) && !creatingCustomShape) {
            selected = "circle";
            clearConsole();
            getStatus();
        }
        if (keysDown.getOrDefault("2", false) && !creatingCustomShape) {
            selected = "square";
            clearConsole();
            getStatus();
        }
        if (keysDown.getOrDefault("3", false) && !creatingCustomShape) {
            selected = "triangle";
            clearConsole();
            getStatus();
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
            if (defaultSize > MAX_SIZE) {
                defaultSize = MAX_SIZE;
            }
            clearConsole();
            System.out.println("ShapeSelector: increased size to " + defaultSize);
        }
        if (keysDown.getOrDefault("minus", false)) {
            defaultSize -= 10;
            if (defaultSize < MIN_SIZE) {
                defaultSize = MIN_SIZE;
            }
            clearConsole();
            System.out.println("ShapeSelector: decreased size to " + defaultSize);
        }
        if (keysDown.getOrDefault("c", false)) {
            openMainMenu();
            return;
        }
        if (keysDown.getOrDefault("x", false)) {
            if (creatingCustomShape) {
                cancelCustomShape();
                clearConsole();
                System.out.println("Custom shape canceled.");
                getStatus();
                return;
            }
        }

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

                cancelCustomShape();
            }

            clearConsole();
            getStatus();
        }
    }

    /**
     * Handles the choices for the menu options.
     *
     * @param choice the selected menu option
     */
    public void getChoice(int choice) {
        if (editingFiles) {
            handleFileMenuChoice(choice);
            return;
        }

        handleMainMenuChoice(choice);
    }

    /**
     * Handles selections from the main editing menu.
     *
     * @param choice the selected menu option
     */
    private void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                Double newSize = promptDouble("Enter new size (10-100): ", MIN_SIZE, MAX_SIZE);
                if (newSize == null) {
                    System.out.println("Size change canceled.");
                    closeMenus();
                    return;
                }
                defaultSize = newSize;
                System.out.println("Size changed to: " + defaultSize);
                closeMenus();
                return;
            case 2:
                String newColor = promptColor("Enter new color: ");
                if (newColor == null) {
                    System.out.println("Color change canceled.");
                    closeMenus();
                    return;
                }
                color = newColor;
                System.out.println("Color changed to: " + color);
                closeMenus();
                return;
            case 3:
                creatingCustomShape = true;
                closeMenus();
                return;
            case 4:
                openFileMenu();
                return;
            case 5:
                closeMenus();
                return;
            default:
                System.out.println("Invalid choice. Please enter 1, 2, 3, 4, or 5.");
        }
    }

    /**
     * Handles selections from the file editing menu.
     *
     * @param choice the selected menu option
     */
    private void handleFileMenuChoice(int choice) {
        FileIO fileIO = new FileIO();

        switch (choice) {
            case 1:
                String creationName = promptText("Enter a name for the creation (eg: 'Creation 1') or type 'cancel': ");
                if (creationName == null) {
                    System.out.println("Save canceled.");
                    closeMenus();
                    return;
                }
                fileIO.saveCreation(creationName, shapes);
                closeMenus();
                return;
            case 2:
                loadingFile = true;
                ArrayList<String> fileNames = fileIO.getSavedCreations();
                String fileToLoad = pickCreation(fileNames, "Enter the number of the creation you want to load: ");
                if (fileToLoad == null) {
                    System.out.println("Load canceled.");
                    closeMenus();
                    return;
                }

                shapes = fileIO.loadCreation(fileToLoad);
                if (shapes.isEmpty()) {
                    System.out.println("Creation '" + fileToLoad + "' is empty or could not be loaded.");
                    closeMenus();
                    return;
                }

                for (TurtleDesigner shape : shapes) {
                    shape.draw();
                }
                loadingFile = false;
                closeMenus();
                System.out.println("Creation '" + fileToLoad + "' loaded successfully.");
                return;
            case 3:
                String fileToDelete = pickCreation(fileIO.getSavedCreations(),
                        "Enter the number of the creation you want to delete: ");
                if (fileToDelete == null) {
                    System.out.println("Delete canceled.");
                    closeMenus();
                    return;
                }

                if (fileIO.removeCreation(fileToDelete)) {
                    System.out.println("Creation '" + fileToDelete + "' deleted successfully.");
                } else {
                    System.out.println("Unable to delete creation '" + fileToDelete + "'.");
                }
                closeMenus();
                return;
            case 4:
                closeMenus();
                return;
            default:
                System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
        }
    }

    /**
     * Places the currently selected shape at the specified canvas coordinates. This
     * method is called by the App class when the mouse is clicked.
     *
     * @param canvasX the canvas x coordinate
     * @param canvasY the canvas y coordinate
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
     * Prints the status of the shape selector to the console.
     */
    public void getStatus() {
        if (loadingFile) {
            System.out.println("Loading creation...");
            return;
        }
        if (editingFiles) {
            printMenu("File Editing Menu:", "Awaiting input for file editing...", FILE_MENU_OPTIONS);
            return;
        }

        if (awaitingInput) {
            printMenu("Editing Menu:", "Awaiting input for editing...", MAIN_MENU_OPTIONS);
            return;
        }
        if (creatingCustomShape) {
            System.out.println("Creating custom shape: " + customShapePoints.size() + " points defined");
            System.out.println("Press Enter to finalize, x to cancel, or Escape to close the app.");
            return;
        }

        printKeybinds();
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
