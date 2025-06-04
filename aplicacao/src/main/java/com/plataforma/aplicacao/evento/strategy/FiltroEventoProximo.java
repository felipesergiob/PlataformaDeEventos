package com.plataforma.aplicacao.evento.strategy;

import com.plataforma.aplicacao.evento.EventoResumo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FiltroEventoProximo implements FiltroEventoStrategy {

	@Override
	public List<EventoResumo> filtrar(List<EventoResumo> eventos) {
		LocalDateTime agora = LocalDateTime.now();
		LocalDateTime limiteFuturo = agora.plusDays(7);

		return eventos.stream()
				.filter(evento -> evento.getDataInicio().isAfter(agora) &&
						evento.getDataInicio().isBefore(limiteFuturo))
				.collect(Collectors.toList());
	}
}