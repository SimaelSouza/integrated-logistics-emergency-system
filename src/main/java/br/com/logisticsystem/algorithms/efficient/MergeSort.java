package br.com.logisticsystem.algorithms.efficient;

public class MergeSort {
    public static <T extends Comparable<T>> void sort(T[] array) {
        if (array.length < 2) return;
        sort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void sort(T[] array, int esquerda, int direita) {
        if (esquerda < direita) {
            int meio = esquerda + (direita - esquerda) / 2;
            sort(array, esquerda, meio);
            sort(array, meio + 1, direita);
            merge(array, esquerda, meio, direita);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void merge(T[] array, int esquerda, int meio, int direita) {
        int n1 = meio - esquerda + 1;
        int n2 = direita - meio;

        Object[] L = new Object[n1];
        Object[] R = new Object[n2];

        for (int i = 0; i < n1; ++i) L[i] = array[esquerda + i];
        for (int j = 0; j < n2; ++j) R[j] = array[meio + 1 + j];

        int i = 0, j = 0, k = esquerda;
        while (i < n1 && j < n2) {
            if (((T) L[i]).compareTo((T) R[j]) <= 0) {
                array[k] = (T) L[i];
                i++;
            } else {
                array[k] = (T) R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = (T) L[i];
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = (T) R[j];
            j++;
            k++;
        }
    }
}
