package br.com.logisticsystem.algorithms.linear;

public class CountingSort {
    public static void sort(int[] array) {
        if (array.length == 0) return;
        int max = array[0], min = array[0];
        for (int val : array) {
            if (val > max) max = val;
            if (val < min) min = val;
        }

        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[array.length];

        for (int i = 0; i < array.length; i++) count[array[i] - min]++;
        for (int i = 1; i < count.length; i++) count[i] += count[i - 1];
        for (int i = array.length - 1; i >= 0; i--) {
            output[count[array[i] - min] - 1] = array[i];
            count[array[i] - min]--;
        }
        System.arraycopy(output, 0, array, 0, array.length);
    }
}
