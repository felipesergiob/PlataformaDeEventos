# language: pt
Funcionalidade: Visualizar Calendário Pessoal
  Como um usuário da plataforma
  Eu quero ver no calendário os eventos que salvei ou confirmei presença
  Para organizar minha agenda

  Cenário: Visualizar eventos salvos no calendário
    Dado que eu sou um usuário da plataforma
    E tenho eventos salvos como interesse
    Quando eu acessar meu calendário pessoal
    Então devo ver os eventos salvos destacados com a cor azul

  Cenário: Visualizar eventos confirmados no calendário
    Dado que eu sou um usuário da plataforma
    E tenho eventos confirmados
    Quando eu acessar meu calendário pessoal
    Então devo ver os eventos confirmados destacados com a cor verde

  Cenário: Visualizar eventos marcados como "talvez" no calendário
    Dado que eu sou um usuário da plataforma
    E tenho eventos marcados como "talvez"
    Quando eu acessar meu calendário pessoal
    Então devo ver os eventos marcados como "talvez" destacados com a cor amarela

  Cenário: Visualizar detalhes de um evento no calendário
    Dado que eu sou um usuário da plataforma
    E tenho eventos no meu calendário
    Quando eu clicar em um evento no calendário
    Então devo ver os detalhes do evento:
      | campo     | valor esperado |
      | nome      | Show de Rock   |
      | local     | Arena Rock     |
      | data      | 2024-12-01     |
      | horário   | 20:00          |
      | descrição | Show da banda X |
      | link      | /eventos/123   |

  Cenário: Alternar entre visualizações do calendário
    Dado que eu sou um usuário da plataforma
    E tenho eventos no meu calendário
    Quando eu alternar a visualização do calendário para "semanal"
    Então devo ver os eventos organizados por "semana"
    Quando eu alternar a visualização do calendário para "diária"
    Então devo ver os eventos organizados por "dia"
    Quando eu alternar a visualização do calendário para "mensal"
    Então devo ver os eventos organizados por "mês" 