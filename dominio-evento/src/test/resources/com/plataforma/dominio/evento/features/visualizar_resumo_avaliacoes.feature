# language: pt
Funcionalidade: Visualizar histórico e avaliações de eventos de um organizador
  Como um usuário da plataforma
  Eu quero visualizar o histórico e as avaliações dos eventos organizados por outros usuários
  Para que eu possa decidir se vale a pena participar dos próximos eventos

  Cenário: Visualizar histórico de eventos passados de um organizador
    Dado o organizador "João" possui os seguintes eventos passados:
      | nome         | dataInicio           | dataFim             | participantes | mediaAvaliacao | comentarios                |
      | Evento X     | 2024-05-01T10:00:00 | 2024-05-01T12:00:00 | 50            | 4.5            | "Ótimo evento!", "Muito bom" |
      | Evento Y     | 2024-04-15T14:00:00 | 2024-04-15T16:00:00 | 30            | 3.8            | "Poderia ser melhor"         |
      | Evento Z     | 2024-03-10T09:00:00 | 2024-03-10T11:00:00 | 80            | 4.9            | "Excelente!"                 |
    Quando eu acessar o perfil público do organizador "João"
    Então devo ver a lista de eventos passados organizados por ele
    E para cada evento devo ver a média de avaliação, comentários dos participantes e quantidade de participantes confirmados
