Feature: Visualização de Histórico de Eventos no Perfil
  Como usuário da plataforma
  Eu quero visualizar o histórico e notas de eventos criados por outros usuários
  Para avaliar a credibilidade dos organizadores e decidir sobre participar de futuros eventos

  Scenario: Visualizar histórico de eventos passados e avaliações
    Given que existem eventos passados organizados pelo usuário "1"
    And que o evento "Workshop de Programação" foi organizado pelo usuário "1"
    And que o evento "Workshop de Programação" aconteceu em "01/01/2024"
    And que o evento "Workshop de Programação" recebeu 3 avaliações com notas 4, 5 e 5
    And que o evento "Workshop de Programação" possui os seguintes comentários:
      | autor        | comentario                                    |
      | Maria Santos | "Excelente workshop, muito conteúdo prático!" |
      | Pedro Costa  | "Ótima experiência, recomendo!"               |
    When eu acesso o perfil do usuário "1"
    Then eu devo ver a lista de eventos passados organizados por ele
    And eu devo ver que o evento "Workshop de Programação" tem uma média de avaliação de 4.67
    And eu devo ver os comentários dos participantes do evento "Workshop de Programação"
