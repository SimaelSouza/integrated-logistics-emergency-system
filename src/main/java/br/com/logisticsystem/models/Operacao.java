package br.com.logisticsystem.models;

import java.time.LocalDateTime;

public class Operacao {

    private final TipoOperacao tipoOperacao;
    private final Object estadoAnterior;
    private final Object estadoNovo;
    private final LocalDateTime horario;
    private final String descricao;

    public Operacao(TipoOperacao tipoOperacao, Object estadoAnterior,
                    Object estadoNovo, String descricao) {
        validarTipoOperacao(tipoOperacao);
        validarDescrição(descricao);

        this.tipoOperacao = tipoOperacao;
        this.estadoAnterior = estadoAnterior;
        this.estadoNovo = estadoNovo;
        this.horario = LocalDateTime.now();
        this.descricao = descricao;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public Object getEstadoAnterior() {
        return estadoAnterior;
    }

    public Object getEstadoNovo() {
        return estadoNovo;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public String getDescricao() {
        return descricao;
    }

    private void validarTipoOperacao(TipoOperacao tipoOperacao){
        if(tipoOperacao == null){
            throw new IllegalArgumentException("O tipo de operação é obrigatório");
        }
    }

    private void validarDescrição(String descricao){
        if(descricao == null || descricao.isBlank()){
            throw new IllegalArgumentException("A descrição é obrigatória");
        }
    }
}
