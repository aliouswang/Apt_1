package com.example.lib_algorithm.step1;

public class BinarySearch {

    public static int search(int[] array, int target, boolean useRecursion) {
        return useRecursion ? searchWithRecursion(array, target) : searchWithLoop(array, target);
    }

    public static int searchWithLoop(int[] array, int target) {
        if (array == null || array.length <= 0) return -1;
        int low = 0;
        int high = array.length - 1;
        while(low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] == target) {
                return mid;
            }
            else if (array[mid] > target) {
                high = mid - 1;
            }else {
                low = mid + 1;
            }
        }
        return -1;
    }

    public static int searchWithRecursion(int[] array, int target) {
        return searchWithRecursion(array, target, 0, array.length - 1);
    }

    private static int searchWithRecursion(int[] array, int target, int left, int right) {
        if (left > right) return -1;
        int mid = left + ((right - left) >> 1);
        if (array[mid] == target) {
            return mid;
        }else if (array[mid] > target) {
            return searchWithRecursion(array, target, left, mid - 1);
        }else {
            return searchWithRecursion(array, target, mid + 1, right);
        }
    }

    public static int binarySearchFirst(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] == target) {
                if (mid == 0 || array[mid - 1] != target) {
                    return mid;
                }else {
                    high = mid - 1;
                }
            }else if (array[mid] > target) {
                high = mid - 1;
            }else {
                low = mid + 1;
            }
        }
        return -1;
    }

    public static int binarySearchLast(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] == target) {
                if (mid == array.length - 1 || array[mid + 1] != target) {
                    return mid;
                }else {
                    low = mid + 1;
                }
            }else if (array[mid] > target) {
                high = mid - 1;
            }else {
                low = mid + 1;
            }
        }
        return -1;
    }

}
