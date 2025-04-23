# language: pt
Funcionalidade: Filtrar Eventos
  Como um usuário da plataforma
  Eu quero filtrar eventos por gênero, horário, data e preço
  Para encontrar os eventos que me interessam

  Cenário: Filtrar eventos por gênero
    Dado que existem eventos cadastrados na plataforma
    Quando eu filtrar eventos por gênero "Música"
    Então devo ver apenas eventos do gênero "Música"

  Cenário: Filtrar eventos por horário
    Dado que existem eventos cadastrados na plataforma
    Quando eu filtrar eventos por horário "Noite"
    Então devo ver apenas eventos que acontecem à noite

  Cenário: Filtrar eventos por data
    Dado que existem eventos cadastrados na plataforma
    Quando eu filtrar eventos pela data "2024-12-01"
    Então devo ver apenas eventos que acontecem nesta data

  Cenário: Filtrar eventos por faixa de preço
    Dado que existem eventos cadastrados na plataforma
    Quando eu filtrar eventos com preço entre "0" e "100"
    Então devo ver apenas eventos dentro desta faixa de preço

  Cenário: Filtrar eventos com múltiplos critérios
    Dado que existem eventos cadastrados na plataforma
    Quando eu filtrar eventos com os seguintes critérios:
      | gênero | Música |
      | horário | Noite |
      | data | 2024-12-01 |
      | preçoMínimo | 0 |
      | preçoMáximo | 100 |
    Então devo ver apenas eventos que atendem a todos os critérios 