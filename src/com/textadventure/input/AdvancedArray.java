package com.textadventure.input;

import java.util.Arrays;

public class AdvancedArray <T> {
    private final T[] array;
    AdvancedArray(T[] elements) {
        array = elements;
    }

    T get(int index) {
        return array[index];
    }

    public boolean contains(T e) {
        for (T a : array) {
            if (a.equals(e))
                return true;
        }
        return false;
    }
}
