import java.util.HashMap;

public class App {
    public static boolean contains(String[] arr, String key) {
        for (String s : arr) {
            if (s.equals(key)) {
                return true;
            }
        }
        return false;
    }

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

            // looping over the kes in the arraylist fom turtle
            // sending each pressed key to the shape selector to handle
            for (String key : keys) {
                keysDown.put(key, true);
                System.out.println(key);
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
        }
    }
}
