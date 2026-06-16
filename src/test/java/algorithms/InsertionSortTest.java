package algorithms;

import br.com.logisticsystem.algorithms.quadratic.InsertionSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {
    @Test
    void deveOrdenarArrayDeStrings() {
        String[] atual = {"Logistica", "Caminhao", "Entrega", "Aviao"};
        String[] esperado = {"Aviao", "Caminhao", "Entrega", "Logistica"};

        InsertionSort.sort(atual);

        assertArrayEquals(esperado, atual);
    }
}