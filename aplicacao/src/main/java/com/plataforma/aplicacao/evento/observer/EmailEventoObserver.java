package com.plataforma.aplicacao.evento.observer;

import org.springframework.stereotype.Component;

@Component
public class EmailEventoObserver implements EventoObserver {
	@Override
	public void notificar(EventoNotificacao notificacao) {
		System.out.println("Enviando email sobre o evento: " + notificacao.getEvento().getTitulo());
		System.out.println("Tipo: " + notificacao.getTipo());
		System.out.println("Mensagem: " + notificacao.getMensagem());
	}
}