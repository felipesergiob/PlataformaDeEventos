# language: pt
Funcionalidade: Visualização de Perfil Público
  Como usuário da plataforma
  Eu quero acessar o perfil público de outros organizadores
  Para conhecer seu histórico de eventos e publicações

  Contexto:
    Dado que existe um usuário organizador

  Cenário: Visualizar perfil com eventos passados e publicações
    E que ele organizou os seguintes eventos passados:
      | id | nome           | descricao        | dataInicio           | dataFim             | local      | genero | valor |
      | 1  | Show de Rock   | Show incrível    | 2023-01-01T20:00:00  | 2023-01-01T23:00:00 | Bar Rock   | Música | 50.00 |
      | 2  | Festa Eletro   | Festa eletrônica | 2023-02-01T22:00:00  | 2023-02-02T06:00:00 | Club Dance | Música | 80.00 |

    E que existem as seguintes avaliações para os eventos:
      | nota | comentario                    |
      | 5    | Evento incrível!             |
      | 4    | Muito bom, recomendo!        |
      | 5    | Melhor evento do ano!        |

    E que ele fez as seguintes publicações:
      | id | eventoId | conteudo                    |
      | 1  | 1        | Fotos do show de rock!     |
      | 2  | 2        | Resumo da festa eletro!    |

    Quando eu acessar o perfil público do usuário
    Então devo ver a lista de eventos passados organizados por ele
    E para cada evento devo ver o nome, data de início e fim
    E a nota média e comentários das avaliações
    E as publicações feitas por ele