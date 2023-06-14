package main.java.org.luaneric.quicksort;

import main.java.org.luaneric.GenericCustomValue;
import main.java.org.luaneric.binaryThree.CustomBinaryNode;

import java.util.Comparator;
import java.util.List;

public class Quicksort {

    protected Comparator<String> functionCompare;

    public Quicksort(Comparator<String> functionCompare) {
        this.functionCompare = functionCompare;
    }

    public void quicksort(List<String> words, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(words, low, high);
            quicksort(words, low, pivotIndex - 1);
            quicksort(words, pivotIndex + 1, high);
        }
    }

    private int partition(List<String> words, int low, int high) {
        String pivot = words.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {

            int compareValue = this.functionCompare.compare(words.get(j), pivot);
            if (compareValue < 0) {
                i++;
                swap(words, i, j);
            }
        }

        swap(words, i + 1, high);
        return i + 1;
    }

    private void swap(List<String> words, int i, int j) {
        String temp = words.get(i);
        words.set(i, words.get(j));
        words.set(j, temp);
    }

}
