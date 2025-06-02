# language: pt
Funcionalidade: Listagem de Eventos em Destaque
  Como um usuário da plataforma
  Eu quero ver os eventos em destaque da semana
  Para que eu possa me inscrever nos eventos mais populares

  Cenário: Listar eventos em destaque da semana
    Dado os seguintes eventos cadastrados:
      | nome           | dataInicio           | dataFim             | inscritos |
      | Evento A       | 2025-05-28T10:00:00 | 2025-05-28T12:00:00 | 100       |
      | Evento B       | 2025-05-29T14:00:00 | 2025-05-29T16:00:00 | 150       |
      | Evento C       | 2025-05-30T09:00:00 | 2025-05-30T11:00:00 | 80        |
      | Evento D       | 2025-06-01T10:00:00 | 2025-06-01T12:00:00 | 200       |
      | Evento E       | 2025-05-27T15:00:00 | 2025-05-27T17:00:00 | 120       |
    Quando eu solicitar os eventos em destaque da semana
    Então devo ver 3 eventos
    E os eventos devem estar ordenados do mais para o menos popular
    E todos os eventos exibidos devem ocorrer na semana atual