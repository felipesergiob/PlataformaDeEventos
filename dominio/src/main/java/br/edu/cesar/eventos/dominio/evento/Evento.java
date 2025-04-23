package br.edu.cesar.eventos.dominio.evento;

import br.edu.cesar.eventos.dominio.usuario.Usuario;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento {
    private EventoId id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private String local;
    private BigDecimal preco;
    private String genero;
    private Usuario organizador;
    private int limiteParticipantes;
    private List<Usuario> participantes;
    private List<Usuario> interessados;
    private List<Usuario> talvezVao;

    public Evento() {
        this.participantes = new ArrayList<>();
        this.interessados = new ArrayList<>();
        this.talvezVao = new ArrayList<>();
    }

    public EventoId getId() {
        return id;
    }

    public void setId(EventoId id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }

    public int getLimiteParticipantes() {
        return limiteParticipantes;
    }

    public void setLimiteParticipantes(int limiteParticipantes) {
        this.limiteParticipantes = limiteParticipantes;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    public List<Usuario> getInteressados() {
        return interessados;
    }

    public List<Usuario> getTalvezVao() {
        return talvezVao;
    }

    public boolean adicionarParticipante(Usuario participante) {
        if (participantes.size() < limiteParticipantes && !participantes.contains(participante)) {
            participantes.add(participante);
            return true;
        }
        return false;
    }

    public void marcarInteresse(Usuario interessado) {
        if (!interessados.contains(interessado)) {
            interessados.add(interessado);
        }
    }

    public void marcarTalvez(Usuario usuario) {
        if (!talvezVao.contains(usuario)) {
            talvezVao.add(usuario);
        }
    }

    public boolean verificarDisponibilidade() {
        return participantes.size() < limiteParticipantes;
    }
} 