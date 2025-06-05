package com.plataforma.aplicacao.evento.chain;

import com.plataforma.aplicacao.evento.EventoResumo;

public abstract class ValidadorEvento {
	private ValidadorEvento proximo;

	public ValidadorEvento setProximo(ValidadorEvento proximo) {
		this.proximo = proximo;
		return proximo;
	}

	public void validar(EventoResumo evento) throws ValidacaoEventoException {
		executarValidacao(evento);

		if (proximo != null) {
			proximo.validar(evento);
		}
	}

	protected abstract void executarValidacao(EventoResumo evento) throws ValidacaoEventoException;
}