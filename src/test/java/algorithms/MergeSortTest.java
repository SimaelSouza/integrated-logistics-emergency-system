package algorithms;

import br.com.logisticsystem.algorithms.efficient.MergeSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    @Test
    void deveOrdenarGrandesVolumesComDuplicados() {
        Integer[] atual = {100, -50, 100, 0, 15, -50, 4};
        Integer[] esperado = {-50, -50, 0, 4, 15, 100, 100};

        MergeSort.sort(atual);

        assertArrayEquals(esperado, atual);
    }
}