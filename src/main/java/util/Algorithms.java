package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Algorithms {


    /**
     * Sorts an array using the bubble sort algorithm.
     *
     * @param arr the array to sort.
     * @return the number of operations during the sorting.
     */
    public static long bubbleSort(int[] arr) {
        long cpt = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    ++cpt;
                    // Swap
                    int temp = arr[j];
                    ++cpt;
                    arr[j] = arr[j + 1];
                    ++cpt;
                    arr[j + 1] = temp;
                    ++cpt;
                }
            }
        }
        return cpt;
    }


    private static long merge(int[] arr, int[] l, int[] r, int left, int right) {
        long cpt = 0;
        int i = 0, j = 0, k = 0;
        cpt += 3;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                ++cpt;
                arr[k++] = l[i++];
            } else {
                arr[k++] = r[j++];
            }
            ++cpt;
        }
        while (i < left) {
            arr[k++] = l[i++];
            ++cpt;
        }
        while (j < right) {
            arr[k++] = r[j++];
            ++cpt;
        }
        return cpt;
    }


    /**
     * Sorts an array using the merge sort algorithm.
     *
     * @param arr  the array to sort.
     * @param size the size of the array to sort
     * @return the number of operations during the sorting.
     */
    public static long mergeSort(int[] arr, int size) {
        long cpt = 0;
        if (size < 2) {
            ++cpt;
            return cpt;
        }
        int mid = size / 2;
        ++cpt;
        int[] l = new int[mid];
        ++cpt;
        int[] r = new int[size - mid];
        ++cpt;

        for (int i = 0; i < mid; i++) {
            l[i] = arr[i];
            ++cpt;
        }
        for (int i = mid; i < size; i++) {
            r[i - mid] = arr[i];
            ++cpt;
        }
        cpt += mergeSort(l, mid);
        cpt += mergeSort(r, size - mid);
        cpt += merge(arr, l, r, mid, size - mid);
        return cpt;
    }


    public static List<int[]> generateArrays(long nb) {
        ArrayList<int[]> arrays = new ArrayList<>();
        for (int i = 10; i >= 0; i--) {
            int[] arr = IntStream.generate(() -> new Random().nextInt(100))
                    .limit(i * (nb / 10))
                    .toArray();
            arrays.add(arr);
        }
        return arrays;
    }


    public static long insertSort(int[] array) {
        long cpt = 0;
        int n = array.length;
        cpt++;
        for (int i = 1; i < n; ++i) {
            int key = array[i];
            cpt++;
            int j = i - 1;
            cpt++;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                cpt++;
                j = j - 1;
                cpt++;
            }
            array[j + 1] = key;
            cpt++;
        }
        return cpt;
    }

}
