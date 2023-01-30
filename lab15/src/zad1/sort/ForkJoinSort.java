package zad1.sort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinSort extends RecursiveAction {
    private static final long serialVersionUID = 1L;
    private int[] arr;
    private int start, end;

    public ForkJoinSort(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        // If the length of the subarray is less than or equal to 2, sort it
        if (end - start <= 2) {
            Arrays.sort(arr, start, end);
            System.out.println("Sorting subarray [" + start + ", " + end + "]: " + Arrays.toString(Arrays.copyOfRange(arr, start, end)));
        } else {
            // Split the array into two halves
            int mid = (start + end) / 2;
            System.out.println("Splitting array to [" + start + ", " + mid + "]: " + Arrays.toString(Arrays.copyOfRange(arr, start, mid)));
            System.out.println("Splitting array to [" + mid + ", " + end + "]: " + Arrays.toString(Arrays.copyOfRange(arr, mid, end)));
            ForkJoinSort left = new ForkJoinSort(arr, start, mid);
            ForkJoinSort right = new ForkJoinSort(arr, mid, end);

            // Start the left task in a separate thread and continue with the right task in the current thread
            right.fork();
            left.compute();
            right.join();

            // Merge the results of the two tasks
            int[] temp = new int[end - start];
            int i = start, j = mid, k = 0;
            while (i < mid && j < end) {
                if (arr[i] < arr[j]) {
                    temp[k++] = arr[i++];
                } else {
                    temp[k++] = arr[j++];
                }
            }
            while (i < mid) {
                temp[k++] = arr[i++];
            }
            while (j < end) {
                temp[k++] = arr[j++];
            }
            System.arraycopy(temp, 0, arr, start, end - start);
            System.out.println("Merging subarrays [" + start + ", " + mid + "] and [" + mid + ", " + end + "]: " + Arrays.toString(Arrays.copyOfRange(arr, start, end)));
        }
    }

    public static void sort(int[] arr) {
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ForkJoinSort(arr, 0, arr.length));
        pool.shutdown();
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        System.out.println("Original array: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
