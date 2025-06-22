# Configuração do Banco de Dados

## Opções de Banco de Dados

### 1. H2 (Banco em Memória/Arquivo) - Padrão
- **Arquivo**: `./data/dev-db.mv.db`
- **Configuração**: `application.yaml`
- **Uso**: Desenvolvimento local simples

### 2. PostgreSQL (Recomendado para Produção)
- **Configuração**: `application-postgres.yaml`
- **Interface**: pgAdmin em http://localhost:8081

## Como usar o PostgreSQL

### 1. Iniciar o banco de dados
```bash
# Opção 1: Usar o script
start-database.bat

# Opção 2: Comando direto
docker-compose up -d
```

### 2. Executar a aplicação com PostgreSQL
```bash
.\gradlew.bat bootRun --args='--spring.profiles.active=postgres'
```

### 3. Acessar o pgAdmin
- **URL**: http://localhost:8081
- **Email**: admin@certification.com
- **Senha**: admin123

### 4. Conectar ao banco no pgAdmin
- **Host**: postgres
- **Port**: 5432
- **Database**: certification_db
- **Username**: certification_user
- **Password**: certification_pass

### 5. Parar o banco de dados
```bash
# Opção 1: Usar o script
stop-database.bat

# Opção 2: Comando direto
docker-compose down
```

## Tabelas Criadas pelo Liquibase

### 1. `users`
- `id` (PK, auto-increment)
- `username` (unique)
- `password` (criptografada com BCrypt)
- `email`
- `first_name`
- `last_name`
- `enabled`
- `account_non_expired`
- `account_non_locked`
- `credentials_non_expired`

### 2. `user_roles`
- `user_id` (FK para users.id)
- `role` (ADMIN, USER, etc.)

## Usuários Iniciais

### Admin
- **Username**: admin
- **Password**: admin123
- **Email**: admin@example.com
- **Roles**: ADMIN

### User
- **Username**: user
- **Password**: user123
- **Email**: user@example.com
- **Roles**: USER

## Migrações Liquibase

As migrações estão em:
- `src/main/resources/db/changelog/db.changelog-master.yaml`
- `src/main/resources/db/changelog/changes/001-create-users-table.yaml`
- `src/main/resources/db/changelog/changes/002-insert-initial-users.yaml` 