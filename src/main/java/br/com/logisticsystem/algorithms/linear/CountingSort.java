package br.com.logisticsystem.algorithms.linear;

import br.com.logisticsystem.utils.Estatisticas;

public class CountingSort {
    public static void ordenar(int[] array, Estatisticas estatisticas) {
        if (array.length == 0) return;

        int max = array[0];
        for (int val : array) {
            if (val < 0) {
                throw new IllegalArgumentException("CountingSort não aceita valores negativos.");
            }
            if (val > max) max = val;
        }

        int[] count = new int[max + 1];
        int[] output = new int[array.length];

        for (int val : array) {
            count[val]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        for (int i = array.length - 1; i >= 0; i--) {
            output[count[array[i]] - 1] = array[i];
            count[array[i]]--;
            estatisticas.incrementarTrocas();
        }

        System.arraycopy(output, 0, array, 0, array.length);
    }
}