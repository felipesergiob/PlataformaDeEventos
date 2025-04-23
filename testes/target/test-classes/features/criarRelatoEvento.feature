# language: pt
Funcionalidade: Criar relato de evento
  Como usuário da plataforma
  Quero criar relatos sobre eventos que participei
  Para compartilhar minha experiência com a comunidade

  Cenário: Criar relato com texto e fotos
    Dado que sou um usuário da plataforma que deseja criar um relato
    E participei do evento "Workshop de Java"
    Quando acesso a página do evento
    E clico no botão de criar relato "Criar Relato"
    E preencho o título com "Experiência incrível no Workshop"
    E escrevo o relato:
      """
      O workshop foi uma experiência enriquecedora. Aprendi muito sobre boas práticas de programação
      e tive a oportunidade de interagir com outros desenvolvedores. Os instrutores foram muito
      didáticos e o conteúdo estava bem estruturado.
      """
    E adiciono uma foto do evento
    E clico em publicar relato "Publicar"
    Então devo ver meu relato publicado na página do evento
    E o relato deve conter:
      | Campo        | Valor                                                                 |
      | Título       | Experiência incrível no Workshop                                       |
      | Autor        | João Silva                                                             |
      | Data         | Hoje                                                                   |
      | Conteúdo     | O workshop foi uma experiência enriquecedora. Aprendi muito sobre boas práticas de programação e tive a oportunidade de interagir com outros desenvolvedores. Os instrutores foram muito didáticos e o conteúdo estava bem estruturado. |
      | Total Fotos  | 1                                                                      |

  Cenário: Criar relato apenas com texto
    Dado que sou um usuário da plataforma que deseja criar um relato
    E participei do evento "Meetup de Python"
    Quando acesso a página do evento
    E clico no botão de criar relato "Criar Relato"
    E preencho o título com "Primeiro Meetup de Python"
    E escrevo o relato:
      """
      Foi meu primeiro meetup e adorei a experiência. A comunidade é muito acolhedora
      e as discussões foram muito produtivas. Já estou ansioso para o próximo encontro!
      """
    E clico em publicar relato "Publicar"
    Então devo ver meu relato publicado na página do evento
    E o relato deve conter:
      | Campo        | Valor                                                                 |
      | Título       | Primeiro Meetup de Python                                              |
      | Autor        | João Silva                                                             |
      | Data         | Hoje                                                                   |
      | Conteúdo     | Foi meu primeiro meetup e adorei a experiência. A comunidade é muito acolhedora e as discussões foram muito produtivas. Já estou ansioso para o próximo encontro! |
      | Total Fotos  | 0                                                                      |

  Cenário: Tentar criar relato sem título
    Dado que sou um usuário da plataforma que deseja criar um relato
    E participei do evento "Conferência de TI"
    Quando acesso a página do evento
    E clico no botão de criar relato "Criar Relato"
    E escrevo o relato:
      """
      A conferência foi excelente, com palestras muito interessantes.
      """
    E clico em publicar relato "Publicar"
    Então devo ver a mensagem de erro "O título é obrigatório"
    E o relato não deve ser publicado 