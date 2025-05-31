# language: pt

Funcionalidade: Filtragem de Eventos
  Como usuário da plataforma
  Eu gostaria de filtrar eventos por gênero, horário, data e preço
  Para encontrar eventos que correspondam aos meus interesses e disponibilidade

  Contexto:
    Dado que existem eventos cadastrados na plataforma
      | nome           | genero  | dataInicio           | dataFim             | preco |
      | Show de Rock   | Música  | 2024-06-15T20:00:00 | 2024-06-15T23:00:00 | 50.00 |
      | Palestra Tech  | Palestra| 2024-06-15T14:00:00 | 2024-06-15T16:00:00 | 0.00  |
      | Workshop Art   | Workshop| 2024-06-16T09:00:00 | 2024-06-16T12:00:00 | 30.00 |
      | Show de Jazz   | Música  | 2024-06-15T19:00:00 | 2024-06-15T22:00:00 | 40.00 |
      | Palestra Dev   | Palestra| 2024-06-16T14:00:00 | 2024-06-16T16:00:00 | 25.00 |
    E que os eventos possuem diferentes gêneros, horários, datas e preços

  Cenário: Filtrar eventos por gênero
    Quando eu selecionar o gênero "Música"
    Então devo ver apenas eventos do gênero "Música"
    E a lista deve ser atualizada automaticamente

  Cenário: Filtrar eventos por horário do dia
    Quando eu selecionar o horário "Noite"
    Então devo ver apenas eventos que acontecem no período noturno
    E a lista deve ser atualizada automaticamente

  Cenário: Filtrar eventos por data específica
    Quando eu selecionar a data "15/06/2024"
    Então devo ver apenas eventos que acontecem nesta data
    E a lista deve ser atualizada automaticamente
