public class App {
    public static void main(String[] args) {
        // Initialize the shape selector and turtle controller
        Turtle controller = new Turtle();
        controller.hide();

        // varaible which tracks if the mouse was down in the previous iteration of the
        // loop
        boolean mouseWasDown = false;

        // main loop which iterates every 50 milliseconds to check for
        // key presses and mouse clicks, and updates the shape
        // selector accordingly
        while (true) {
            // Clear the console and print the status of the shape selector
            ShapeSelector.getStatus();
            System.out.flush();
            System.out.print("\033[H\033[2J");

            // Gets an array of pressed keys from the Turtle class
            String[] keys = Turtle.keysDown();

            // boolean for tracking whether or not 'u' is pressed
            // used for undo functionality
            boolean uPressed = false;

            // looping over the kes in the arraylist fom turtle
            // sending each pressed key to the shape selector to handle
            for (String key : keys) {
                ShapeSelector.keyPressed(key);
                if (key.equals("u")) {
                    uPressed = true;
                }
            }
            // resets the undo variable to allow for multiple uses
            // Essentially, the user cannot hold down 'u' to continuously undo, 
            // they must press it once for each undo action
            // Since the program runs on a loop every millisecond, a short press may
            // result in many undo's
            if (!uPressed) {
                ShapeSelector.reset();
            }
        
            // like the undo, the user must click for each shape placement, 
            // they cannot hold down the mouse button to continuously place shapes
            boolean mouseDown = Turtle.mouseButton1();
            if (mouseDown && !mouseWasDown) {
                ShapeSelector.mousePressed(Turtle.canvasX(Turtle.mouseX()), Turtle.canvasY(Turtle.mouseY()));
            }
            mouseWasDown = mouseDown;
        }
    }
}
