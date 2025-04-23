# Plataforma de Eventos

<p align="center">
  <img
    src="https://img.shields.io/badge/Status-Em%20desenvolvimento-green?style=flat-square"
    alt="Status"
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

## ♟️ Estrutura do Projeto

O projeto segue os princípios de Domain-Driven Design (DDD) e está organizado nos seguintes bounded contexts:

- GestaoUsuarios
- GestaoEventos
- Interacoes
- Relatorios

## 👩‍💻 Tecnologias

- Java
- Maven
- JUnit (para testes)
- Domain-Driven Design

## 📁 Estrutura de Diretórios

```
PlataformaDeEventos/
├── dominio/           # Código do domínio
├── testes/           # Testes unitários
├── sistema.cml       # Modelo de domínio
└── pom.xml           # Configuração Maven
```

## 📝 Documentação

- [Modelo de Domínio](sistema.cml)
- [Descrição do Domínio](docs/dominio.md)

## License

[MIT](LICENSE) 