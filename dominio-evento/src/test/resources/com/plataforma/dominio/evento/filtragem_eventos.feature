# language: pt

Funcionalidade: Filtragem de Eventos
  Como um usuário da plataforma
  Eu quero poder filtrar eventos por diferentes critérios
  Para encontrar eventos que me interessam

  Cenário: Filtrar eventos por gênero
    Dado os seguintes eventos:
      | nome           | descricao | dataInicio           | dataFim              | local    | capacidade | organizador | categoria | genero | valor |
      | Show de Rock   | Show      | 2024-04-01T20:00:00 | 2024-04-01T23:00:00 | Bar XYZ  | 100        | João        | Música    | Rock    | 50.00 |
      | Show de Samba  | Show      | 2024-04-02T20:00:00 | 2024-04-02T23:00:00 | Bar ABC  | 100        | Maria       | Música    | Samba   | 40.00 |
    Quando eu filtrar eventos por gênero "Rock"
    Então devo ver 1 eventos
    E o evento deve ter o nome "Show de Rock"

  Cenário: Filtrar eventos por horário
    Dado os seguintes eventos:
      | nome           | descricao | dataInicio           | dataFim              | local    | capacidade | organizador | categoria | genero | valor |
      | Show de Rock   | Show      | 2024-04-01T20:00:00 | 2024-04-01T23:00:00 | Bar XYZ  | 100        | João        | Música    | Rock    | 50.00 |
      | Show de Samba  | Show      | 2024-04-01T21:00:00 | 2024-04-01T23:00:00 | Bar ABC  | 100        | Maria       | Música    | Samba   | 40.00 |
    Quando eu filtrar eventos por horário "2024-04-01T20:00:00"
    Então devo ver 1 eventos
    E o evento deve ter o nome "Show de Rock"

  Cenário: Filtrar eventos por data
    Dado os seguintes eventos:
      | nome           | descricao | dataInicio           | dataFim              | local    | capacidade | organizador | categoria | genero | valor |
      | Show de Rock   | Show      | 2024-04-01T20:00:00 | 2024-04-01T23:00:00 | Bar XYZ  | 100        | João        | Música    | Rock    | 50.00 |
      | Show de Samba  | Show      | 2024-04-02T20:00:00 | 2024-04-02T23:00:00 | Bar ABC  | 100        | Maria       | Música    | Samba   | 40.00 |
    Quando eu filtrar eventos por data "2024-04-01T00:00:00"
    Então devo ver 1 eventos
    E o evento deve ter o nome "Show de Rock"

  Cenário: Filtrar eventos por preço máximo
    Dado os seguintes eventos:
      | nome           | descricao | dataInicio           | dataFim              | local    | capacidade | organizador | categoria | genero | valor |
      | Show de Rock   | Show      | 2024-04-01T20:00:00 | 2024-04-01T23:00:00 | Bar XYZ  | 100        | João        | Música    | Rock    | 50.00 |
      | Show de Samba  | Show      | 2024-04-02T20:00:00 | 2024-04-02T23:00:00 | Bar ABC  | 100        | Maria       | Música    | Samba   | 40.00 |
    Quando eu filtrar eventos por preço máximo 45.00
    Então devo ver 2 eventos
    E o evento deve ter o nome "Show de Samba" 