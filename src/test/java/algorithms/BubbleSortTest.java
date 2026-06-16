package algorithms;

import br.com.logisticsystem.algorithms.quadratic.BubbleSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    @Test
    void deveOrdenarArrayDeInteiros() {
        Integer[] atual = {5, 3, 8, 4, 2, -1, 0, 3};
        Integer[] esperado = {-1, 0, 2, 3, 3, 4, 5, 8};

        BubbleSort.sort(atual);

        assertArrayEquals(esperado, atual);
    }

    @Test
    void deveLidarComArrayVazioOuUnitario() {
        Integer[] vazio = {};
        Integer[] unitario = {42};

        BubbleSort.sort(vazio);
        BubbleSort.sort(unitario);

        assertArrayEquals(new Integer[]{}, vazio);
        assertArrayEquals(new Integer[]{42}, unitario);
    }
}