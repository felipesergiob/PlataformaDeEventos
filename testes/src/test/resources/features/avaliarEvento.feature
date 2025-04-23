# language: pt

Funcionalidade: Avaliar evento
  Como usuário da plataforma
  Quero avaliar eventos que participei
  Para compartilhar minha experiência e ajudar outros usuários

  Cenário: Avaliar evento com sucesso
    Dado que sou um usuário que participou do evento
    Quando avalio o evento com nota 5
    Então a avaliação deve ser registrada

  Cenário: Tentar avaliar evento sem ter participado
    Dado que sou um usuário que não participou do evento
    Quando avalio o evento com nota 4
    Então devo ver a mensagem de erro de avaliação "Apenas participantes podem avaliar o evento"
    E a avaliação não deve ser registrada

  Cenário: Avaliar evento sem comentário
    Dado que sou um usuário que confirmou presença em um evento finalizado
    Quando acesso a página de avaliação do evento
    E preencho apenas a nota 5
    E submeto a avaliação
    Então a avaliação deve ser registrada com sucesso
    E devo ver uma mensagem de confirmação

  Cenário: Tentar avaliar evento não finalizado
    Dado que sou um usuário que confirmou presença em um evento
    Quando tento acessar a página de avaliação do evento
    Então devo ver uma mensagem informando que o evento ainda não finalizou

  Cenário: Tentar avaliar evento sem ter confirmado presença
    Dado que sou um usuário que não confirmou presença em um evento finalizado
    Quando tento acessar a página de avaliação do evento
    Então devo ver uma mensagem informando que preciso ter confirmado presença para avaliar 