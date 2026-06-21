package br.com.logisticsystem.algorithms.quadratic;

import br.com.logisticsystem.utils.Estatisticas;

public class SelectionSort {

    public static void ordenar(int[] array, Estatisticas estatisticas) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < n; j++) {
                estatisticas.incrementarComparacoes();
                if (array[j] < array[indiceMinimo]) {
                    indiceMinimo = j;
                }
            }
            int temp = array[indiceMinimo];
            array[indiceMinimo] = array[i];
            array[i] = temp;
            estatisticas.incrementarTrocas();
        }
    }
}