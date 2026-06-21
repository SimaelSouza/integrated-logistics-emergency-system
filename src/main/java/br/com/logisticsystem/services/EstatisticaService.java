package br.com.logisticsystem.services;

import br.com.logisticsystem.models.Entrega;
import br.com.logisticsystem.models.EnumStatus;
import br.com.logisticsystem.models.Pedido;

import java.time.Duration;

public class EstatisticaService {

    private final AtendimentoService atendimentoService;
    private final PedidoService pedidoService;
    private final EntregaService entregaService;
    private final HistoricoService historicoService;

    public EstatisticaService(
            AtendimentoService atendimentoService,
            PedidoService pedidoService,
            EntregaService entregaService,
            HistoricoService historicoService
    ) {
        this.atendimentoService = atendimentoService;
        this.pedidoService = pedidoService;
        this.entregaService = entregaService;
        this.historicoService = historicoService;
    }



    public int getTotalAtendimentosPendentes() {
        return atendimentoService.getTotalPendentes();
    }

    public double getTempoMedioEspera() {
        return atendimentoService.calcularTempoMedioEsperaSegundos();
    }



    public int getTotalPedidos() {
        return pedidoService.listarPedidos().length;
    }

    public int getPedidosAtivos() {

        int total = 0;

        for (Pedido pedido : pedidoService.listarPedidos()) {

            if (pedido.getStatus() != EnumStatus.CANCELADA &&
                    pedido.getStatus() != EnumStatus.ENTREGUE) {

                total++;
            }
        }

        return total;
    }



    public int getTotalEntregas() {
        return entregaService.listarTodas().length;
    }

    public int getEntregasConcluidas() {

        int total = 0;

        for (Entrega entrega : entregaService.listarTodas()) {

            if (entrega.getStatus() == EnumStatus.ENTREGUE) {
                total++;
            }
        }

        return total;
    }

    public int getEntregasCanceladas() {

        int total = 0;

        for (Entrega entrega : entregaService.listarTodas()) {

            if (entrega.getStatus() == EnumStatus.CANCELADA) {
                total++;
            }
        }

        return total;
    }

    public double getTempoMedioEntregaHoras() {

        long somaHoras = 0;
        int concluidas = 0;

        for (Entrega entrega : entregaService.listarTodas()) {

            if (entrega.getHorarioConclusao() != null) {

                somaHoras += Duration.between(
                        entrega.getHorarioCriacao(),
                        entrega.getHorarioConclusao()
                ).toHours();

                concluidas++;
            }
        }

        if (concluidas == 0) {
            return 0;
        }

        return (double) somaHoras / concluidas;
    }



    public int getTamanhoAtualFilas() {
        return atendimentoService.getTotalPendentes();
    }



    public int getQuantidadeMovimentacoes() {
        return historicoService.getQuantidadeMovimentacoes();
    }



    public int getTotalRegistrosSistema() {

        return getTotalPedidos()
                + getTotalEntregas()
                + getQuantidadeMovimentacoes();
    }

    public double getTaxaCrescimentoSistema() {

        int registros = getTotalRegistrosSistema();

        if (registros == 0) {
            return 0;
        }

        return registros / 30.0;
    }
}