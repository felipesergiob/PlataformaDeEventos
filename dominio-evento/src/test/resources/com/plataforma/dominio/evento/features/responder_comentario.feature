# language: pt
Funcionalidade: Responder comentários em eventos
  Como organizador de eventos
  Eu quero responder aos comentários dos participantes
  Para interagir com o público e melhorar futuras edições

  Contexto:
    Dado que existe um evento para comentários "Workshop de Programação"
    E que o usuário "João Silva" é organizador deste evento
    E que existe um comentário de "Maria Santos" no evento dizendo "Ótimo evento!"

  Cenário: Responder a um comentário
    Quando o organizador "João Silva" responde ao comentário com "Obrigado pelo feedback!"
    Então a resposta deve ser exibida abaixo do comentário original
    E deve mostrar o nome do organizador "João Silva"
    E deve mostrar a data e hora da resposta

