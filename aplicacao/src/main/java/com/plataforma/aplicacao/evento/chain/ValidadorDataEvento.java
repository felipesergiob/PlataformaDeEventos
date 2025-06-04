package com.plataforma.aplicacao.evento.chain;

import com.plataforma.aplicacao.evento.EventoResumo;
import java.time.LocalDateTime;

public class ValidadorDataEvento extends ValidadorEvento {

	@Override
	protected void executarValidacao(EventoResumo evento) throws ValidacaoEventoException {
		LocalDateTime agora = LocalDateTime.now();

		if (evento.getDataInicio().isBefore(agora)) {
			throw new ValidacaoEventoException("A data de início do evento não pode ser no passado");
		}

		if (evento.getDataFim().isBefore(evento.getDataInicio())) {
			throw new ValidacaoEventoException("A data de fim não pode ser anterior à data de início");
		}
	}
}