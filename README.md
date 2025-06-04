# Plataforma de Eventos

<p align="center">
  <img
    src="https://img.shields.io/badge/Status-Em%20desenvolvimento-green?style=flat-square"
    alt="Status"
  />
  <img
    src="https://img.shields.io/badge/Java-17-blue?style=flat-square"
    alt="Java"
  />
  <img
    src="https://img.shields.io/badge/Maven-3.9.6-red?style=flat-square"
    alt="Maven"
  />
</p>

## 🗪 Visão Geral

A Plataforma de Eventos é um ambiente digital colaborativo onde qualquer usuário pode criar, divulgar, participar e acompanhar eventos. O sistema permite a gestão completa de eventos, desde sua criação até a avaliação pós-evento.

## 🔧 Funcionalidades Principais

1. Gestão de Usuários
   - Cadastro e autenticação
   - Seguir outros usuários
   - Notificações de eventos
   - Autenticação por token para maior segurança

2. Gestão de Eventos
   - Criação e edição de eventos
   - Filtros por data, gênero e preço
   - Confirmação de presença
   - Lista de interesse
   - Upload de imagens e anexos nos eventos
   - Integração com API externa para geolocalização

3. Interações
   - Avaliações e comentários
   - Relatos com fotos
   - Achados e perdidos
   - Comentários públicos
   - Sistema de denúncias e moderação de comentários
   - Módulo de achados e perdidos aprimorado com suporte a anexos

4. Relatórios
   - Dashboard de eventos
   - Eventos em destaque
   - Métricas de participação

## 👩‍💻 Tecnologias

- Java 17
- Maven 3.9.6
- JUnit 5 (para testes)
- Domain-Driven Design (DDD)
- Clean Architecture

## 📁 Estrutura do Projeto

O projeto segue os princípios de Domain-Driven Design (DDD) e Clean Architecture, organizado nos seguintes módulos:

```
PlataformaDeEventos/
├── dominio/           # Código do domínio (entidades, value objects, regras de negócio)
├── persistencia/      # Implementações de repositórios e acesso a dados
├── apresentacao/      # Controladores, DTOs e interfaces de usuário
├── testes/           # Testes unitários e de integração
├── sistema.cml       # Modelo de domínio
└── pom.xml           # Configuração Maven
```

## 🚀 Como Executar
- Consulte o arquivo [SEGUNDA_ENTREGA.md](SEGUNDA_ENTREGA.md)


## 📝 Documentação

- [Descrição do Domínio](https://docs.google.com/document/d/1YUaVytl6GA5PXFs6PHMEqKl3QaDEjtxsSOqKD3RUcFE/edit?usp=sharing)
- [Histórias de Usuário e BDD](https://docs.google.com/document/d/16oGIn57jkQUDBvDI-jhLO2Bh0EmEZtsuZOeUfGvmrSw/edit?tab=t.0)
- [Personas e Mapa de Histórias](https://miro.com/app/board/uXjVI_nYtro=/?share_link_id=188525928359)
- [Protótipo de Baixa Fidelidade](https://www.figma.com/design/J3leMrEJF7Uy62RjUHxrMl/Low-prototype?node-id=0-1&t=X3RX3u4DNpBW3Car-1)
- [Modelo de Domínio (Context Mapper)](plataforma-de-eventos.cml)
- [Diagrama UML](PlantUML-Requisitos.png)
- [Slides (1ª Entrega)](https://www.canva.com/design/DAGlfz9Gsok/b5sVY79cs_YWp_q9z9RJpA/edit?utm_content=DAGlfz9Gsok&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)
- [Arquivo (2ª Entrega)](SEGUNDA_ENTREGA.md)

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE). 
