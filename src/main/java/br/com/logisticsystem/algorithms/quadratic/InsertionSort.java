package br.com.logisticsystem.algorithms.quadratic;

import br.com.logisticsystem.utils.Estatisticas;

public class InsertionSort {

    public static void ordenar(int[] array, Estatisticas estatisticas) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int chave = array[i];
            int j = i - 1;
            while (j >= 0) {
                estatisticas.incrementarComparacoes();
                if (array[j] > chave) {
                    array[j + 1] = array[j];
                    estatisticas.incrementarTrocas();
                    j--;
                } else {
                    break;
                }
            }
            array[j + 1] = chave;
        }
    }
}