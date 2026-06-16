package br.com.logisticsystem.algorithms.quadratic;

public class InsertionSort {
    public static <T extends Comparable<T>> void sort(T[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            T chave = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].compareTo(chave) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = chave;
        }
    }
}
