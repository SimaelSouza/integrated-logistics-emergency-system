package br.com.logisticsystem.algorithms.linear;

import br.com.logisticsystem.utils.Estatisticas;

public class BucketSort {

    public static void sort(int[] array) {
        if (array == null || array.length < 2) return;

        int max = array[0];
        int min = array[0];
        for (int val : array) {
            if (val > max) max = val;
            if (val < min) min = val;
        }

        if (max == min) return;

        int numBaldes = array.length;

        int[][] baldes = new int[numBaldes][array.length];
        int[] tamanhoBaldes = new int[numBaldes];

        for (int val : array) {
            int indiceBalde = (int) ((long) (val - min) * (numBaldes - 1) / (max - min));

            int posicaoInsercao = tamanhoBaldes[indiceBalde];
            baldes[indiceBalde][posicaoInsercao] = val;
            tamanhoBaldes[indiceBalde]++;
        }

        int indiceGeral = 0;
        for (int i = 0; i < numBaldes; i++) {
            int tamanhoAtual = tamanhoBaldes[i];

            if (tamanhoAtual > 0) {
                insertionSortManual(baldes[i], tamanhoAtual);

                for (int j = 0; j < tamanhoAtual; j++) {
                    array[indiceGeral++] = baldes[i][j];
                }
            }
        }
    }

    private static void insertionSortManual(int[] balde, int tamanho) {
        for (int i = 1; i < tamanho; i++) {
            int chave = balde[i];
            int j = i - 1;
            while (j >= 0 && balde[j] > chave) {
                balde[j + 1] = balde[j];
                j--;
            }
            balde[j + 1] = chave;
        }
    }


    public static void ordenar(int[] array, Estatisticas estatisticas) {
        if (array == null || array.length < 2) return;

        int max = array[0];
        int min = array[0];
        for (int val : array) {
            if (val > max) max = val;
            if (val < min) min = val;
        }

        if (max == min) return;

        int numBaldes = array.length;
        int[][] baldes = new int[numBaldes][array.length];
        int[] tamanhoBaldes = new int[numBaldes];

        for (int val : array) {
            int indiceBalde = (int) ((long) (val - min) * (numBaldes - 1) / (max - min));
            int posicaoInsercao = tamanhoBaldes[indiceBalde];
            baldes[indiceBalde][posicaoInsercao] = val;
            tamanhoBaldes[indiceBalde]++;
        }

        int indiceGeral = 0;
        for (int i = 0; i < numBaldes; i++) {
            int tamanhoAtual = tamanhoBaldes[i];

            if (tamanhoAtual > 0) {
                insertionSortManual(baldes[i], tamanhoAtual, estatisticas);

                for (int j = 0; j < tamanhoAtual; j++) {
                    array[indiceGeral++] = baldes[i][j];
                    estatisticas.incrementarTrocas();
                }
            }
        }
    }

    private static void insertionSortManual(int[] balde, int tamanho, Estatisticas estatisticas) {
        for (int i = 1; i < tamanho; i++) {
            int chave = balde[i];
            int j = i - 1;
            while (j >= 0) {
                estatisticas.incrementarComparacoes();
                if (balde[j] > chave) {
                    balde[j + 1] = balde[j];
                    estatisticas.incrementarTrocas();
                    j--;
                } else {
                    break;
                }
            }
            balde[j + 1] = chave;
        }
    }
}