/**
 * Simple global selector helper for choosing shapes with keys and placing them with mouse clicks.
 *
 * Press 1 = circle, 2 = square, 3 = triangle. Left-click to place the selected shape.
 */
public class ShapeSelector {
    private static String selected = "circle";
    private static double defaultSize = 90;

    public static void keyPressed(String keyText) {
        if (keyText == null)
            return;
        switch (keyText) {
            case "1":
                selected = "circle";
                System.out.println("ShapeSelector: selected circle");
                break;
            case "2":
                selected = "square";
                System.out.println("ShapeSelector: selected square");
                break;
            case "3":
                selected = "triangle";
                System.out.println("ShapeSelector: selected triangle");
                break;
            default:
                break;
        }
    }

    public static void placeAtCanvas(double canvasX, double canvasY) {
        try {
            switch (selected) {
                case "circle":
                    // new CircleTool(canvasX, canvasY, defaultSize, "green").draw();
                    break;
                case "square":
                    // new SquareTool(canvasX, canvasY, defaultSize, "blue").draw();
                    break;
                case "triangle":
                    // new TriangleTool(canvasX, canvasY, defaultSize, "orange").draw();
                    break;
                default:
                    break;
            }
        } catch (Throwable t) {
            System.out.println("ShapeSelector: failed to place shape: " + t.getMessage());
            t.printStackTrace();
        }
    }
}
