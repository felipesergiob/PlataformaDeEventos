# language: pt
Funcionalidade: Seguir usuários
  Como usuário da plataforma
  Quero seguir outros usuários
  Para receber atualizações sobre seus eventos

  Cenário: Seguir um organizador e ver seus eventos
    Dado que sou um usuário da plataforma que deseja seguir outros usuários
    E existe um organizador chamado "João Silva"
    Quando acesso o perfil do organizador "João Silva"
    E clico no botão "Seguir"
    Entao devo ver a mensagem "Você está seguindo João Silva"
    E o botão deve mudar para "Seguindo"

    Quando o organizador "João Silva" cria um novo evento
    Entao devo ver o evento na seção "Eventos de quem você segue" do meu feed
    E o evento deve estar marcado como "Novo"

  Cenário: Parar de seguir um organizador
    Dado que sou um usuário da plataforma que deseja seguir outros usuários
    E estou seguindo o organizador "João Silva"
    Quando acesso o perfil do organizador "João Silva"
    E clico no botão "Seguindo"
    Entao devo ver a mensagem "Você parou de seguir João Silva"
    E o botão deve mudar para "Seguir"

    Quando o organizador "João Silva" cria um novo evento
    Entao não devo ver o evento na seção "Eventos de quem você segue" do meu feed

  Cenário: Ver lista de usuários seguidos
    Dado que sou um usuário da plataforma que deseja seguir outros usuários
    E estou seguindo os organizadores:
      | Nome         |
      | João Silva   |
      | Maria Santos |
    Quando acesso minha lista de usuários seguidos
    Entao devo ver os organizadores:
      | Nome         | Status    |
      | João Silva   | Seguindo  |
      | Maria Santos | Seguindo  | 