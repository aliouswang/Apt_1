package com.example.lib_algorithm.step1;

import com.example.lib_algorithm.util.Logger;

public class Step1_Main {

    public static void main(String[] args) {

        int [] array = {0, 1, 2, 3, 4, 5, 6, 6, 6, 7, 8, 9};

        Logger.d("search reuslt with loop: " + BinarySearch.search(array, 3, false));

        Logger.d("search reuslt with recuration: " + BinarySearch.search(array, 3, true));

    }

}
