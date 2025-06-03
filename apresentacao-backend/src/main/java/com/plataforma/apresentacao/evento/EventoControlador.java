package com.plataforma.apresentacao.evento;

import com.plataforma.aplicacao.evento.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoControlador {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoMapper eventoMapper;

    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarEventos() {
        List<Evento> eventos = eventoService.listarEventos();
        List<EventoDTO> dtos = eventos.stream()
                .map(eventoMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarEvento(@PathVariable Long id) {
        Evento evento = eventoService.buscarEvento(id);
        return ResponseEntity.ok(eventoMapper.toDTO(evento));
    }

    @PostMapping
    public ResponseEntity<EventoDTO> criarEvento(@RequestBody EventoDTO dto) {
        Evento evento = eventoService.criarEvento(dto);
        return ResponseEntity.ok(eventoMapper.toDTO(evento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizarEvento(@PathVariable Long id, @RequestBody EventoDTO dto) {
        Evento evento = eventoService.atualizarEvento(id, dto);
        return ResponseEntity.ok(eventoMapper.toDTO(evento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEvento(@PathVariable Long id) {
        eventoService.excluirEvento(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/avaliacoes")
    public ResponseEntity<AvaliacaoDTO> adicionarAvaliacao(@PathVariable Long id, @RequestBody AvaliacaoDTO dto) {
        Avaliacao avaliacao = eventoService.adicionarAvaliacao(id, eventoMapper.toDomain(dto));
        return ResponseEntity.ok(eventoMapper.toDTO(avaliacao));
    }

    @PostMapping("/avaliacoes/{id}/respostas")
    public ResponseEntity<RespostaDTO> adicionarResposta(@PathVariable Long id, @RequestBody RespostaDTO dto) {
        Resposta resposta = eventoService.adicionarResposta(id, eventoMapper.toDomain(dto));
        return ResponseEntity.ok(eventoMapper.toDTO(resposta));
    }

    @PostMapping("/{id}/publicacoes")
    public ResponseEntity<PublicacaoDTO> adicionarPublicacao(@PathVariable Long id, @RequestBody PublicacaoDTO dto) {
        Publicacao publicacao = eventoService.adicionarPublicacao(id, eventoMapper.toDomain(dto));
        return ResponseEntity.ok(eventoMapper.toDTO(publicacao));
    }

    @GetMapping("/organizador/{id}")
    public ResponseEntity<PerfilOrganizadorDTO> buscarPerfilOrganizador(@PathVariable Long id) {
        PerfilOrganizador perfil = eventoService.buscarPerfilOrganizador(id);
        return ResponseEntity.ok(eventoMapper.toDTO(perfil));
    }

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DashboardEventoDTO> buscarDashboardEvento(@PathVariable Long id) {
        DashboardEvento dashboard = eventoService.buscarDashboardEvento(id);
        return ResponseEntity.ok(eventoMapper.toDTO(dashboard));
    }
} 