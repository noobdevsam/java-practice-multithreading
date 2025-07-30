package third_phase.mini_projects.library_access_simulation;

public class LibraryTest {

    public static void main(String[] args) {
        // Create an instance of the Library class to simulate library access.
        var library = new Library();

        // Loop to create and start threads for multiple students attempting to access the library.
        for (int i = 1; i < 7; i++) {
            // Generate a unique name for each student.
            final String studentName = "Student_" + i;

            // Create a new thread for each student and start it.
            // Each thread invokes the enterLibrary method of the Library class.
            new Thread(() -> library.enterLibrary(studentName)).start();
        }
    }

}
