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
     * @param cwd
     */
    public FileIO(String cwd) {
        this.cwd = cwd;
    }

    /**
     * Updates the current working directory to the specified path
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
    public static void saveCreation(String name) {
    }

    /**
     * Loads a creation from a file and updates the app state accordingly
     * 
     * @param path the path to the creation to be loaded
     * @return
     */
    public static void loadCreation(String path) {
    }

    /**
     * Deletes a creation from the file system
     * 
     * @param path the path to the creation to be deleted
     * @return
     */
    public static void removeCreation(String path) {
    }
}