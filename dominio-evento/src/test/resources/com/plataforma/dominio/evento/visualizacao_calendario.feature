# language: pt

Funcionalidade: Visualização de eventos no calendário
  Como um usuário
  Eu quero ver meus eventos salvos ou confirmados
  Para que eu possa organizar minha agenda

  Cenário: Visualizar eventos confirmados no calendário
    Dado que existem os seguintes eventos:
      | nome | descricao | dataInicio | dataFim | local | capacidade | organizador | categoria | genero | valor |
      | Show de Rock | Show de rock nacional | 2024-06-01T20:00:00 | 2024-06-01T23:00:00 | Casa de Shows | 100 | Produtora XYZ | Show | Rock | 50.00 |
      | Festival de Jazz | Festival de jazz internacional | 2024-06-15T19:00:00 | 2024-06-15T22:00:00 | Teatro Municipal | 200 | Jazz Productions | Festival | Jazz | 80.00 |
    E que o usuário tem as seguintes inscrições:
      | eventoIndex | status |
      | 1 | CONFIRMADA |
      | 2 | PENDENTE |
    Quando o usuário visualiza seu calendário
    Então o usuário deve ver 1 eventos confirmados

  Cenário: Filtrar eventos do calendário por período
    Dado que existem os seguintes eventos:
      | nome | descricao | dataInicio | dataFim | local | capacidade | organizador | categoria | genero | valor |
      | Show de Rock | Show de rock nacional | 2024-06-01T20:00:00 | 2024-06-01T23:00:00 | Casa de Shows | 100 | Produtora XYZ | Show | Rock | 50.00 |
      | Festival de Jazz | Festival de jazz internacional | 2024-06-15T19:00:00 | 2024-06-15T22:00:00 | Teatro Municipal | 200 | Jazz Productions | Festival | Jazz | 80.00 |
    E que o usuário tem as seguintes inscrições:
      | eventoIndex | status |
      | 1 | CONFIRMADA |
      | 2 | CONFIRMADA |
    Quando o usuário filtra os eventos do período "2024-06-01" até "2024-06-10"
    Então o usuário deve ver 1 eventos no período

  Cenário: Visualizar eventos salvos no calendário
    Dado que existem os seguintes eventos:
      | nome | descricao | dataInicio | dataFim | local | capacidade | organizador | categoria | genero | valor |
      | Show de Rock | Show de rock nacional | 2024-06-01T20:00:00 | 2024-06-01T23:00:00 | Casa de Shows | 100 | Produtora XYZ | Show | Rock | 50.00 |
    Quando o usuário visualiza seus eventos salvos
    Então o usuário deve ver o evento "Show de Rock" 