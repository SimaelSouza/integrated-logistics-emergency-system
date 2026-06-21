package br.com.logisticsystem.services;

import br.com.logisticsystem.algorithms.efficient.QuickSort;
import br.com.logisticsystem.models.Movimentacao;
import br.com.logisticsystem.models.TipoOperacao;
import br.com.logisticsystem.structures.doublylinkedlist.ListaDupla;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.UUID;

public class HistoricoService {

    private final ListaDupla<Movimentacao> historico;

    private int indiceAtual;

    public HistoricoService() {
        this.historico = new ListaDupla<>();
        this.indiceAtual = -1;
    }

    public void registrarMovimentacao(TipoOperacao tipoOperacao, String descricao
    ) {

        historico.inserir(
                new Movimentacao(
                        tipoOperacao,
                        descricao
                )
        );

        if (indiceAtual == -1) {
            indiceAtual = 0;
        }
    }

    public void registrarMovimentacao(
            TipoOperacao tipoOperacao,
            String descricao,
            Object entidade
    ) {

        historico.inserir(
                new Movimentacao(
                        tipoOperacao,
                        descricao,
                        entidade
                )
        );

        if (indiceAtual == -1) {
            indiceAtual = 0;
        }
    }

    public boolean estaVazio() {
        return historico.isEmpty();
    }

    public int getQuantidadeMovimentacoes() {
        return historico.getTamanho();
    }

    public Movimentacao buscarPorId(UUID id) {

        for (int i = 0; i < historico.getTamanho(); i++) {

            Movimentacao atual =
                    historico.buscar(i);

            if (atual.getId().equals(id)) {
                return atual;
            }
        }

        return null;
    }

    public Movimentacao buscarPorPosicao(int posicao) {
        return historico.buscar(posicao);
    }

    public Movimentacao[] buscarPorTipo(
            TipoOperacao tipo
    ) {

        int quantidade = 0;

        for (int i = 0; i < historico.getTamanho(); i++) {

            if (historico.buscar(i)
                    .getTipoOperacao() == tipo) {

                quantidade++;
            }
        }

        Movimentacao[] resultado =
                new Movimentacao[quantidade];

        int indice = 0;

        for (int i = 0; i < historico.getTamanho(); i++) {

            Movimentacao atual =
                    historico.buscar(i);

            if (atual.getTipoOperacao() == tipo) {
                resultado[indice++] = atual;
            }
        }

        return resultado;
    }

    public Movimentacao[] buscarPorPeriodo(
            LocalDateTime inicio,
            LocalDateTime fim
    ) {

        int quantidade = 0;

        for (int i = 0; i < historico.getTamanho(); i++) {

            Movimentacao atual =
                    historico.buscar(i);

            if (!atual.getHorario().isBefore(inicio)
                    && !atual.getHorario().isAfter(fim)) {

                quantidade++;
            }
        }

        Movimentacao[] resultado =
                new Movimentacao[quantidade];

        int indice = 0;

        for (int i = 0; i < historico.getTamanho(); i++) {

            Movimentacao atual =
                    historico.buscar(i);

            if (!atual.getHorario().isBefore(inicio)
                    && !atual.getHorario().isAfter(fim)) {

                resultado[indice++] = atual;
            }
        }

        return resultado;
    }

    public Movimentacao primeiraMovimentacao() {

        if (historico.isEmpty()) {
            return null;
        }

        indiceAtual = 0;

        return historico.buscar(indiceAtual);
    }

    public Movimentacao ultimaMovimentacao() {

        if (historico.isEmpty()) {
            return null;
        }

        indiceAtual = historico.getTamanho() - 1;

        return historico.buscar(indiceAtual);
    }

    public Movimentacao proximaMovimentacao() {

        if (historico.isEmpty()) {
            return null;
        }

        if (indiceAtual >= historico.getTamanho() - 1) {
            return null;
        }

        indiceAtual++;

        return historico.buscar(indiceAtual);
    }

    public Movimentacao movimentacaoAnterior() {

        if (historico.isEmpty()) {
            return null;
        }

        if (indiceAtual <= 0) {
            return null;
        }

        indiceAtual--;

        return historico.buscar(indiceAtual);
    }

    public Movimentacao[] listarHistorico() {

        Movimentacao[] movimentacoes =
                new Movimentacao[historico.getTamanho()];

        for (int i = 0; i < historico.getTamanho(); i++) {

            movimentacoes[i] =
                    historico.buscar(i);
        }

        return movimentacoes;
    }

    public String listarHistoricoTexto() {
        return historico.listarFrente();
    }

    public Movimentacao[] listarOrdenadoPorHorario() {

        Movimentacao[] movimentacoes =
                listarHistorico();

        QuickSort.sort(
                movimentacoes,
                Comparator.comparing(
                        Movimentacao::getHorario
                )
        );

        return movimentacoes;
    }

    public Movimentacao[] listarOrdenadoPorTipoOperacao() {

        Movimentacao[] movimentacoes =
                listarHistorico();

        QuickSort.sort(
                movimentacoes,
                Comparator.comparing(
                        m -> m.getTipoOperacao().name()
                )
        );

        return movimentacoes;
    }

    public Movimentacao[] listarOrdenadoPorDescricao() {

        Movimentacao[] movimentacoes =
                listarHistorico();

        QuickSort.sort(
                movimentacoes,
                Comparator.comparing(
                        Movimentacao::getDescricao
                )
        );

        return movimentacoes;
    }
}