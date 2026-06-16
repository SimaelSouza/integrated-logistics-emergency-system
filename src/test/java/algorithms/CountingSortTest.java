package algorithms;

import br.com.logisticsystem.algorithms.linear.CountingSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CountingSortTest {
    @Test
    void deveOrdenarValoresNegativosEPositivos() {
        int[] atual = {4, -2, 0, 3, -2, 1};
        int[] esperado = {-2, -2, 0, 1, 3, 4};

        CountingSort.sort(atual);

        assertArrayEquals(esperado, atual);
    }
}