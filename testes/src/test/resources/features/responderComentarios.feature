# language: pt
Funcionalidade: Responder comentários
  Como organizador de eventos
  Quero responder aos comentários dos participantes
  Para interagir com o público e melhorar futuras edições

  Cenário: Responder a um comentário
    Dado que sou um organizador da plataforma
    E existe um evento meu com comentários
    Quando acesso a seção de comentários do evento
    E seleciono o comentário do usuário "João Silva"
    E clico no botão de "Responder" do comentário
    E preencho a resposta com "Obrigado pelo feedback! Vamos considerar sua sugestão para o próximo evento."
    E clico em "Enviar"
    Entao devo ver minha resposta abaixo do comentário original
    E a resposta deve conter:
      | Autor      | Organizador |
      | Data       | Hoje        |
      | Conteúdo   | Obrigado pelo feedback! Vamos considerar sua sugestão para o próximo evento. |

  Cenário: Editar uma resposta
    Dado que sou um organizador da plataforma
    E existe um evento meu com comentários
    E já respondi ao comentário do usuário "João Silva"
    Quando acesso a seção de comentários do evento
    E seleciono minha resposta
    E clico no botão de "Editar" do comentário
    E altero o texto para "Obrigado pelo feedback! Já implementamos sua sugestão para o próximo evento."
    E clico em "Salvar"
    Entao devo ver a resposta atualizada
    E a resposta deve conter:
      | Autor      | Organizador |
      | Data       | Hoje        |
      | Conteúdo   | Obrigado pelo feedback! Já implementamos sua sugestão para o próximo evento. |

  Cenário: Excluir uma resposta
    Dado que sou um organizador da plataforma
    E existe um evento meu com comentários
    E já respondi ao comentário do usuário "João Silva"
    Quando acesso a seção de comentários do evento
    E seleciono minha resposta
    E clico no botão de "Excluir" do comentário
    E confirmo a exclusão
    Entao nao devo mais ver minha resposta
    E o comentário original deve permanecer visível

  Cenário: Responder a múltiplos comentários
    Dado que sou um organizador da plataforma
    E existe um evento meu com múltiplos comentários:
      | Usuário     | Comentário                                |
      | João Silva  | Adorei o evento!                         |
      | Maria Santos| Sugestão: mais pontos de água            |
      | Pedro Costa | Ótima organização!                       |
    Quando acesso a seção de comentários do evento
    E respondo a todos os comentários
    Entao devo ver minhas respostas abaixo de cada comentário
    E cada resposta deve conter:
      | Campo      | Valor                     |
      | Autor      | Organizador               |
      | Data       | Hoje                      |
      | Conteúdo   | Adorei o evento!          |
      | Conteúdo   | Sugestão: mais pontos de água |
      | Conteúdo   | Ótima organização!        | 