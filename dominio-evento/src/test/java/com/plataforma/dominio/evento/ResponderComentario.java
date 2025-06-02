package com.plataforma.dominio.evento;

import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import com.plataforma.comentario.Comentario;
import com.plataforma.comentario.ComentarioService;
import com.plataforma.comentario.ComentarioRepository;
import com.plataforma.compartilhado.ComentarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import io.cucumber.java.pt.*;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class ResponderComentario {

	@Autowired
	private ComentarioService comentarioService;

	private ComentarioRepository comentarioRepository;
	private Comentario comentarioOriginal;
	private Comentario resposta;
	private Exception excecao;
	private EventoId eventoId;
	private Evento evento;
	private EventoRepository eventoRepository;

	@Dado("que existe um evento para comentários {string}")
	public void que_existe_um_evento_para_comentarios(String nomeEvento) {
		eventoId = EventoId.de(1);
		evento = Mockito.mock(Evento.class);
		eventoRepository = Mockito.mock(EventoRepository.class);
		Mockito.when(evento.getNome()).thenReturn(nomeEvento);
		Mockito.when(eventoRepository.obter(eventoId)).thenReturn(evento);
	}

	@E("que o usuário {string} é organizador deste evento")
	public void que_o_usuario_e_organizador_deste_evento(String nomeOrganizador) {
		comentarioRepository = Mockito.mock(ComentarioRepository.class);
		comentarioService = new ComentarioService(comentarioRepository);
	}

	@E("que existe um comentário de {string} no evento dizendo {string}")
	public void que_existe_um_comentario_de_no_evento_dizendo(String nomeAutor, String texto) {
		UsuarioId autorId = UsuarioId.de(2);
		comentarioOriginal = new Comentario(ComentarioId.novo(), texto, eventoId, autorId);

		Mockito.when(comentarioRepository.existe(comentarioOriginal.getId())).thenReturn(true);
		Mockito.when(comentarioRepository.obter(comentarioOriginal.getId())).thenReturn(comentarioOriginal);
	}

	@Quando("o organizador {string} responde ao comentário com {string}")
	public void o_organizador_responde_ao_comentario_com(String nomeOrganizador, String textoResposta) {
		try {
			UsuarioId organizadorId = UsuarioId.de(1);
			Mockito.when(comentarioRepository.ehOrganizadorDoEvento(organizadorId, comentarioOriginal.getEventoId()))
					.thenReturn(true);

			resposta = comentarioService.responderComentario(textoResposta, comentarioOriginal.getId(), organizadorId);
		} catch (Exception e) {
			excecao = e;
		}
	}

	@Entao("a resposta deve ser exibida abaixo do comentário original")
	public void a_resposta_deve_ser_exibida_abaixo_do_comentario_original() {
		Assert.assertNotNull("A resposta não deve ser nula", resposta);
		Assert.assertEquals("A resposta deve estar vinculada ao comentário original",
				comentarioOriginal.getId(), resposta.getComentarioPaiId());
	}

	@E("deve mostrar o nome do organizador {string}")
	public void deve_mostrar_o_nome_do_organizador(String nomeOrganizador) {
		Assert.assertNotNull("O ID do autor da resposta não deve ser nulo", resposta.getAutorId());
	}

	@E("deve mostrar a data e hora da resposta")
	public void deve_mostrar_a_data_e_hora_da_resposta() {
		Assert.assertNotNull("A data de criação da resposta não deve ser nula", resposta.getDataCriacao());
	}
}
