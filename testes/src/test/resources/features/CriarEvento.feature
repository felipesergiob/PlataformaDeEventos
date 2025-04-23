# language: pt
Funcionalidade: Criar Evento
  Como um organizador
  Eu quero criar um novo evento
  Para que eu possa gerenciar suas informações

  Cenário: Criar evento com sucesso
    Dado que eu sou um organizador
    Quando eu criar um evento com os seguintes dados:
      | nome      | Local Tech Conference 2024     |
      | data      | 2024-12-01                    |
      | local     | Centro de Convenções          |
      | categoria | Tecnologia                    |
    Então o evento deve ser criado com sucesso
    E devo receber uma confirmação

  Cenário: Tentar criar evento sem nome
    Dado que eu sou um organizador
    Quando eu tentar criar um evento sem nome
    Então devo receber uma mensagem de erro informando que o nome é obrigatório 