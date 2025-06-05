package com.plataforma.aplicacao.evento.observer;

import com.plataforma.aplicacao.evento.EventoResumo;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventoNotificacao {
	private EventoResumo evento;
	private String tipo;
	private String mensagem;
	private LocalDateTime dataNotificacao;

	public EventoNotificacao(EventoResumo evento, String tipo, String mensagem) {
		this.evento = evento;
		this.tipo = tipo;
		this.mensagem = mensagem;
		this.dataNotificacao = LocalDateTime.now();
	}
}