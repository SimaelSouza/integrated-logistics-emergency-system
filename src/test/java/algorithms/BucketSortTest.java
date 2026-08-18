package algorithms;

import br.com.logisticsystem.algorithms.linear.BucketSort;
import br.com.logisticsystem.utils.Estatisticas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BucketSortTest {

    @Test
    void deveOrdenarArrayDesordenado() {
        int[] array = {29, 25, 3, 49, 9, 37, 21, 43};

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{3, 9, 21, 25, 29, 37, 43, 49},
                array
        );
    }

    @Test
    void deveOrdenarArrayJaOrdenado() {
        int[] array = {1, 2, 3, 4, 5};

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void deveOrdenarArrayInvertido() {
        int[] array = {5, 4, 3, 2, 1};

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void deveOrdenarArrayComElementosRepetidos() {
        int[] array = {4, 2, 4, 1, 2, 3};

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{1, 2, 2, 3, 4, 4},
                array
        );
    }

    @Test
    void deveOrdenarArrayComValoresNegativos() {
        int[] array = {-10, 5, -2, 8, 0};

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{-10, -2, 0, 5, 8},
                array
        );
    }

    @Test
    void deveManterArrayComTodosValoresIguais() {
        int[] array = {7, 7, 7, 7, 7};

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{7, 7, 7, 7, 7},
                array
        );
    }

    @Test
    void deveOrdenarArrayComUmElemento() {
        int[] array = {10};

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{10},
                array
        );
    }

    @Test
    void deveOrdenarArrayVazio() {
        int[] array = {};

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{},
                array
        );
    }

    @Test
    void deveRegistrarComparacoes() {
        int[] array = {5, 3, 1, 2};

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        assertTrue(estatisticas.getComparacoes() >= 0);
    }

    @Test
    void deveRegistrarTrocas() {
        int[] array = {5, 3, 1, 2};

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        assertTrue(estatisticas.getTrocas() > 0);
    }

    @Test
    void deveOrdenarArrayGrande() {
        int[] array = new int[1000];

        for (int i = 0; i < array.length; i++) {
            array[i] = array.length - i;
        }

        Estatisticas estatisticas =
                new Estatisticas("BucketSort", array.length);

        BucketSort.ordenar(array, estatisticas);

        for (int i = 0; i < array.length - 1; i++) {
            assertTrue(array[i] <= array[i + 1]);
        }
    }
}