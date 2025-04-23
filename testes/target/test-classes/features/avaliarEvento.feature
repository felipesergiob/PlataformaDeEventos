# language: pt

Funcionalidade: Avaliação de Eventos
  Como usuário da plataforma
  Quero poder avaliar eventos que participei
  Para compartilhar minha experiência com outros usuários

  Cenário: Avaliar evento com sucesso
    Dado que sou um usuário que confirmou presença em um evento finalizado
    Quando acesso a página de avaliação do evento
    E preencho a avaliação com nota 4 e comentário "Evento muito bom, organização impecável"
    E submeto a avaliação
    Então a avaliação deve ser registrada com sucesso
    E devo ver uma mensagem de confirmação

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