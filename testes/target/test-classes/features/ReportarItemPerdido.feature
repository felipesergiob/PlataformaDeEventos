# language: pt
Funcionalidade: Reportar Item Perdido
  Como um participante de um evento
  Eu quero reportar um item perdido e requisitar assistência
  Para que a equipe de achados e perdidos possa me ajudar

  Cenário: Reportar item perdido com sucesso
    Dado que eu sou um participante de um evento finalizado
    Quando eu acessar a área de Achados e Perdidos
    E preencher o formulário com os seguintes dados:
      | campo           | valor                     |
      | tipo           | Carteira                  |
      | cor            | Marrom                    |
      | descrição      | Carteira de couro marrom  |
      | localAproximado| Área de alimentação       |
    Então o item perdido deve ser registrado com sucesso
    E devo receber uma confirmação do registro

  Cenário: Reportar item perdido com foto
    Dado que eu sou um participante de um evento finalizado
    Quando eu acessar a área de Achados e Perdidos
    E preencher o formulário com os seguintes dados:
      | campo           | valor                     |
      | tipo           | Celular                   |
      | cor            | Preto                     |
      | descrição      | iPhone 12                 |
      | localAproximado| Palco principal           |
    E anexar uma foto do item
    Então o item perdido deve ser registrado com sucesso
    E a foto deve ser salva junto com o registro

  Cenário: Tentar reportar item perdido sem dados obrigatórios
    Dado que eu sou um participante de um evento finalizado
    Quando eu acessar a área de Achados e Perdidos
    E tentar submeter o formulário sem preencher os campos obrigatórios
    Então devo receber uma mensagem de erro informando os campos obrigatórios 