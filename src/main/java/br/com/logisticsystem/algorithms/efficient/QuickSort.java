package br.com.logisticsystem.algorithms.efficient;

import java.util.Comparator;

public class QuickSort {
    public static <T> void sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length < 2) {return;}
        sort(array, 0, array.length - 1, comparator);
    }

    private static <T> void sort(T[] array, int baixo, int alto, Comparator<T> comparator) {
        if (baixo < alto) {
            int pivot = particionar(array, baixo, alto, comparator);
            sort(array, baixo, pivot - 1, comparator);
            sort(array, pivot + 1, alto, comparator);
        }
    }

    private static <T> int particionar(T[] array, int baixo, int alto, Comparator<T> comparator) {
        T pivot = array[alto];
        int i = baixo - 1;
        for (int j = baixo; j < alto; j++) {
            if (comparator.compare(array[j], pivot) <= 0) {
                i++;
                trocar(array, i, j);
            }
        }
        trocar(array, i + 1, alto);
        return i + 1;
    }

    private static <T> void trocar(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}