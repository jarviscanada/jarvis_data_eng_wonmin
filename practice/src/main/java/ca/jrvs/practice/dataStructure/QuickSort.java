package ca.jrvs.practice.dataStructure;

public class QuickSort {

    /**
     * Sorts an integer array using quick sort
     *
     * @param arr array to be sorted
     * @param start starting index of array
     * @param end ending index of array
     */
    public void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int partitionIndex = partition(arr, start, end);

            quickSort(arr, start, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    public int partition(int[] arr, int start, int end) {
        //Pivot element
        int pivot = arr[end];
        int i = start-1;

        //Ensures that elements from start to i-1 are lesser than the pivot and sorts them
        for (int j = start; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                int swapTerm = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTerm;
            }
        }
        //Ensures that elements from i to j are greater than the pivot and sorts them
        int swapTerm = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTerm;

        return i++;
    }
}
