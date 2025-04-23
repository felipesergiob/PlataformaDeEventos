# language: pt

Funcionalidade: Visualizar eventos em destaque

  Como usuário da plataforma
  Quero ver os eventos em destaque na página inicial
  Para saber quais eventos estão mais populares

  Cenário: Visualizar eventos em destaque na página inicial
    Dado que existem eventos em destaque cadastrados na plataforma
    E a semana atual é a semana 17 de 2024
    Quando acesso a página inicial
    Então devo ver a seção "Eventos em Destaque"
    E a lista deve conter os eventos:
      | Workshop de Java |
      | Meetup de Python |
      | Conferência de TI |
    E os eventos devem estar ordenados por número de confirmados em ordem decrescente
    E o evento com mais confirmados deve ser "Workshop de Java" com 150 participantes
    E a seção deve mostrar apenas eventos da semana 17
    E não deve mostrar eventos de semanas anteriores 