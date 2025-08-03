package forth_phase.mini_projects.project_6;

import java.util.concurrent.RecursiveAction;

class MatrixMultiplierTask extends RecursiveAction {

    // Threshold for splitting tasks
    private static final int THRESHOLD = 100;
    private final double[][] A, B, C;
    private final int rowStart, rowEnd;

    public MatrixMultiplierTask(double[][] A, double[][] B, double[][] C, int rowStart, int rowEnd) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
    }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {

        // If the number of rows to process is below the threshold, perform direct multiplication
        if ((rowEnd - rowStart) < THRESHOLD) {

            // Directly multiply the specified rows of A with B and store in C
            // Sequential multiplication for smaller matrices / blocks
            for (int i = rowStart; i < rowEnd; i++) {

                // Iterate through each column of B
                for (int j = 0; j < B[0].length; j++) {

                    C[i][j] = 0; // Initialize the result cell

                    // Perform the dot product of the i-th row of A and j-th column of B
                    for (int k = 0; k < A[0].length; k++) {

                        // Multiply and accumulate the result
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }

        } else {
            // split task into two subtasks
            var mid = (rowStart + rowEnd) / 2;

            // Create two subtasks for the upper and lower halves of the matrix
            // Each subtask will handle a portion of the rows in parallel
            // This allows for better parallelism and efficiency in processing larger matrices
            var upperHalf = new MatrixMultiplierTask(A, B, C, rowStart, mid);
            var lowerHalf = new MatrixMultiplierTask(A, B, C, mid, rowEnd);

            invokeAll(upperHalf, lowerHalf); // Fork both subtasks for parallel execution
        }

    }
}
