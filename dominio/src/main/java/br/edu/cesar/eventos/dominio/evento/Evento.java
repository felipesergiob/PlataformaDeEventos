package br.edu.cesar.eventos.dominio.evento;

import br.edu.cesar.eventos.dominio.usuario.Usuario;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

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
    private UsuarioId criadorId;
    private String status;
    private int totalConfirmacoes;
    private int totalTalvez;
    private int totalAvaliacoes;
    private double mediaNotas;
    private int totalComentarios;
    private Set<Usuario> usuariosConfirmados;
    private String data;

    public Evento() {
        this.participantes = new ArrayList<>();
        this.interessados = new ArrayList<>();
        this.talvezVao = new ArrayList<>();
        this.usuariosConfirmados = new HashSet<>();
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

    public UsuarioId getCriadorId() {
        return criadorId;
    }

    public void setCriadorId(UsuarioId criadorId) {
        this.criadorId = criadorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalConfirmacoes() {
        return totalConfirmacoes;
    }

    public void setTotalConfirmacoes(int totalConfirmacoes) {
        this.totalConfirmacoes = totalConfirmacoes;
    }

    public int getTotalTalvez() {
        return totalTalvez;
    }

    public void setTotalTalvez(int totalTalvez) {
        this.totalTalvez = totalTalvez;
    }

    public int getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public void setTotalAvaliacoes(int totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }

    public double getMediaNotas() {
        return mediaNotas;
    }

    public void setMediaNotas(double mediaNotas) {
        this.mediaNotas = mediaNotas;
    }

    public int getTotalComentarios() {
        return totalComentarios;
    }

    public void setTotalComentarios(int totalComentarios) {
        this.totalComentarios = totalComentarios;
    }

    public Set<Usuario> getUsuariosConfirmados() {
        return usuariosConfirmados;
    }

    public void adicionarUsuarioConfirmado(Usuario usuario) {
        this.usuariosConfirmados.add(usuario);
        this.totalConfirmacoes = this.usuariosConfirmados.size();
    }

    public boolean isFinalizado() {
        return "FINALIZADO".equals(status);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String, Object> gerarRelatorio() {
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("totalConfirmacoes", totalConfirmacoes);
        relatorio.put("totalTalvez", totalTalvez);
        relatorio.put("totalAvaliacoes", totalAvaliacoes);
        relatorio.put("mediaNotas", mediaNotas);
        relatorio.put("totalComentarios", totalComentarios);
        return relatorio;
    }

    public Map<String, Object> gerarRelatorio(String periodo) {
        Map<String, Object> relatorio = gerarRelatorio();
        relatorio.put("periodo", periodo);
        // Aqui seria implementada a lógica de filtragem por período
        return relatorio;
    }
} 