# Rapidoja Backend

Projeto backend da aplicação Rapidoja, desenvolvido com Spring Boot.

## Pré-requisitos

- Java 21
- Maven 3.9+
- MySQL 8.0

## Configuração do Banco de Dados

### Opção 1: Docker (Recomendado)

#### MySQL + Aplicação (Padrão)
```bash
docker compose up -d
```

#### Apenas MySQL
```bash
docker compose -f docker-compose.mysql.yml up -d
```

### Opção 2: MySQL Local

Certifique-se de ter o MySQL rodando na porta 3307 com as seguintes configurações:
- Database: `rapidoja_db`
- Username: `root`
- Password: `root123`

## Rodando o Projeto Localmente

### 1. Clone o repositório
```bash
git clone <repository-url>
cd tcc/backEnd/tcc
```

### 2. Configure as variáveis de ambiente (opcional)
Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:
```env
# JWT Configuration
JWT_SECRET=5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
JWT_EXPIRATION=86400000

# Database Configuration
DB_USERNAME=root
DB_PASSWORD=root123
DB_PORT=3307

# JPA Configuration
JPA_SHOW_SQL=true
```

### 3. Compile o projeto
```bash
mvn clean compile
```

### 4. Execute os testes
```bash
mvn test
```

### 5. Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/com/rapidoja/tcc/
│   │   ├── config/          # Configurações (Security, CORS, etc)
│   │   ├── controller/      # Controladores REST
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── mapper/          # Mapeamento de entidades
│   │   ├── model/           # Entidades do banco de dados
│   │   ├── repository/      # Repositórios JPA
│   │   ├── security/        # Configurações de segurança e JWT
│   │   └── service/         # Lógica de negócio
│   └── resources/
│       ├── application.yaml # Configurações da aplicação
│       └── db/migration/    # Migrações do Flyway
└── test/                    # Testes unitários e de integração
```

## Endpoints Principais

- **Admin**: `/api/admins`
- **Customer**: `/api/customers`
- **Delivery Man**: `/api/delivery-men`
- **Order**: `/api/orders`
- **Address**: `/api/addresses`

## Variáveis de Ambiente

O `application.yaml` suporta as seguintes variáveis de ambiente com valores padrão:

- `DB_USERNAME`: Usuário do banco de dados (padrão: `root`)
- `DB_PASSWORD`: Senha do banco de dados (padrão: `root123`)
- `JPA_SHOW_SQL`: Mostrar SQL no console (padrão: `true`)
- `JWT_SECRET`: Segredo para assinatura do token JWT
- `JWT_EXPIRATION`: Tempo de expiração do token em milissegundos (padrão: `86400000`)

## Docker Compose

### docker-compose.yml
Sobe MySQL + aplicação Spring Boot em containers Docker (configuração padrão).

### docker-compose.mysql.yml
Sobe apenas o MySQL com as configurações padrão.

## Postman Collection

Uma collection do Postman está disponível na pasta `Postman Collection` com todos os endpoints da API.

### Como usar

1. Importe o arquivo `TCC.postman_collection.json` no Postman
2. Execute a requisição de login para obter o token JWT:
   - **Admin**: `get token - admin` (email: `teste_admin@rapidoja.com`, senha: `admin123`)
   - **Customer**: `get token - customer` (email: `joao_cliente@email.com`, senha: `admin123`)
   - **Delivery Man**: `get token - delivery` (email: `maria_entregadora@email.com`, senha: `admin123`)
3. O token será automaticamente salvo na variável `{{token}}` e usado nas outras requisições

### Endpoints disponíveis

- **Customer**: Criar conta, buscar, atualizar e deletar
- **Admin**: Buscar, criar e atualizar administradores
- **Delivery Man**: Criar, buscar, atualizar e deletar entregadores
- **Order**: Criar pedidos, buscar por cliente/entregador, atribuir entregador, atualizar status
- **Login**: Obter token JWT para autenticação

## Testes

Os testes estão localizados em `src/test/java` e seguem o padrão:
- Controller tests: Testes de integração com MockMvc
- Service tests: Testes unitários com Mockito
- Security tests: Testes de autenticação e autorização

Para rodar todos os testes:
```bash
mvn test
```

Para rodar uma classe de teste específica:
```bash
mvn test -Dtest=OrderServiceImplTest
```

## Migrações do Banco de Dados

O projeto usa Flyway para gerenciamento de migrações. As migrações estão em `src/main/resources/db/migration`.

As migrações são executadas automaticamente ao iniciar a aplicação.

## Limpeza

Para parar e remover os containers Docker:
```bash
docker-compose down
```

Para remover também os volumes:
```bash
docker-compose down -v
```
