package com.plataforma.apresentacao.evento;

import com.plataforma.aplicacao.evento.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class EventoMapper {

    public EventoDTO toDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setTitulo(evento.getTitulo());
        dto.setDescricao(evento.getDescricao());
        dto.setDataHora(evento.getDataHora());
        dto.setLocal(evento.getLocal());
        dto.setPreco(evento.getPreco());
        dto.setGenero(evento.getGenero());
        dto.setOrganizadorId(evento.getOrganizadorId());
        dto.setNumeroParticipantes(evento.getNumeroParticipantes());
        dto.setDestaque(evento.isDestaque());
        return dto;
    }

    public AvaliacaoDTO toDTO(Avaliacao avaliacao) {
        AvaliacaoDTO dto = new AvaliacaoDTO();
        dto.setId(avaliacao.getId());
        dto.setEventoId(avaliacao.getEventoId());
        dto.setUsuarioId(avaliacao.getUsuarioId());
        dto.setNota(avaliacao.getNota());
        dto.setComentario(avaliacao.getComentario());
        dto.setDataAvaliacao(avaliacao.getDataAvaliacao());
        dto.setRespostas(avaliacao.getRespostas().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    public RespostaDTO toDTO(Resposta resposta) {
        RespostaDTO dto = new RespostaDTO();
        dto.setId(resposta.getId());
        dto.setAvaliacaoId(resposta.getAvaliacaoId());
        dto.setAutorId(resposta.getAutorId());
        dto.setConteudo(resposta.getConteudo());
        dto.setDataResposta(resposta.getDataResposta());
        return dto;
    }

    public PublicacaoDTO toDTO(Publicacao publicacao) {
        PublicacaoDTO dto = new PublicacaoDTO();
        dto.setId(publicacao.getId());
        dto.setEventoId(publicacao.getEventoId());
        dto.setAutorId(publicacao.getAutorId());
        dto.setTitulo(publicacao.getTitulo());
        dto.setConteudo(publicacao.getConteudo());
        dto.setFotos(publicacao.getFotos());
        dto.setDataPublicacao(publicacao.getDataPublicacao());
        return dto;
    }

    public PerfilOrganizadorDTO toDTO(PerfilOrganizador perfil) {
        PerfilOrganizadorDTO dto = new PerfilOrganizadorDTO();
        dto.setId(perfil.getId());
        dto.setNome(perfil.getNome());
        dto.setEmail(perfil.getEmail());
        dto.setEventosPassados(perfil.getEventosPassados().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
        dto.setAvaliacoes(perfil.getAvaliacoes().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
        dto.setMediaAvaliacoes(perfil.getMediaAvaliacoes());
        dto.setTotalEventos(perfil.getTotalEventos());
        dto.setTotalParticipantes(perfil.getTotalParticipantes());
        return dto;
    }

    public DashboardEventoDTO toDTO(DashboardEvento dashboard) {
        DashboardEventoDTO dto = new DashboardEventoDTO();
        dto.setNomeEvento(dashboard.getNomeEvento());
        dto.setTotalInscritos(dashboard.getTotalInscritos());
        dto.setMediaAvaliacoes(dashboard.getMediaAvaliacoes());
        dto.setQuantidadeAvaliacoes(dashboard.getQuantidadeAvaliacoes());
        dto.setComentarios(dashboard.getComentarios());
        dto.setDistribuicaoNotas(dashboard.getDistribuicaoNotas());
        dto.setPublicacoes(dashboard.getPublicacoes().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    public Avaliacao toDomain(AvaliacaoDTO dto) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(dto.getId());
        avaliacao.setEventoId(dto.getEventoId());
        avaliacao.setUsuarioId(dto.getUsuarioId());
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setDataAvaliacao(dto.getDataAvaliacao());
        avaliacao.setRespostas(dto.getRespostas().stream()
                .map(this::toDomain)
                .collect(Collectors.toList()));
        return avaliacao;
    }

    public Resposta toDomain(RespostaDTO dto) {
        Resposta resposta = new Resposta();
        resposta.setId(dto.getId());
        resposta.setAvaliacaoId(dto.getAvaliacaoId());
        resposta.setAutorId(dto.getAutorId());
        resposta.setConteudo(dto.getConteudo());
        resposta.setDataResposta(dto.getDataResposta());
        return resposta;
    }

    public Publicacao toDomain(PublicacaoDTO dto) {
        Publicacao publicacao = new Publicacao();
        publicacao.setId(dto.getId());
        publicacao.setEventoId(dto.getEventoId());
        publicacao.setAutorId(dto.getAutorId());
        publicacao.setTitulo(dto.getTitulo());
        publicacao.setConteudo(dto.getConteudo());
        publicacao.setFotos(dto.getFotos());
        publicacao.setDataPublicacao(dto.getDataPublicacao());
        return publicacao;
    }
} 