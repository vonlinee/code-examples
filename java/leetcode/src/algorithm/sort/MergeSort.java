package algorithm.sort;

public class MergeSort {
    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]，Second subarray is arr[m+1..r]
    void merge(int[] arr, int l, int m, int r) {
        // 要合并的数组的长度
        int n1 = m - l + 1;
        int n2 = r - m;
        // 创建临时数组
        int[] L = new int[n1];
        int[] R = new int[n2];

        // 复制两个子数组到临时数组
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
        int k = l; // 从[l, r]进行合并，起点为l
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // 剩下的元素直接[l, r]区间放到最后面
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // 递归调用
    // merge()
    public void sort(int[] arr, int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;
            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    /* A utility function to print array of size n */
    public static void printArray(int[] arr) {
        for (int j : arr) System.out.print(j + " ");
        System.out.println();
    }

    /**
     * 递归版本
     * @param arr
     * @param l
     * @param r
     */
    public void sortRecursively(int[] arr, int l, int r) {

    }

    // Driver code
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};

        System.out.println("Given Array");
        printArray(arr);

        MergeSort ob = new MergeSort();
        ob.sort(arr, 0, arr.length - 1);

        System.out.println("\nSorted array");
        printArray(arr);
    }
}