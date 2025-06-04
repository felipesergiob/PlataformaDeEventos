package com.plataforma.dominio.evento;

import com.plataforma.Publicacao.Publicacao;
import com.plataforma.compartilhado.PublicacaoId;
import com.plataforma.Publicacao.PublicacaoRepository;
import com.plataforma.Publicacao.PublicacaoService;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PublicacaoSobreOEventoSteps {

    private PublicacaoService publicacaoService;
    private PublicacaoRepository publicacaoRepository;
    private EventoRepository eventoRepository;

    private Publicacao publicacao;
    private UsuarioId usuarioId;
    private EventoId eventoId;
    private List<Publicacao> publicacoesSalvas = new ArrayList<>();

    @Dado("que eu sou um usuário logado na plataforma")
    public void que_eu_sou_um_usuario_logado_na_plataforma() {
        // Inicializa os mocks
        publicacaoRepository = Mockito.mock(PublicacaoRepository.class);
        eventoRepository = Mockito.mock(EventoRepository.class);
        publicacaoService = new PublicacaoService(publicacaoRepository, eventoRepository);

        usuarioId = UsuarioId.de(1);
    }

    @E("que eu participei de um evento")
    public void que_eu_participei_de_um_evento() {
        eventoId = EventoId.de(1);
        Evento evento = Mockito.mock(Evento.class);
        Mockito.when(eventoRepository.obter(eventoId)).thenReturn(evento);
    }

    @Quando("eu acessar a página do evento")
    public void eu_acessar_a_pagina_do_evento() {
    }

    @E("clicar no botão {string}")
    public void clicar_no_botao(String botao) {
    }

    @E("preencher o título do resumo")
    public void preencher_o_titulo_do_resumo() {
        PublicacaoId publicacaoId = PublicacaoId.gerar();
        publicacao = new Publicacao(
                publicacaoId,
                usuarioId,
                eventoId,
                "Conteúdo do resumo");
    }

    @E("escrever o conteúdo do resumo")
    public void escrever_o_conteudo_do_resumo() {
    }

    @E("clicar em {string}")
    public void clicar_em(String botao) {
        publicacaoService.salvar(publicacao, usuarioId);
    }

    @Entao("a Publicação deve ser publicado com sucesso")
    public void a_publicacao_deve_ser_publicado_com_sucesso() {
        Mockito.verify(publicacaoRepository).salvar(Mockito.any(Publicacao.class));
    }

    @E("deve aparecer na página do evento")
    public void deve_aparecer_na_pagina_do_evento() {
        List<Publicacao> publicacoes = new ArrayList<>();
        publicacoes.add(publicacao);
        Mockito.when(publicacaoRepository.listarPorEvento(eventoId)).thenReturn(publicacoes);

        List<Publicacao> publicacoesRetornadas = publicacaoService.listarPorEvento(eventoId);
        Assert.assertFalse("Deve existir pelo menos uma publicação", publicacoesRetornadas.isEmpty());
    }

    @E("outros usuários devem poder visualizar a Publicação")
    public void outros_usuarios_devem_poder_visualizar_a_publicacao() {
        Mockito.when(publicacaoRepository.obter(publicacao.getId())).thenReturn(publicacao);

        Publicacao publicacaoSalva = publicacaoService.obter(publicacao.getId());
        Assert.assertNotNull("A publicação deve ser encontrada", publicacaoSalva);
        Assert.assertEquals("O conteúdo deve ser o mesmo", publicacao.getConteudo(), publicacaoSalva.getConteudo());
    }
}