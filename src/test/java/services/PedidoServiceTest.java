package services;

import br.com.logisticsystem.models.*;
import br.com.logisticsystem.services.HistoricoService;
import br.com.logisticsystem.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        pedidoService = new PedidoService(new HistoricoService());
    }


    @Test
    void deveCadastrarPedido() {

        Pedido pedido = pedidoService.cadastrar(
                "João",
                "Carga eletrônica",
                EnumPrioridade.ALTA
        );

        assertNotNull(pedido);
        assertEquals(1, pedidoService.getTotalPedidos());
    }

    @Test
    void deveLancarExcecaoAoCadastrarNomeNulo() {

        assertThrows(
                IllegalArgumentException.class,
                () -> pedidoService.cadastrar(
                        null,
                        "Carga",
                        EnumPrioridade.ALTA
                )
        );
    }

    @Test
    void deveLancarExcecaoAoCadastrarDescricaoNula() {

        assertThrows(
                IllegalArgumentException.class,
                () -> pedidoService.cadastrar(
                        "João",
                        null,
                        EnumPrioridade.ALTA
                )
        );
    }

    @Test
    void deveLancarExcecaoAoCadastrarPrioridadeNula() {

        assertThrows(
                IllegalArgumentException.class,
                () -> pedidoService.cadastrar(
                        "João",
                        "Carga",
                        null
                )
        );
    }


    @Test
    void deveBuscarPedidoPorId() {

        Pedido pedido = pedidoService.cadastrar(
                "Maria",
                "Medicamentos",
                EnumPrioridade.MEDIA
        );

        Pedido encontrado =
                pedidoService.buscarPorId(pedido.getId());

        assertEquals(pedido, encontrado);
    }

    @Test
    void deveRetornarNullAoBuscarIdInexistente() {

        Pedido encontrado =
                pedidoService.buscarPorId(UUID.randomUUID());

        assertNull(encontrado);
    }


    @Test
    void deveAtualizarDescricaoEPrioridade() {

        Pedido pedido = pedidoService.cadastrar(
                "Carlos",
                "Carga A",
                EnumPrioridade.BAIXA
        );

        boolean atualizado =
                pedidoService.atualizar(
                        pedido.getId(),
                        "Carga B",
                        EnumPrioridade.CRITICA
                );

        Pedido atualizadoPedido =
                pedidoService.buscarPorId(pedido.getId());

        assertTrue(atualizado);
        assertEquals(
                "Carga B",
                atualizadoPedido.getDescricaoCarga()
        );
        assertEquals(
                EnumPrioridade.CRITICA,
                atualizadoPedido.getPrioridade()
        );
    }

    @Test
    void deveRetornarFalsoAoAtualizarPedidoInexistente() {

        assertFalse(
                pedidoService.atualizar(
                        UUID.randomUUID(),
                        "Nova carga",
                        EnumPrioridade.ALTA
                )
        );
    }


    @Test
    void deveAlterarStatusPedido() {

        Pedido pedido = pedidoService.cadastrar(
                "Pedro",
                "Carga",
                EnumPrioridade.MEDIA
        );

        boolean alterado =
                pedidoService.alterarStatus(
                        pedido.getId(),
                        EnumStatus.EM_TRANSITO
                );

        assertTrue(alterado);

        assertEquals(
                EnumStatus.EM_TRANSITO,
                pedido.getStatus()
        );
    }

    @Test
    void deveRetornarFalsoAoAlterarStatusDePedidoInexistente() {

        assertFalse(
                pedidoService.alterarStatus(
                        UUID.randomUUID(),
                        EnumStatus.EM_TRANSITO
                )
        );
    }


    @Test
    void deveRemoverPedido() {

        Pedido pedido = pedidoService.cadastrar(
                "Lucas",
                "Carga",
                EnumPrioridade.ALTA
        );

        assertTrue(
                pedidoService.remover(
                        pedido.getId()
                )
        );

        assertEquals(
                0,
                pedidoService.getTotalPedidos()
        );
    }

    @Test
    void deveRetornarFalsoAoRemoverPedidoInexistente() {

        assertFalse(
                pedidoService.remover(
                        UUID.randomUUID()
                )
        );
    }


    @Test
    void deveListarTodosPedidos() {

        pedidoService.cadastrar(
                "A",
                "Carga A",
                EnumPrioridade.ALTA
        );

        pedidoService.cadastrar(
                "B",
                "Carga B",
                EnumPrioridade.MEDIA
        );

        assertEquals(
                2,
                pedidoService.listarPedidos().length
        );
    }

    @Test
    void deveListarPedidosPorStatus() {

        Pedido pedido =
                pedidoService.cadastrar(
                        "João",
                        "Carga",
                        EnumPrioridade.MEDIA
                );

        pedidoService.alterarStatus(
                pedido.getId(),
                EnumStatus.EM_TRANSITO
        );

        Pedido[] resultado =
                pedidoService.listarPorStatus(
                        EnumStatus.EM_TRANSITO
                );

        assertEquals(1, resultado.length);
        assertEquals(
                EnumStatus.EM_TRANSITO,
                resultado[0].getStatus()
        );
    }

    @Test
    void deveListarPedidosAtivos() {

        Pedido pedido =
                pedidoService.cadastrar(
                        "Maria",
                        "Carga",
                        EnumPrioridade.ALTA
                );

        pedidoService.alterarStatus(
                pedido.getId(),
                EnumStatus.ENTREGUE
        );

        assertEquals(
                0,
                pedidoService.listarPedidosAtivos().length
        );
    }


    @Test
    void deveOrdenarPorPrioridade() {

        pedidoService.cadastrar(
                "A",
                "Carga",
                EnumPrioridade.CRITICA
        );

        pedidoService.cadastrar(
                "B",
                "Carga",
                EnumPrioridade.BAIXA
        );

        pedidoService.cadastrar(
                "C",
                "Carga",
                EnumPrioridade.ALTA
        );

        Pedido[] ordenados =
                pedidoService.listarOrdenadosPorPrioridade();

        assertEquals(
                EnumPrioridade.BAIXA,
                ordenados[0].getPrioridade()
        );

        assertEquals(
                EnumPrioridade.CRITICA,
                ordenados[2].getPrioridade()
        );
    }


    @Test
    void deveDesfazerCadastro() {

        Pedido pedido =
                pedidoService.cadastrar(
                        "João",
                        "Carga",
                        EnumPrioridade.MEDIA
                );

        pedidoService.desfazer();

        assertNull(
                pedidoService.buscarPorId(
                        pedido.getId()
                )
        );
    }

    @Test
    void deveRefazerCadastro() {

        Pedido pedido =
                pedidoService.cadastrar(
                        "João",
                        "Carga",
                        EnumPrioridade.MEDIA
                );

        pedidoService.desfazer();
        pedidoService.refazer();

        assertNotNull(
                pedidoService.buscarPorId(
                        pedido.getId()
                )
        );
    }

    @Test
    void deveDesfazerRemocao() {

        Pedido pedido =
                pedidoService.cadastrar(
                        "João",
                        "Carga",
                        EnumPrioridade.MEDIA
                );

        pedidoService.remover(
                pedido.getId()
        );

        pedidoService.desfazer();

        assertNotNull(
                pedidoService.buscarPorId(
                        pedido.getId()
                )
        );
    }

    @Test
    void deveDesfazerAtualizacao() {

        Pedido pedido =
                pedidoService.cadastrar(
                        "João",
                        "Carga Original",
                        EnumPrioridade.BAIXA
                );

        pedidoService.atualizar(
                pedido.getId(),
                "Nova Carga",
                EnumPrioridade.CRITICA
        );

        pedidoService.desfazer();

        Pedido resultado =
                pedidoService.buscarPorId(
                        pedido.getId()
                );

        assertEquals(
                "Carga Original",
                resultado.getDescricaoCarga()
        );

        assertEquals(
                EnumPrioridade.BAIXA,
                resultado.getPrioridade()
        );
    }


    @Test
    void deveIniciarVazio() {

        assertTrue(
                pedidoService.estaVazio()
        );
    }

    @Test
    void naoDeveEstarVazioAposCadastro() {

        pedidoService.cadastrar(
                "João",
                "Carga",
                EnumPrioridade.ALTA
        );

        assertFalse(
                pedidoService.estaVazio()
        );
    }

    @Test
    void deveInformarQuandoPodeDesfazer() {

        pedidoService.cadastrar(
                "João",
                "Carga",
                EnumPrioridade.ALTA
        );

        assertTrue(
                pedidoService.podeDesfazer()
        );
    }

    @Test
    void naoDevePoderRefazerSemDesfazerAntes() {

        assertFalse(
                pedidoService.podeRefazer()
        );
    }
}