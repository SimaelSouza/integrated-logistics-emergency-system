package br.com.logisticsystem.algorithms.quadratic;

import br.com.logisticsystem.utils.Estatisticas;

public class BubbleSort {

    public static void ordenar(int[] array, Estatisticas estatisticas) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            boolean trocou = false;
            for (int j = 0; j < n - i - 1; j++) {
                estatisticas.incrementarComparacoes();
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    estatisticas.incrementarTrocas();
                    trocou = true;
                }
            }
            if (!trocou) break;
        }
    }
}