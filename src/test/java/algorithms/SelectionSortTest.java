package algorithms;

import br.com.logisticsystem.algorithms.quadratic.SelectionSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SelectionSortTest {
    @Test
    void deveOrdenarArrayJaOrdenadoEInvertido() {
        Integer[] invertido = {9, 7, 5, 3, 1};
        Integer[] esperado = {1, 3, 5, 7, 9};

        SelectionSort.sort(invertido);

        assertArrayEquals(esperado, invertido);
    }
}