# language: pt
Funcionalidade: Avaliar Evento
  Como usuário da plataforma
  Eu quero avaliar um evento que participei
  Para compartilhar minha experiência e feedback

  Contexto:
    Dado que existe um evento "Workshop de Programação"
    E que o evento já terminou
    E que eu confirmei presença neste evento

  Cenário: Avaliar evento com sucesso
    Quando eu avalio o evento com nota 5
    E comento "Excelente workshop, aprendi muito sobre programação!"
    Então a avaliação deve ser registrada com sucesso
    E o evento deve ter uma nova avaliação
