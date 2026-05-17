import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileIO {
    private String cwd;

    /**
     * Creates FileIO Object autmatically setting cwd to the current directory
     */
    public FileIO() {
        this.cwd = System.getProperty("user.dir");
    }

    /**
     * Creates FileIO Object with the specified cwd
     * 
     * @param cwd
     */
    public FileIO(String cwd) {
        this.cwd = cwd;
    }

    /**
     * Updates the current working directory to the specified path
     * 
     * @param newCWD
     */
    public void updateCWD(String newCWD) {
        this.cwd = newCWD;
    }

    /**
     * Saves the current app state as a creation which can be loaded later on
     * 
     * @param name the name of the creation to be saved (should not include
     *             extension)
     * @return
     */
    public void saveCreation(String name, ArrayList<TurtleDesigner> shapes) {
        System.out.println("Saving creation '" + name + "'...");

        try (FileWriter fw = new FileWriter(cwd + "/" + name + ".creation")) {
            for (TurtleDesigner shape : shapes) {
                fw.write(shape.toString() + "\n");
            }
            fw.write("To Prevent corruption or loss of data, do not modify this file.");
            System.out.println("Creation '" + name + "' saved successfully.");
            return;
        } catch (IOException e) {
            System.out.println("An error occurred while saving the creation: " + e.getMessage());
            return;
        }
    }

    /**
     * Loads a creation from a file and updates the app state accordingly
     * 
     * @param path the path to the creation to be loaded
     * @return
     */
    public ArrayList<TurtleDesigner> loadCreation(String path) {
        ArrayList<TurtleDesigner> shapes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(resolveCreationPath(path)))) {
            Stream<String> lines = reader.lines();
            lines.forEach(line -> {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("To Prevent corruption or loss of data")) {
                    return;
                }

                String[] parts = trimmedLine.split(",");
                if (parts.length < 4) {
                    return;
                }

                String shapeType = extractShapeType(parts[0]);
                double x = extractValue(parts[0]);
                double y = extractValue(parts[1]);
                double size = extractValue(parts[2]);
                String color = extractTextValue(parts[3]);

                switch (shapeType) {
                    case "Circle":
                        shapes.add(new CircleTool(x, y, size, color));
                        break;
                    case "Square":
                        shapes.add(new SquareTool(x, y, size, color));
                        break;
                    case "Triangle":
                        shapes.add(new TriangleTool(x, y, size, color));
                        break;
                    default:
                        System.out.println("Unknown shape type: " + shapeType);
                }
            });
        } catch (IOException e) {
            System.out.println("An error occurred while loading the creation: " + e);
            return new ArrayList<>();
        }

        return shapes;
    }

    /**
     * Resolves a creation path relative to the configured working directory.
     *
     * @param path the file name or path provided by the caller
     * @return the absolute or cwd-resolved path to the creation file
     */
    private String resolveCreationPath(String path) {
        File file = new File(path);
        if (file.isAbsolute()) {
            return file.getPath();
        }
        return new File(cwd, path).getPath();
    }

    private String extractShapeType(String segment) {
        int colonIndex = segment.indexOf(':');
        if (colonIndex < 0) {
            return segment.trim();
        }
        return segment.substring(0, colonIndex).trim();
    }

    private double extractValue(String segment) {
        int colonIndex = segment.lastIndexOf(':');
        if (colonIndex < 0 || colonIndex == segment.length() - 1) {
            throw new IllegalArgumentException("Unable to parse numeric value from: " + segment);
        }
        return Double.parseDouble(segment.substring(colonIndex + 1).trim());
    }

    private String extractTextValue(String segment) {
        int colonIndex = segment.lastIndexOf(':');
        if (colonIndex < 0 || colonIndex == segment.length() - 1) {
            throw new IllegalArgumentException("Unable to parse text value from: " + segment);
        }
        return segment.substring(colonIndex + 1).trim();
    }

    /**
     * Deletes a creation from the file system
     * 
     * @param path the path to the creation to be deleted
     * @return true if the file was deleted, false otherwise
     */
    public boolean removeCreation(String path) {
        File file = new File(resolveCreationPath(path));
        if (!file.exists()) {
            return false;
        }
        return file.delete();
    }

    /**
     * Gets the saved creation file names in the configured working directory.
     *
     * @return the .creation file names available in cwd
     */
    public ArrayList<String> getSavedCreations() {
        File folder = new File(cwd);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> fileNames = new ArrayList<String>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".creation")) {
                    fileNames.add(file.getName());
                }
            }
        }
        return fileNames;
    }

}