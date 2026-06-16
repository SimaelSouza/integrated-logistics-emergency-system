package br.com.logisticsystem.algorithms.efficient;

public class QuickSort {
    public static <T extends Comparable<T>> void sort(T[] array) {
        if (array.length < 2) return;
        sort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void sort(T[] array, int baixo, int alto) {
        if (baixo < alto) {
            int indicePivot = particionar(array, baixo, alto);
            sort(array, baixo, indicePivot - 1);
            sort(array, indicePivot + 1, alto);
        }
    }

    private static <T extends Comparable<T>> int particionar(T[] array, int baixo, int alto) {
        T pivot = array[alto];
        int i = (baixo - 1);
        for (int j = baixo; j < alto; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        T temp = array[i + 1];
        array[i + 1] = array[alto];
        array[alto] = temp;
        return i + 1;
    }
}
