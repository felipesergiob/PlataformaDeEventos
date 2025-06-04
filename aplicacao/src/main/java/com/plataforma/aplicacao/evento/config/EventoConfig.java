package com.plataforma.aplicacao.evento.config;

import com.plataforma.aplicacao.evento.chain.ValidadorEvento;
import com.plataforma.aplicacao.evento.chain.ValidadorDataEvento;
import com.plataforma.aplicacao.evento.strategy.FiltroEventoStrategy;
import com.plataforma.aplicacao.evento.strategy.FiltroEventoProximo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class EventoConfig {

	@Bean
	public ValidadorDataEvento validadorDataEvento() {
		return new ValidadorDataEvento();
	}

	@Bean
	@Primary
	public ValidadorEvento validadorEventoPrincipal(ValidadorDataEvento validadorData) {
		return validadorData;
	}

	@Bean
	public Map<String, FiltroEventoStrategy> estrategiasFiltro(
			FiltroEventoProximo filtroEventoProximo) {
		Map<String, FiltroEventoStrategy> estrategias = new HashMap<>();
		estrategias.put("proximos", filtroEventoProximo);
		return estrategias;
	}
}