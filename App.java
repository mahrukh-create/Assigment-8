public class App {
    public static void main(String[] args) {
        Turtle controller = new Turtle();
        controller.hide(); 

        boolean mouseWasDown = false;
        while (true) {
            if (Turtle.isKeyDown("1")) {
                ShapeSelector.keyPressed("1");
            }
            if (Turtle.isKeyDown("2")) {
                ShapeSelector.keyPressed("2");
            }
            if (Turtle.isKeyDown("3")) {
                ShapeSelector.keyPressed("3");
            }

            boolean mouseDown = Turtle.mouseButton1();
            if (mouseDown && !mouseWasDown) {
                ShapeSelector.placeAtCanvas(Turtle.canvasX(Turtle.mouseX()), Turtle.canvasY(Turtle.mouseY()));
            }
            mouseWasDown = mouseDown;
        }
    }
}
