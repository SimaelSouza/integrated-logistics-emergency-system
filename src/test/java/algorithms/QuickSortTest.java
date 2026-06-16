package algorithms;

import br.com.logisticsystem.algorithms.efficient.QuickSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    @Test
    void deveOrdenarCorretamenteComQuickSort() {
        Integer[] atual = {25, 12, 44, 10, 7, 9, 1};
        Integer[] esperado = {1, 7, 9, 10, 12, 25, 44};

        QuickSort.sort(atual);

        assertArrayEquals(esperado, atual);
    }
}