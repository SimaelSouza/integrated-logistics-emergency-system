package algorithms;

import br.com.logisticsystem.algorithms.linear.RadixSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RadixSortTest {
    @Test
    void deveOrdenarInteirosNaoNegativosComDiferentesDigitos() {
        int[] atual = {170, 45, 75, 90, 802, 24, 2, 66};
        int[] esperado = {2, 24, 45, 66, 75, 90, 170, 802};

        RadixSort.sort(atual);

        assertArrayEquals(esperado, atual);
    }
}