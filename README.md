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

2. Gestão de Eventos
   - Criação e edição de eventos
   - Filtros por data, gênero e preço
   - Confirmação de presença
   - Lista de interesse

3. Interações
   - Avaliações e comentários
   - Relatos com fotos
   - Achados e perdidos
   - Comentários públicos

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

1. Clone o repositório
2. Certifique-se de ter Java 17 e Maven 3.9.6 instalados
3. Execute `mvn clean install` para compilar o projeto (isso tambem ja roda os testes)
4. Execute os testes com `mvn test`

## 📝 Documentação

- [Modelo de Domínio](sistema.cml)
- [Descrição do Domínio](docs/dominio.md)

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE). 
