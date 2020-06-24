package ca.jrvs.practice.dataStructure;

public class MergeSort {
    /**
     * Sorts an integer array using merge sort
     *
     * @param arr array to be sorted
     * @param n length of array
     */
    public static void mergeSort(int[] arr, int n) {
        //Base condition - Return when when the array length is 1 (smallest subdivision)
        if (n < 2) {
            return;
        }
        //Define mid point, assign new sub arrays based on the mid point
        int midPoint = n / 2;
        int[] leftArr = new int[midPoint];
        int[] rightArr = new int[n - midPoint];

        //Populating the arrays
        for (int i = 0; i < midPoint; i++) {
            leftArr[i] = arr[i];
        }
        for (int i = midPoint; i < n; i++) {
            rightArr[i - midPoint] = arr[i];
        }
        mergeSort(leftArr, midPoint);
        mergeSort(rightArr, n - midPoint);

        merge(arr, leftArr, rightArr, midPoint, n - midPoint);
    }

    /**
     * Compares each sub array and sorts them
     *
     * @param arr array to be sorted
     * @param leftArr left sub array
     * @param rightArr right sub array
     * @param start starting index of sub array
     * @param end ending index of sub array
     */
    public static void merge(int[] arr, int[] leftArr, int[] rightArr, int start, int end) {
        int i = 0, j = 0, k = 0;

        while(i < start && j < end) {
            //Comparing elements of both sub array and place smaller one in the input array
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            }
            else {
                arr[k++] = rightArr[j++];
            }
        }
        //When end of sub array is reached, rest of the element are copied into the input array
        while (i < start) {
            arr[k++] = leftArr[i++];
        }
        while (j < end) {
            arr[k++] = rightArr[j++];
        }
    }
}
