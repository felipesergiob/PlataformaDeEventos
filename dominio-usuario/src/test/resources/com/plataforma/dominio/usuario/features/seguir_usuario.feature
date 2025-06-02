# language: pt
Funcionalidade: Seguir Usuário e Listar Eventos
  Como um usuário da plataforma
  Eu quero poder seguir outros usuários e ver seus eventos
  Para que eu possa acompanhar as atividades dos usuários que me interessam

  Cenário: Seguir um usuário e listar seus eventos
    Dado que existem os seguintes eventos do usuário "2":
      | nome           | dataInicio           | dataFim             | status    |
      | Evento A       | 2025-05-28T10:00:00 | 2025-05-28T12:00:00 | CONFIRMADO|
      | Evento B       | 2025-05-29T14:00:00 | 2025-05-29T16:00:00 | SALVO     |
    Quando eu seguir o usuário "2" sendo o usuário "1"
    Então devo ver 2 eventos do usuário seguido
    E os eventos devem estar ordenados por data de início

  Cenário: Tentar seguir a si mesmo
    Dado que existem os seguintes eventos do usuário "1":
      | nome           | dataInicio           | dataFim             | status    |
      | Evento A       | 2025-05-28T10:00:00 | 2025-05-28T12:00:00 | CONFIRMADO|
    Quando eu tentar seguir o usuário "1" sendo o usuário "1"
    Então devo receber um erro informando que não posso seguir a mim mesmo 