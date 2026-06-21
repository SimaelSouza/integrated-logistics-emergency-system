package br.com.logisticsystem.algorithms.efficient;

import br.com.logisticsystem.utils.Estatisticas;

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


    public static void ordenar(int[] array, Estatisticas estatisticas) {
        if (array.length < 2) return;
        ordenar(array, 0, array.length - 1, estatisticas);
    }

    private static void ordenar(int[] array, int esquerda, int direita, Estatisticas estatisticas) {
        if (esquerda < direita) {
            int meio = esquerda + (direita - esquerda) / 2;
            ordenar(array, esquerda, meio, estatisticas);
            ordenar(array, meio + 1, direita, estatisticas);
            merge(array, esquerda, meio, direita, estatisticas);
        }
    }

    private static void merge(int[] array, int esquerda, int meio, int direita, Estatisticas estatisticas) {
        int n1 = meio - esquerda + 1;
        int n2 = direita - meio;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(array, esquerda, L, 0, n1);
        System.arraycopy(array, meio + 1, R, 0, n2);

        int i = 0, j = 0, k = esquerda;
        while (i < n1 && j < n2) {
            estatisticas.incrementarComparacoes();
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            estatisticas.incrementarTrocas();
            k++;
        }

        while (i < n1) {
            array[k] = L[i];
            estatisticas.incrementarTrocas();
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = R[j];
            estatisticas.incrementarTrocas();
            j++;
            k++;
        }
    }
}