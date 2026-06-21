package br.com.logisticsystem.algorithms.efficient;

import br.com.logisticsystem.utils.Estatisticas;

public class MergeSort {
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