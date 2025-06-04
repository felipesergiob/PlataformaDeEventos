# SEGUNDA_ENTREGA.md

## ✅ Histórias de Usuário Implementadas

Durante o desenvolvimento do projeto, implementamos as seguintes histórias de usuário:

1. **Filtro de eventos**

   > Como usuário da plataforma, eu gostaria de filtrar eventos por gênero, horário, data e preço.

2. **Eventos salvos no calendário**

   > Como usuário da plataforma, eu gostaria de ver no calendário os eventos que salvei ou confirmei presença.

3. **Achados e perdidos**

   > Como usuário da plataforma, eu gostaria de reportar um item perdido e requisitar assistência da equipe de achados e perdidos de um evento que participei.

4. **Relatórios e dashboards**

   > Como usuário da plataforma, eu gostaria de ter relatórios/dashboards de eventos que eu criei e já aconteceram.

5. **Avaliação de eventos**

   > Como usuário da plataforma, eu gostaria de dar nota e comentário após o evento que confirmei presença.

6. **Histórico e avaliações de eventos**

   > Como usuário da plataforma, eu gostaria de visualizar o histórico e notas de eventos criados por outros usuários.

7. **Seguir usuários**

   > Como usuário da plataforma, eu gostaria de seguir outros usuários.

8. **Responder comentários**

   > Como usuário da plataforma, eu gostaria de responder aos comentários deixados nos meus eventos.

9. **Relato de participação**

   > Como usuário da plataforma, eu gostaria de criar resumos ou relatos sobre eventos que participei.

10. **Eventos em destaque**

    > Como usuário da plataforma, eu gostaria de visualizar os eventos em destaque da semana.

---

## 🧩 Padrões de Projeto Utilizados

Durante o desenvolvimento, utilizamos alguns padrões de projeto para organizar melhor a arquitetura do sistema, promover reuso de código e facilitar a manutenção.

**Padrões adotados:**

- **[Nome do Padrão 1]**: Aplicado nos arquivos `[NomeArquivo1.kt, NomeArquivo2.kt]`
- **[Nome do Padrão 2]**: Utilizado para [explicação breve], presente em `[NomeArquivo.kt]`
- ...

---

## ⚙️ Instruções de Execução e Acesso

Plataforma de Eventos

Sistema de gerenciamento de eventos desenvolvido com Spring Boot, seguindo princípios de Clean Architecture.

### Requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL 15+

### Configuração Rápida do Ambiente

```bash
git clone https://github.com/felipesergiob/PlataformaDeEventos
cd PlataformaDeEventos
```

```bash
# Criar banco de dados PostgreSQL
touch ~/.pgpass  # se desejar salvar credenciais
createdb plataforma_eventos
```

> Configure o `application.properties`:
> `apresentacao-backend/src/main/resources/application.properties`

### Build e Testes

Compilar com testes:

```bash
mvn clean install
```

Compilar sem testes:

```bash
mvn clean install -DskipTests
```

### Rodar a Aplicação

```bash
cd apresentacao-backend
mvn spring-boot:run
```

Acesse: [http://localhost:8081](http://localhost:8081)

### Estrutura do Projeto

- **apresentacao-backend**: Controllers REST e configurações da API
- **aplicacao**: Casos de uso e regras da aplicação
- **dominio**: Entidades e regras centrais
- **infraestrutura**: Persistência e integrações externas

### APIs Disponíveis

#### Usuários

- `POST /usuario` — Criar usuário
- `POST /usuario/login` — Login
- `POST /usuario/seguir` — Seguir usuário
- `DELETE /usuario/seguir` — Deixar de seguir

#### Eventos

- `POST /evento` — Criar evento
- `GET /evento/{id}` — Buscar por ID
- `GET /evento` — Listar todos
- `GET /evento/organizador/{organizadorId}` — Por organizador

Exemplo de payload JSON:

```json
{
  "titulo": "Show de Rock",
  "descricao": "Um incrível show de rock",
  "dataInicio": "2024-04-01T20:00:00",
  "dataFim": "2024-04-02T02:00:00",
  "local": "Arena Show",
  "genero": "Rock",
  "valor": 100.00,
  "imagem": "http://exemplo.com/imagem.jpg",
  "organizadorId": 1
}
```

### Banco de Dados

Gerenciado com Flyway. Migrações estão em:
`infraestrutura/src/main/resources/db/migration/`

### Desenvolvimento

#### 🔍 Convenções

- Nomes claros e descritivos
- Arquivos pequenos (< 200 linhas)
- Comentários só quando necessário
- Siga SOLID e Clean Code

#### ✅ Testes

- Testes unitários
- Integração
- Aceitação (Cucumber)

Rodar apenas os testes:

```bash
mvn test
```

#### 📄 Boas Práticas

1. Rode os testes antes do commit
2. Documente alterações
3. Use commits semânticos
4. Crie branches para cada feature/fix

### Troubleshooting

#### 1. Erro de Conexão com Banco

- PostgreSQL está rodando?
- application.properties está correto?

#### 2. Erro de Compilação

- Verifique versão do Java
- Rode `mvn clean`

#### 3. Testes Falhando

- Banco de testes configurado?
- Rode `mvn clean test`

### Contribuição

1. Crie uma branch
2. Faça suas mudanças
3. Rode os testes
4. Abra um pull request

### Licença

Projeto sob Licença MIT. Veja `LICENSE` para mais detalhes.
