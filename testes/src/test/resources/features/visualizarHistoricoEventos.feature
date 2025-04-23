# language: pt
Funcionalidade: Visualizar histórico de eventos
  Como organizador
  Quero visualizar o histórico dos eventos que criei
  Para analisar o desempenho e engajamento dos participantes

  Cenário: Visualizar histórico de eventos com avaliações
    Dado que sou um usuário da plataforma
    E existe um organizador com eventos passados
    Quando acesso o perfil do organizador
    Entao devo ver a lista de eventos passados com:
      | Evento         | Data       | Média | Participantes | Comentários |
      | Show de Rock   | 01/01/2023 | 4.5   | 100          | 25          |
      | Festival de Jazz | 15/02/2023 | 4.8   | 150          | 30          |

    Quando clico em um evento específico
    Entao devo ver os detalhes do evento no histórico:
      | Nome                    | Show de Rock |
      | Data                    | 01/01/2023   |
      | Média de Notas          | 4.5          |
      | Total de Avaliações     | 80           |
      | Total de Comentários    | 25           |
      | Participantes Confirmados | 100         |
      | Taxa de Engajamento     | 80%          |

    E devo ver os comentários dos participantes:
      | Usuário | Comentário                                |
      | joao    | Show incrível, organização impecável!     |
      | maria   | Adorei o evento, superou minhas expectativas! |

  Cenário: Visualizar histórico de eventos sem avaliações
    Dado que sou um usuário da plataforma
    E existe um organizador com eventos passados sem avaliações
    Quando acesso o perfil do organizador
    Entao devo ver a lista de eventos passados com:
      | Evento         | Data       | Média | Participantes | Comentários |
      | Show de Rock   | 01/01/2023 | 0.0   | 100          | 0           |
      | Festival de Jazz | 15/02/2023 | 0.0   | 150          | 0           |

    Quando clico em um evento específico
    Entao devo ver os detalhes do evento no histórico:
      | Nome                    | Show de Rock |
      | Data                    | 01/01/2023   |
      | Média de Notas          | 0.0          |
      | Total de Avaliações     | 0            |
      | Total de Comentários    | 0            |
      | Participantes Confirmados | 100         |
      | Taxa de Engajamento     | 0%           | 