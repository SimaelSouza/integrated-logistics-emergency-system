package algorithms;

import br.com.logisticsystem.algorithms.linear.BucketSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BucketSortTest {
    @Test
    void deveOrdenarUtilizandoEstruturaDeBaldesManual() {
        int[] atual = {30, 2, 44, 5, 12, 2, 90, -10};
        int[] esperado = {-10, 2, 2, 5, 12, 30, 44, 90};

        BucketSort.sort(atual);

        assertArrayEquals(esperado, atual);
    }

    @Test
    void deveLidarComArrayDeElementosIguais() {
        int[] atual = {7, 7, 7, 7};
        int[] esperado = {7, 7, 7, 7};

        BucketSort.sort(atual);

        assertArrayEquals(esperado, atual);
    }
}