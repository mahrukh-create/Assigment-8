import java.util.HashMap;

/**
 * The entry point for this app. Initializes the ShapeSelector and TurtleController, and contains the 
 * main loop which checks for user inputs and updates the app state accordingly.
 * 
 * @author Joseph Jazwinski
 */


public class App {
    /**
     * Helper method to check if an array contains a specific key. Used for checking
     * which keys are currently pressed.
     * 
     * @param arr the array to check
     * @param key the key to look for
     * @return true if the array contains the key, false otherwise
     */
    public static boolean contains(String[] arr, String key) {
        for (String s : arr) {
            if (s.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check which keys are currently not pressed. Used for
     * checking
     * which keys were released in the current iteration of the main loop.
     * 
     * @param keys the array of currently pressed keys
     * @return a HashMap mapping each key to a boolean indicating whether it is currently
     */
    public static HashMap<String, Boolean> checkKeyUp(String[] keys) {
        String keysStr = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ0-=[]\\;',./`~!@#$%^&*()_+{}|:\"<>?";

        HashMap<String, Boolean> keysUp = new HashMap<>();

        for (Character key : keysStr.toCharArray()) {
            String keyStr = key.toString();
            if (!contains(keys, keyStr)) {
                keysUp.put(keyStr, true);
            } else {
                keysUp.put(keyStr, false);
            }
        }
        return keysUp;
    }

    /**
     * The main method initializes the ShapeSelector and TurtleController, and
     * contains the main loop which checks for user inputs
     * 
     * @param args the command-line arguments (not used in this app)
     */
    public static void main(String[] args) {
        ShapeSelector shapeSelector = new ShapeSelector();

        // keys dictionary for down and up
        HashMap<String, Boolean> keysDown = new HashMap<>();
        HashMap<String, Boolean> keysUp = new HashMap<>();

        // Initialize the shape selector and turtle controller
        Turtle controller = new Turtle();
        controller.hide();

        // varaible which tracks if the mouse was down in the previous iteration of the
        // loop
        boolean mouseWasDown = false;
        // Set to track which keys were down in the previous iteration
        HashMap<String, Boolean> keysWereDown = new HashMap<>();

        shapeSelector.clearConsole();
        shapeSelector.getStatus();

        // main loop which iterates every 50 milliseconds to check for
        // key presses and mouse clicks, and updates the shape
        // selector accordingly
        while (true) {
            // Gets an array of pressed keys from the Turtle class
            String[] keys = Turtle.keysDown();

            keysDown.clear();
            keysUp.clear();

            // Build a map of currently pressed keys and only process newly-pressed ones
            HashMap<String, Boolean> currentKeys = new HashMap<>();
            for (String key : keys) {
                currentKeys.put(key, true);
            }
            for (String key : currentKeys.keySet()) {
                if (!keysWereDown.containsKey(key)) {
                    keysDown.put(key, true);
                }
            }

            keysUp = checkKeyUp(keys);

            shapeSelector.checkKeys(keysDown, keysUp);

            // like the undo, the user must click for each shape placement,
            // they cannot hold down the mouse button to continuously place shapes
            boolean mouseDown = Turtle.mouseButton1();
            if (mouseDown && !mouseWasDown) {
                shapeSelector.mousePressed(Turtle.canvasX(Turtle.mouseX()), Turtle.canvasY(Turtle.mouseY()));
            }
            mouseWasDown = mouseDown;
            // Update keysWereDown for the next iteration
            keysWereDown.clear();
            for (String key : keys) {
                keysWereDown.put(key, true);
            }
        }
    }
}
