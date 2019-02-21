package com.example.lib_algorithm.step1;

public class BinarySearch {

    public static int search(int[] array, int target, boolean useRecursion) {
        return useRecursion ? searchWithRecursion(array, target) : searchWithLoop(array, target);
    }

    public static int searchWithLoop(int[] array, int target) {
        if (array == null || array.length <= 0) return -1;
        int len = array.length;
        int left = 0;
        int right = len - 1;
        while(left <= right) {
            int mid = left + ((right - left) >> 1);
            if (array[mid] == target) {
                return mid;
            }
            else if (array[mid] > target) {
                right = mid - 1;
            }else {
                left = left + 1;
            }
        }
        return -1;
    }

    public static int searchWithRecursion(int[] array, int target) {
        return searchWithRecursion(array, target, 0, array.length - 1);
    }

    private static int searchWithRecursion(int[] array, int target, int left, int right) {
        int mid = left + ((right - left) >> 1);
        if (array[mid] == target) {
            return mid;
        }else if (array[mid] > target) {
            return searchWithRecursion(array, target, left, mid - 1);
        }else {
            return searchWithRecursion(array, target, mid + 1, right);
        }
    }

}
