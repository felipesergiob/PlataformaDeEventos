# language: pt
Funcionalidade: Visualizar Relatório de Evento
  Como usuário da plataforma
  Quero visualizar relatórios de eventos que criei e já aconteceram
  Para ter insights sobre o desempenho e engajamento dos participantes

  Cenário: Visualizar relatório de evento com dados completos
    Dado que sou um usuário logado na plataforma e criador de eventos
    E tenho um evento encerrado que criei para visualizar relatório
    Quando acesso a página de relatórios do evento
    Então devo ver as seguintes informações:
      | totalConfirmacoes | 50  |
      | totalTalvez      | 20  |
      | totalAvaliacoes  | 40  |
      | mediaNotas       | 4.5 |
      | totalComentarios | 30  |

  Cenário: Visualizar relatório de evento sem avaliações
    Dado que sou um usuário logado na plataforma e criador de eventos
    E tenho um evento encerrado que criei para visualizar relatório
    E o evento não recebeu avaliações
    Quando acesso a página de relatórios do evento
    Então devo ver as seguintes informações:
      | totalConfirmacoes | 30 |
      | totalTalvez      | 10 |
      | totalAvaliacoes  | 0  |
      | mediaNotas       | 0  |
      | totalComentarios | 5  |

  Cenário: Visualizar relatório de evento com filtro por período
    Dado que sou um usuário logado na plataforma e criador de eventos
    E tenho um evento encerrado que criei para visualizar relatório
    Quando acesso a página de relatórios do evento
    E seleciono o período "Últimos 30 dias"
    Então devo ver as métricas filtradas para o período selecionado 