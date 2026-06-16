package br.com.logisticsystem.algorithms.quadratic;

public class SelectionSort {
    public static <T extends Comparable<T>> void sort(T[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j].compareTo(array[indiceMinimo]) < 0) {
                    indiceMinimo = j;
                }
            }
            T temp = array[indiceMinimo];
            array[indiceMinimo] = array[i];
            array[i] = temp;
        }
    }
}
