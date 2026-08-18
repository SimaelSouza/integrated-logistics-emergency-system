package algorithms;

import br.com.logisticsystem.algorithms.efficient.QuickSort;
import br.com.logisticsystem.utils.Estatisticas;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    @Test
    void deveOrdenarCorretamenteComQuickSort() {
        int[] atual = {25, 12, 44, 10, 7, 9, 1};
        int[] esperado = {1, 7, 9, 10, 12, 25, 44};

        Estatisticas estatisticas = new Estatisticas("QuickSort", 1);
        QuickSort.ordenar(atual, estatisticas);

        assertArrayEquals(esperado, atual);
    }
}