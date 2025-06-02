# language: pt
Funcionalidade: Publicação de resumo sobre evento
  Como usuário da plataforma
  Eu quero criar Publicação sobre eventos que participei
  Para compartilhar minha experiência com a comunidade

  Contexto:
    Dado que eu sou um usuário logado na plataforma
    E que eu participei de um evento

  Cenário: Criar um resumo básico sobre o evento
    Quando eu acessar a página do evento
    E clicar no botão "Criar Publicação"
    E preencher o título do resumo
    E escrever o conteúdo do resumo
    E clicar em "Publicar"
    Então a Publicação deve ser publicado com sucesso
    E deve aparecer na página do evento
    E outros usuários devem poder visualizar a Publicação
