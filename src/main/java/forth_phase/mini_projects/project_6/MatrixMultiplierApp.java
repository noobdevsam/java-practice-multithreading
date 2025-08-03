package forth_phase.mini_projects.project_6;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MatrixMultiplierApp {

    public static void main(String[] args) {
        var size = 1000; // Size or dimension of the matrices

        var A = generateMatrix(size); // Initialize matrix A
        var B = generateMatrix(size); // Initialize matrix B

        // Initialize the result matrix C
        // C = A * B
        var C = new double[size][size];


        try (var pool = new ForkJoinPool()) { // Create a ForkJoinPool to manage parallel tasks
            var start = System.currentTimeMillis();

            // Submit the matrix multiplication task to the pool
            // The task will compute the product of matrices A and B, storing the result in C
            // The task is defined in the MatrixMultiplierTask class
            // The task is executed in parallel, utilizing multiple threads for efficiency
            pool.invoke(
                    new MatrixMultiplierTask(A, B, C, 0, size)
            );

            var end = System.currentTimeMillis();
            System.out.println("Time taken: " + (end - start) + " ms");
        }

    }

    private static double[][] generateMatrix(int size) {

        var random = new Random();
        var mat = new double[size][size];

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {
                mat[i][j] = random.nextDouble();
            }

        }

        return mat;

    }

}
