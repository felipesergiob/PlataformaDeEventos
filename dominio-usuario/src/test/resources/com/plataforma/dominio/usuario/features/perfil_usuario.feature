# language: pt
Funcionalidade: Visualização do perfil do usuário
  Como um usuário da plataforma
  Eu quero visualizar o perfil de um usuário
  Para ver seus eventos passados

  Cenário: Visualizar perfil de um usuário com eventos passados
    Dado que existe um usuário "João Silva" com email "joao@email.com"
    E que o usuário organizou os seguintes eventos:
      | Nome do Evento | Data Início        | Data Fim          |
      | Workshop Java  | 2024-01-01 10:00   | 2024-01-01 12:00  |
      | Palestra Web   | 2024-01-15 14:00   | 2024-01-15 16:00  |
    Quando eu visualizar o perfil do usuário
    Então devo ver os seguintes eventos passados:
      | Nome do Evento | Data Início        | Data Fim          |
      | Workshop Java  | 2024-01-01 10:00   | 2024-01-01 12:00  |
      | Palestra Web   | 2024-01-15 14:00   | 2024-01-15 16:00  |