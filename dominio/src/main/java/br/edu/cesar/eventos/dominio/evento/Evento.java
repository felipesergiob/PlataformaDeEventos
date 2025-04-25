package br.edu.cesar.eventos.dominio.evento;

import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.time.temporal.ChronoUnit;

public class Evento {
    private EventoId id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private String local;
    private BigDecimal preco;
    private String genero;
    private UsuarioId organizadorId;
    private int limiteParticipantes;
    private List<UsuarioId> participantes;
    private List<UsuarioId> interessados;
    private List<UsuarioId> talvezVao;
    private UsuarioId criadorId;
    private String status;
    private int totalConfirmacoes;
    private int totalTalvez;
    private int totalAvaliacoes;
    private double mediaNotas;
    private int totalComentarios;
    private Set<UsuarioId> usuariosConfirmados;
    private String data;
    private List<String> categorias;
    private List<String> tags;
    private boolean verificado;
    private LocalDateTime dataVerificacao;
    private String statusVerificacao;
    private int visualizacoes;
    private List<UsuarioId> visualizadores;
    private Map<String, Integer> metricasEngajamento;

    public Evento() {
        this.participantes = new ArrayList<>();
        this.interessados = new ArrayList<>();
        this.talvezVao = new ArrayList<>();
        this.usuariosConfirmados = new HashSet<>();
        this.categorias = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.visualizadores = new ArrayList<>();
        this.metricasEngajamento = new HashMap<>();
        this.status = "RASCUNHO";
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

    public UsuarioId getOrganizadorId() {
        return organizadorId;
    }

    public void setOrganizadorId(UsuarioId organizadorId) {
        this.organizadorId = organizadorId;
    }

    public int getLimiteParticipantes() {
        return limiteParticipantes;
    }

    public void setLimiteParticipantes(int limiteParticipantes) {
        this.limiteParticipantes = limiteParticipantes;
    }

    public List<UsuarioId> getParticipantes() {
        return participantes;
    }

    public List<UsuarioId> getInteressados() {
        return interessados;
    }

    public List<UsuarioId> getTalvezVao() {
        return talvezVao;
    }

    public boolean adicionarParticipante(UsuarioId participanteId) {
        if (participantes.size() < limiteParticipantes && 
            !participantes.contains(participanteId) &&
            !isFinalizado()) {
            participantes.add(participanteId);
            atualizarMetricasEngajamento("participantes");
            return true;
        }
        return false;
    }

    public void marcarInteresse(UsuarioId interessadoId) {
        if (!interessados.contains(interessadoId) && !isFinalizado()) {
            interessados.add(interessadoId);
            atualizarMetricasEngajamento("interessados");
        }
    }

    public void marcarTalvez(UsuarioId usuarioId) {
        if (!talvezVao.contains(usuarioId) && !isFinalizado()) {
            talvezVao.add(usuarioId);
            totalTalvez++;
            atualizarMetricasEngajamento("talvez");
        }
    }

    public boolean verificarDisponibilidade() {
        return participantes.size() < limiteParticipantes && !isFinalizado();
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

    public Set<UsuarioId> getUsuariosConfirmados() {
        return usuariosConfirmados;
    }

    public void adicionarUsuarioConfirmado(UsuarioId usuarioId) {
        if (!isFinalizado()) {
            usuariosConfirmados.add(usuarioId);
            totalConfirmacoes++;
            atualizarMetricasEngajamento("confirmacoes");
        }
    }

    public void registrarVisualizacao(UsuarioId usuarioId) {
        if (!visualizadores.contains(usuarioId)) {
            visualizadores.add(usuarioId);
            visualizacoes++;
            atualizarMetricasEngajamento("visualizacoes");
        }
    }

    public void adicionarCategoria(String categoria) {
        if (!categorias.contains(categoria)) {
            categorias.add(categoria);
        }
    }

    public void adicionarTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void verificar(String status) {
        this.verificado = true;
        this.statusVerificacao = status;
        this.dataVerificacao = LocalDateTime.now();
    }

    public boolean podeSerEditado() {
        if (isFinalizado()) {
            return false;
        }
        long horasRestantes = ChronoUnit.HOURS.between(LocalDateTime.now(), dataHora);
        return horasRestantes >= 24;
    }

    public boolean isFinalizado() {
        return "FINALIZADO".equals(status);
    }

    public void finalizar() {
        if (!isFinalizado() && dataHora.isBefore(LocalDateTime.now())) {
            this.status = "FINALIZADO";
        }
    }

    private void atualizarMetricasEngajamento(String tipo) {
        metricasEngajamento.merge(tipo, 1, Integer::sum);
    }

    public Map<String, Object> gerarRelatorio() {
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("totalConfirmacoes", totalConfirmacoes);
        relatorio.put("totalParticipantes", participantes.size());
        relatorio.put("totalInteressados", interessados.size());
        relatorio.put("totalTalvez", totalTalvez);
        relatorio.put("mediaNotas", mediaNotas);
        relatorio.put("totalAvaliacoes", totalAvaliacoes);
        relatorio.put("totalComentarios", totalComentarios);
        relatorio.put("visualizacoes", visualizacoes);
        relatorio.put("metricasEngajamento", metricasEngajamento);
        return relatorio;
    }

    public Map<String, Object> gerarRelatorio(String periodo) {
        Map<String, Object> relatorio = gerarRelatorio();
        relatorio.put("periodo", periodo);
        return relatorio;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public LocalDateTime getDataVerificacao() {
        return dataVerificacao;
    }

    public String getStatusVerificacao() {
        return statusVerificacao;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public List<UsuarioId> getVisualizadores() {
        return visualizadores;
    }

    public Map<String, Integer> getMetricasEngajamento() {
        return metricasEngajamento;
    }
}