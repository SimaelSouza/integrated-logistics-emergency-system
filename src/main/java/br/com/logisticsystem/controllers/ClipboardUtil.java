package br.com.logisticsystem.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;

import java.util.UUID;


public final class ClipboardUtil {

    private ClipboardUtil() {
        throw new UnsupportedOperationException("ClipboardUtil é uma classe utilitária e não deve ser instanciada.");
    }


    public static void copiarId(UUID id) {
        if (id == null) return;

        ClipboardContent content = new ClipboardContent();
        content.putString(id.toString());
        Clipboard.getSystemClipboard().setContent(content);
    }


    public static Button criarBotaoCopiar(UUID id) {
        Button botao = new Button("📋");
        botao.getStyleClass().add("copy-id-button");
        botao.setFocusTraversable(false);

        Tooltip tooltipPadrao = new Tooltip("Copiar ID");
        Tooltip.install(botao, tooltipPadrao);

        botao.setOnAction(evento -> {
            copiarId(id);
            Tooltip confirmacao = new Tooltip("Copiado!");
            botao.setTooltip(confirmacao);
            confirmacao.show(botao,
                    botao.localToScreen(botao.getBoundsInLocal()).getMinX(),
                    botao.localToScreen(botao.getBoundsInLocal()).getMaxY());

            javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(Duration.seconds(1));
            pausa.setOnFinished(e -> {
                confirmacao.hide();
                botao.setTooltip(tooltipPadrao);
            });
            pausa.play();
        });

        return botao;
    }
}