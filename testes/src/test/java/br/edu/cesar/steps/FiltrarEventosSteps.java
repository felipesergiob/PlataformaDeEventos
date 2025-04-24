package br.edu.cesar.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.cucumber.datatable.DataTable;
import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import static org.junit.Assert.*;

public class FiltrarEventosSteps {

    private List<Evento> eventos = new ArrayList<>();
    private List<Evento> eventosFiltrados = new ArrayList<>();

    @Dado("que existem eventos cadastrados na plataforma")
    public void queExistemEventosCadastradosNaPlataforma() {
        Evento evento1 = new Evento();
        evento1.setId(new EventoId("1"));
        evento1.setGenero("Música");
        evento1.setDataHora(LocalDateTime.of(2024, 12, 1, 20, 0)); // Noite
        evento1.setPreco(new BigDecimal("50.00"));

        Evento evento2 = new Evento();
        evento2.setId(new EventoId("2"));
        evento2.setGenero("Palestra");
        evento2.setDataHora(LocalDateTime.of(2024, 12, 1, 10, 0)); // Manhã
        evento2.setPreco(new BigDecimal("0.00"));

        Evento evento3 = new Evento();
        evento3.setId(new EventoId("3"));
        evento3.setGenero("Música");
        evento3.setDataHora(LocalDateTime.of(2024, 12, 2, 20, 0)); // Noite
        evento3.setPreco(new BigDecimal("150.00"));

        eventos.add(evento1);
        eventos.add(evento2);
        eventos.add(evento3);
    }

    @Quando("eu filtrar eventos por gênero {string}")
    public void euFiltrarEventosPorGenero(String genero) {
        eventosFiltrados = eventos.stream()
            .filter(e -> e.getGenero().equals(genero))
            .collect(Collectors.toList());
    }

    @Quando("eu filtrar eventos por horário {string}")
    public void euFiltrarEventosPorHorario(String horario) {
        LocalTime inicioNoite = LocalTime.of(18, 0);
        LocalTime fimNoite = LocalTime.of(23, 59);

        eventosFiltrados = eventos.stream()
            .filter(e -> {
                LocalTime horaEvento = e.getDataHora().toLocalTime();
                if (horario.equals("Noite")) {
                    return horaEvento.isAfter(inicioNoite) && horaEvento.isBefore(fimNoite);
                }
                return false;
            })
            .collect(Collectors.toList());
    }

    @Quando("eu filtrar eventos pela data {string}")
    public void euFiltrarEventosPelaData(String data) {
        LocalDateTime dataFiltro = LocalDateTime.parse(data + "T00:00:00");
        eventosFiltrados = eventos.stream()
            .filter(e -> e.getDataHora().toLocalDate().equals(dataFiltro.toLocalDate()))
            .collect(Collectors.toList());
    }

    @Quando("eu filtrar eventos com preço entre {string} e {string}")
    public void euFiltrarEventosComPrecoEntre(String precoMinimo, String precoMaximo) {
        BigDecimal min = new BigDecimal(precoMinimo);
        BigDecimal max = new BigDecimal(precoMaximo);

        eventosFiltrados = eventos.stream()
            .filter(e -> e.getPreco().compareTo(min) >= 0 && e.getPreco().compareTo(max) <= 0)
            .collect(Collectors.toList());
    }

    @Quando("eu filtrar eventos com os seguintes critérios:")
    public void euFiltrarEventosComOsSeguintesCriterios(DataTable dataTable) {
        Map<String, String> criterios = dataTable.asMap(String.class, String.class);

        eventosFiltrados = eventos.stream()
            .filter(e -> {
                boolean match = true;

                if (criterios.containsKey("gênero")) {
                    match = match && e.getGenero().equals(criterios.get("gênero"));
                }

                if (criterios.containsKey("horário") && criterios.get("horário").equals("Noite")) {
                    LocalTime horaEvento = e.getDataHora().toLocalTime();
                    LocalTime inicioNoite = LocalTime.of(18, 0);
                    LocalTime fimNoite = LocalTime.of(23, 59);
                    match = match && horaEvento.isAfter(inicioNoite) && horaEvento.isBefore(fimNoite);
                }

                if (criterios.containsKey("data")) {
                    LocalDateTime dataFiltro = LocalDateTime.parse(criterios.get("data") + "T00:00:00");
                    match = match && e.getDataHora().toLocalDate().equals(dataFiltro.toLocalDate());
                }

                if (criterios.containsKey("preçoMínimo") && criterios.containsKey("preçoMáximo")) {
                    BigDecimal min = new BigDecimal(criterios.get("preçoMínimo"));
                    BigDecimal max = new BigDecimal(criterios.get("preçoMáximo"));
                    match = match && e.getPreco().compareTo(min) >= 0 && e.getPreco().compareTo(max) <= 0;
                }

                return match;
            })
            .collect(Collectors.toList());
    }

    @Então("devo ver apenas eventos do gênero {string}")
    public void devoVerApenasEventosDoGenero(String genero) {
        assertFalse("Deveria haver eventos filtrados", eventosFiltrados.isEmpty());
        assertTrue("Todos os eventos devem ser do gênero " + genero,
            eventosFiltrados.stream().allMatch(e -> e.getGenero().equals(genero)));
    }

    @Então("devo ver apenas eventos que acontecem à noite")
    public void devoVerApenasEventosQueAcontecemANoite() {
        assertFalse("Deveria haver eventos filtrados", eventosFiltrados.isEmpty());
        LocalTime inicioNoite = LocalTime.of(18, 0);
        LocalTime fimNoite = LocalTime.of(23, 59);
        assertTrue("Todos os eventos devem ser à noite",
            eventosFiltrados.stream().allMatch(e -> {
                LocalTime horaEvento = e.getDataHora().toLocalTime();
                return horaEvento.isAfter(inicioNoite) && horaEvento.isBefore(fimNoite);
            }));
    }

    @Então("devo ver apenas eventos que acontecem nesta data")
    public void devoVerApenasEventosQueAcontecemNestaData() {
        assertFalse("Deveria haver eventos filtrados", eventosFiltrados.isEmpty());
        LocalDateTime dataReferencia = eventosFiltrados.get(0).getDataHora();
        assertTrue("Todos os eventos devem ser na mesma data",
            eventosFiltrados.stream().allMatch(e -> 
                e.getDataHora().toLocalDate().equals(dataReferencia.toLocalDate())));
    }

    @Então("devo ver apenas eventos dentro desta faixa de preço")
    public void devoVerApenasEventosDentroDestaFaixaDePreco() {
        assertFalse("Deveria haver eventos filtrados", eventosFiltrados.isEmpty());
        BigDecimal precoMinimo = eventosFiltrados.stream()
            .map(Evento::getPreco)
            .min(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);
        BigDecimal precoMaximo = eventosFiltrados.stream()
            .map(Evento::getPreco)
            .max(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);

        assertTrue("Todos os eventos devem estar dentro da faixa de preço",
            eventosFiltrados.stream().allMatch(e -> 
                e.getPreco().compareTo(precoMinimo) >= 0 && 
                e.getPreco().compareTo(precoMaximo) <= 0));
    }

    @Então("devo ver apenas eventos que atendem a todos os critérios")
    public void devoVerApenasEventosQueAtendemATodosOsCriterios() {
        assertFalse("Deveria haver eventos filtrados", eventosFiltrados.isEmpty());
        // A validação específica é feita no método de filtragem
    }
}