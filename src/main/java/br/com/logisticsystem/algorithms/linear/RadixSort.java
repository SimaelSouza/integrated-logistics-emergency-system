package br.com.logisticsystem.algorithms.linear;

import br.com.logisticsystem.utils.Estatisticas;

public class RadixSort {
    public static void ordenar(int[] array, Estatisticas estatisticas) {
        if (array.length == 0) return;

        int max = array[0];
        for (int val : array) if (val > max) max = val;

        for (int exp = 1; max / exp > 0; exp *= 10) {
            int[] output = new int[array.length];
            int[] count = new int[10];

            for (int i = 0; i < array.length; i++) count[(array[i] / exp) % 10]++;
            for (int i = 1; i < 10; i++) count[i] += count[i - 1];
            for (int i = array.length - 1; i >= 0; i--) {
                output[count[(array[i] / exp) % 10] - 1] = array[i];
                count[(array[i] / exp) % 10]--;
                estatisticas.incrementarTrocas();
            }
            System.arraycopy(output, 0, array, 0, array.length);
        }
    }
}