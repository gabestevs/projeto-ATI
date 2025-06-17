# Sistema de Suporte

![React](https://img.shields.io/badge/React-18.2.0-blue?logo=react)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.x-green?logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?logo=mysql)

Bem-vindo ao **Sistema de Suporte**, uma aplicação web full-stack para gerenciamento de clientes e tickets de suporte. Com uma interface moderna inspirada em aplicações como Notion e um backend robusto baseado em Spring Boot, este projeto é ideal para equipes que precisam organizar atendimentos de forma eficiente e intuitiva.

## 📋 Funcionalidades

- **Gerenciamento de Clientes**:
  - Listagem automática de clientes ao carregar a página.
  - Criação, edição e exclusão de clientes com validações.
- **Gerenciamento de Tickets**:
  - Criação de tickets associados a clientes.
  - Visualização de tickets em uma sublista expansível abaixo de cada cliente.
- **Interface Moderna**:
  - Design limpo com paleta de azul, botões estilizados e suporte a modo escuro.
  - Modais centrados para operações de CRUD com animações suaves.
  - Layout responsivo e centralizado.
- **API RESTful**:
  - Endpoints para gerenciar clientes e tickets.
  - Integração segura com o frontend via HTTP.

## 🛠 Tecnologias Utilizadas

### Frontend
- **React** (18.2.0): Biblioteca JavaScript para interfaces dinâmicas.
- **Vanilla CSS**: Estilização pura com suporte a temas claro/escuro.
- **Axios** (1.7.2): Cliente HTTP para chamadas à API.
- **Create React App**: Configuração inicial do projeto.

### Backend
- **Spring Boot** (2.x): Framework Java para API REST.
- **Spring Data JPA**: Persistência com Hibernate.
- **MySQL** (8.x) ou **PostgreSQL** (13+): Banco de dados relacional.
- **Maven**: Gerenciador de dependências.

### Outras Ferramentas
- **Node.js** (v16+): Ambiente para o frontend.
- **JDK** (17+): Ambiente para o backend.
- **Git**: Controle de versão.

## 📚 Pré-requisitos

- **Node.js** (v16+): [Download](https://nodejs.org/)
- **JDK** (17+): [Download](https://www.oracle.com/java/)
- **Maven** (3.8+): [Download](https://maven.apache.org/)
- **MySQL** (8.x) ou **PostgreSQL** (13+): [MySQL](https://www.mysql.com/) | [PostgreSQL](https://www.postgresql.org/)
- **Git**: [Download](https://git-scm.com/)
- Editor de código (ex.: **VS Code**).

## 🗄 Configuração do Banco de Dados

1. **Crie o Banco de Dados**:
   - Acesse o MySQL ou PostgreSQL via terminal ou cliente (ex.: MySQL Workbench, pgAdmin):
     ```bash
     mysql -u root -p
     ```
     ou
     ```bash
     psql -U postgres
     ```
   - Crie o banco:
     ```sql
     CREATE DATABASE support_system;
     ```

2. **Crie as Tabelas**:
   - Execute as queries no banco `support_system`:
     ```sql
     CREATE TABLE clients (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         name VARCHAR(255) NOT NULL,
         email VARCHAR(255) NOT NULL UNIQUE
     );

     CREATE TABLE tickets (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         client_id BIGINT NOT NULL,
         category VARCHAR(255) NOT NULL,
         content TEXT NOT NULL,
         status VARCHAR(50) NOT NULL,
         FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
     );
     ```

3. **Configure o Backend**:
   - Edite `backend/src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/support_system
     spring.datasource.username=seu_usuario
     spring.datasource.password=sua_senha
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```
     Para PostgreSQL:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/support_system
     spring.datasource.username=seu_usuario
     spring.datasource.password=sua_senha
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```

## 🚀 Instalação e Execução

### Backend
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/support-system.git
   cd support-system/support-system-backend
   ```

2. Instale as dependências:
   ```bash
   mvn clean install
   ```

3. Inicie o servidor:
   ```bash
   mvn spring-boot:run
   ```
   - Acesse em: `http://localhost:8080/api`.

### Frontend
1. Navegue até o frontend:
   ```bash
   cd ../support-system-frontend
   ```

2. Instale as dependências:
   ```bash
   npm install
   ```

3. Inicie o servidor:
   ```bash
   npm start
   ```
   - Acesse em: `http://localhost:3000`.

## 📂 Estrutura do Projeto

### Backend
```plaintext
support-system-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── controller/
│   │   │           │   ├── ClientController.java
│   │   │           │   └── TicketController.java
│   │   │           ├── model/
│   │   │           │   ├── Client.java
│   │   │           │   └── Ticket.java
│   │   │           ├── repository/
│   │   │           │   ├── ClientRepository.java
│   │   │           │   └── TicketRepository.java
│   │   │           └── config/
│   │   │               └── CorsConfig.java
│   │   └── resources/
│   │       └── application.properties
└── pom.xml
```

### Frontend
```plaintext
support-system-frontend/
├── src/
│   ├── components/
│   │   ├── ClientTable.jsx
│   │   ├── ClientModal.jsx
│   │   ├── DeleteConfirmationModal.jsx
│   │   ├── TicketModal.jsx
│   │   ├── TicketList.jsx
│   │   └── DarkModeToggle.jsx
│   ├── services/
│   │   └── api.js
│   ├── styles/
│   │   └── styles.css
│   ├── App.jsx
│   ├── index.jsx
│   └── constants.js
├── public/
│   └── index.html
└── package.json
```

## 🌐 Uso da API

### Endpoints
- **Clientes**:
  - `GET /api/clients`: Lista todos os clientes.
  - `GET /api/clients/{id}`: Obtém um cliente.
  - `POST /api/clients`: Cria um cliente.
    ```json
    {
      "name": "João Silva",
      "email": "joao.silva@email.com"
    }
    ```
  - `PUT /api/clients/{id}`: Atualiza um cliente.
  - `DELETE /api/clients/{id}`: Exclui um cliente.
- **Tickets**:
  - `GET /api/tickets/client/{clientId}`: Lista tickets de um cliente.
  - `POST /api/tickets/{clientId}`: Cria um ticket.
    ```json
    {
      "category": "Suporte",
      "content": "Problema X",
      "status": "Open"
    }
    ```

### Exemplo
```bash
curl -X POST http://localhost:8080/api/clients \
-H "Content-Type: application/json" \
-d '{"name":"João Silva","email":"joao.silva@email.com"}'
```

## 📜 Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).

## 📬 Contato

Dúvidas ou sugestões? Entre em contato: [gabrielgesteves01@gmail.com].

---

Desenvolvido com 💙 por [Gabriel Esteves].
