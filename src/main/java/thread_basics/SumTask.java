package thread_basics;

import java.util.concurrent.RecursiveTask;

class SumTask extends RecursiveTask<Integer> {

    private final int[] arr;
    private final int start, end;

    public SumTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Integer compute() {

        if ((end - start) <= 2) {
            var sum = 0;

            for (int i = start; i < end; i++) {
                sum += arr[i];
            }

            return sum;
        }

        var mid = (start + end) / 2;

        var left = new SumTask(arr, start, mid);
        var right = new SumTask(arr, mid, end);

        left.fork(); // Fork the left task

        // Compute the right task and join the left task
        return right.compute() + left.join();
    }

}

