package br.edu.cesar.eventos.dominio.relato;

import br.edu.cesar.eventos.dominio.usuario.Usuario;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Relato {
    private String id;
    private String titulo;
    private String conteudo;
    private UsuarioId autorId;
    private String eventoId;
    private LocalDateTime dataCriacao;
    private boolean publicado;
    private LocalDateTime dataPublicacao;
    private int visualizacoes;
    private List<UsuarioId> visualizadores;
    private List<Foto> fotos;
    private List<Comentario> comentarios;
    private boolean moderado;
    private String statusModeracao;
    private String motivoRejeicao;

    public Relato() {
        this.dataCriacao = LocalDateTime.now();
        this.visualizacoes = 0;
        this.visualizadores = new ArrayList<>();
        this.fotos = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.moderado = false;
        this.statusModeracao = "PENDENTE";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public UsuarioId getAutorId() {
        return autorId;
    }

    public void setAutorId(UsuarioId autorId) {
        this.autorId = autorId;
    }
    
    // For backward compatibility
    @Deprecated
    public Usuario getAutor() {
        return null; // This is deprecated, use getAutorId() instead
    }

    // For backward compatibility
    @Deprecated
    public void setAutor(Usuario autor) {
        if (autor != null) {
            this.autorId = autor.getId();
        }
    }

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public List<UsuarioId> getVisualizadores() {
        return visualizadores;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public boolean isModerado() {
        return moderado;
    }

    public String getStatusModeracao() {
        return statusModeracao;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void adicionarFoto(Foto foto) {
        if (fotos.size() < 10) {
            fotos.add(foto);
        }
    }

    public void removerFoto(Foto foto) {
        fotos.remove(foto);
    }

    public void publicar() {
        if (isValid()) {
            publicado = true;
            dataPublicacao = LocalDateTime.now();
        }
    }

    public void registrarVisualizacao(UsuarioId usuarioId) {
        if (publicado && !visualizadores.contains(usuarioId)) {
            visualizacoes++;
            visualizadores.add(usuarioId);
        }
    }
    
    // For backward compatibility
    @Deprecated
    public void registrarVisualizacao(Usuario usuario) {
        if (usuario != null) {
            registrarVisualizacao(usuario.getId());
        }
    }

    public void adicionarComentario(Comentario comentario) {
        if (publicado && comentario.isValid()) {
            comentarios.add(comentario);
        }
    }

    public void moderar(String status, String motivo) {
        if (status.equals("APROVADO") || status.equals("REJEITADO")) {
            moderado = true;
            statusModeracao = status;
            motivoRejeicao = motivo;
        }
    }

    public boolean podeSerEditado() {
        return !publicado || (publicado && dataCriacao.plusHours(24).isAfter(LocalDateTime.now()));
    }

    public boolean isValid() {
        return titulo != null && !titulo.isEmpty() &&
               conteudo != null && !conteudo.isEmpty() &&
               autorId != null && eventoId != null && !eventoId.isEmpty() &&
               fotos.size() <= 10;
    }

    public boolean isAprovado() {
        return moderado && statusModeracao.equals("APROVADO");
    }

    public boolean isRejeitado() {
        return moderado && statusModeracao.equals("REJEITADO");
    }

    public void validar() {
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("O título é obrigatório");
        }
        if (conteudo == null || conteudo.isEmpty()) {
            throw new IllegalArgumentException("O conteúdo é obrigatório");
        }
        if (eventoId == null) {
            throw new IllegalArgumentException("O evento é obrigatório");
        }
        if (autorId == null) {
            throw new IllegalArgumentException("O autor é obrigatório");
        }
    }
}