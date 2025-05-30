package com.plataforma.dominio.evento;

import com.plataforma.avaliacao.Avaliacao;
import com.plataforma.avaliacao.AvaliacaoRepository;
import com.plataforma.avaliacao.AvaliacaoService;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class AvaliarEventoSteps {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    private Evento evento;
    private Avaliacao avaliacao;
    private EventoId eventoId;
    private UsuarioId usuarioId;
    private Exception exception;

    @Dado("que existe um evento {string}")
    public void que_existe_um_evento(String nomeEvento) {
        eventoId = EventoId.de(1);
        evento = Mockito.mock(Evento.class);
        Mockito.when(evento.getNome()).thenReturn(nomeEvento);
        Mockito.when(eventoRepository.obter(eventoId)).thenReturn(evento);
    }

    @Dado("que o evento já terminou")
    public void que_o_evento_ja_terminou() {
        Mockito.when(evento.jaOcorreu()).thenReturn(true);
        Mockito.when(evento.getDataFim()).thenReturn(LocalDateTime.now().minusDays(1));
    }

    @Dado("que eu confirmei presença neste evento")
    public void que_eu_confirmei_presenca_neste_evento() {
        usuarioId = UsuarioId.de(1);
        Mockito.when(avaliacaoRepository.existeInscricaoConfirmada(eventoId, usuarioId)).thenReturn(true);
    }

    @Quando("eu avalio o evento com nota {int}")
    public void eu_avalio_o_evento_com_nota(Integer nota) {
        avaliacao = Mockito.mock(Avaliacao.class);
        Mockito.when(avaliacao.getNota()).thenReturn(nota);
        Mockito.when(avaliacao.getComentario()).thenReturn("Excelente workshop, aprendi muito sobre programação!");
        try {
            avaliacaoService.avaliarEvento(eventoId, avaliacao, usuarioId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Quando("comento {string}")
    public void comento(String comentario) {
        Mockito.when(avaliacao.getComentario()).thenReturn(comentario);
    }

    @Entao("a avaliação deve ser registrada com sucesso")
    public void a_avaliacao_deve_ser_registrada_com_sucesso() {
        Mockito.verify(avaliacaoRepository, Mockito.times(1)).salvar(avaliacao);
        Assert.assertNull("Não deve haver exceção", exception);
    }

    @Entao("o evento deve ter uma nova avaliação")
    public void o_evento_deve_ter_uma_nova_avaliacao() {
        Mockito.verify(avaliacaoRepository, Mockito.times(1)).salvar(avaliacao);
    }
}
