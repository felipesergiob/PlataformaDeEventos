# language: pt
Funcionalidade: Visualização de Calendário de Eventos
  Como um usuário da plataforma
  Eu quero visualizar meu calendário de eventos
  Para que eu possa acompanhar os eventos que salvei e confirmei

  Cenário: Visualizar calendário com eventos salvos e confirmados
    Dado que existem os seguintes eventos salvos para o usuário "123":
      | nome           | dataInicio           | dataFim             |
      | Evento A       | 2025-05-28T10:00:00 | 2025-05-28T12:00:00 |
      | Evento B       | 2025-05-29T14:00:00 | 2025-05-29T16:00:00 |
    E que existem os seguintes eventos confirmados para o usuário "123":
      | nome           | dataInicio           | dataFim             |
      | Evento C       | 2025-05-30T09:00:00 | 2025-05-30T11:00:00 |
      | Evento D       | 2025-06-01T10:00:00 | 2025-06-01T12:00:00 |
    Quando eu solicitar a visualização do calendário para o usuário "123"
    Então devo ver 4 eventos no calendário
    E todos os eventos devem estar ordenados por data de início
    E o calendário deve conter tanto eventos salvos quanto confirmados 