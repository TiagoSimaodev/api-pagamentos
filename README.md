# 🚀 API de Pagamentos

API REST desenvolvida com foco em **boas práticas de arquitetura**, **segurança com JWT**, **testes automatizados** e **padronização de código** utilizando **Spring Boot**.

---

## 📌 Sobre o projeto

A API permite o gerenciamento de **pagamentos e usuários**, incluindo:  

- Registro e login de usuários  
- Proteção de endpoints por **roles** (`USER` e `ADMIN`)  
- Criação, consulta e atualização de status de pagamentos  

O projeto simula um cenário real de backend, aplicando conceitos utilizados no mercado.

---

## 🛠️ Tecnologias utilizadas

- Java 17  
- Spring Boot 3  
- Spring Data JPA / Hibernate  
- Spring Security com JWT  
- H2 Database (local)  
- PostgreSQL (opcional, produção)  
- Maven  
- JUnit 5, Mockito, MockMvc  
- Bean Validation  
- Swagger (OpenAPI)  

---

## 🧠 Arquitetura

O projeto segue o padrão em camadas:  


Controller → Service → Repository → Entity


**Responsabilidades:**

- **Controller** → Camada de entrada (HTTP)  
- **Service** → Regras de negócio  
- **Repository** → Acesso ao banco  
- **Entity** → Representação da base de dados  
- **DTO** → Transferência de dados  
- **Exception** → Tratamento global de erros  

---

## 🔐 Segurança com JWT

- Autenticação via token JWT  
- Endpoints protegidos por roles:  

| Role | Acesso aos endpoints |
|------|--------------------|
| USER | Endpoints gerais |
| ADMIN | /admin/** |

- Senhas armazenadas com **BCrypt**  
- Tokens JWT expiram conforme configuração (`JwtUtil`)  
- **JwtFilter** garante autenticação e validação em todas as requisições  

---


## ⚙️ Funcionalidades

### Usuários

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | /auth/registro | Registrar usuário (`username`, `password`, `role`) |
| POST | /auth/login | Autenticar usuário e gerar token JWT |

### Pagamentos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | /pagamentos | Criar pagamento (status inicial: `PENDING`) |
| PUT | /pagamentos/{id}/status?status=PAID | Atualizar status do pagamento (`PENDING`, `PAID`, `FAILED`) |
| GET | /pagamentos/{id} | Buscar pagamento por ID |
| GET | /pagamentos | Listar todos os pagamentos |

### Admin (somente role `ADMIN`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | /admin/teste | Endpoint de teste acessível apenas para administradores |

---


## ⚠️ Tratamento de erros

- Exceções globais via `@ControllerAdvice`  
- Respostas HTTP padronizadas:

| Código | Descrição |
|--------|-----------|
| 200 | OK - Sucesso |
| 201 | CREATED - Criado com sucesso |
| 400 | BAD REQUEST - Dados inválidos |
| 401 | UNAUTHORIZED - Token ausente ou inválido |
| 403 | FORBIDDEN - Usuário sem permissão |
| 404 | NOT FOUND - Recurso não encontrado |
| 500 | INTERNAL SERVER ERROR - Erro inesperado |

---


## 📄 Documentação da API (Swagger)

Após iniciar a aplicação, acesse:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Permite:  
- Visualizar endpoints  
- Testar requisições  
- Conferir estrutura de request/response  

---


## 🗄️ Banco de dados (H2)

- Console H2 disponível em: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
- Configuração:  
  - JDBC URL: `jdbc:h2:mem:testdb`  
  - User: `sa`  
  - Password: (vazio)  

> Em produção, recomenda-se PostgreSQL ou outro banco relacional.

---

## ✅ Testes

O projeto possui cobertura de testes:  

- **Service** → Testes unitários, validação de regras, uso de Mockito  
- **Controller** → Simulação de requisições HTTP, validação de status e resposta JSON  

---


## 💡 Boas práticas aplicadas

- Princípios **SOLID**  
- **Injeção de dependência**  
- **Separação de responsabilidades**  
- Uso de **ENUM** para status  
- Tratamento centralizado de exceções  
- Código limpo e organizado  
- Segurança com JWT e roles  

---


## 🚀 Como executar o projeto

```bash
# Clonar repositório
git clone https://github.com/TiagoSimaodev/api-pagamento.git

# Entrar na pasta do projeto
cd api-pagamento

# Rodar a aplicação
./mvnw spring-boot:run


Exemplos de requisição
Registrar usuário:

POST /auth/registro
{
  "username": "admin",
  "password": "123",
  "role": "ADMIN"
}

Login e gerar token:

POST /auth/login
{
  "username": "admin",
  "password": "123"
}

Resposta:
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}

Enviar token nos headers para endpoints protegidos:

Authorization: Bearer <TOKEN>

---

📈 Melhorias futuras
Deploy em ambiente cloud (AWS / Render)
Integração com gateway de pagamento
Migração para banco relacional (PostgreSQL)
Documentação avançada da API

---

👨‍💻 Autor

Tiago Simao
LinkedIn:https://www.linkedin.com/in/tiagosimaodev/
