# Configuração do Banco de Dados

## Opções de Banco de Dados

### 1. PostgreSQL para Desenvolvimento/Testes
- **Configuração**: `application.yaml` (dev) / `application-test.yaml` (testes)
- **Porta**: `5433`
- **Database**: `certification_test_db`
- **Uso**: Desenvolvimento local e execução de testes

### 2. PostgreSQL para Produção
- **Configuração**: `application-prod.yaml`
- **Porta**: `5432`
- **Database**: `certification_db`
- **Uso**: Ambiente de produção

## Como usar os diferentes ambientes

### 1. Desenvolvimento
```bash
# Iniciar banco de desenvolvimento/testes
docker-compose -f docker-compose-test.yml up -d

# Executar aplicação (usa configuração padrão)
.\gradlew.bat bootRun
```

### 2. Testes
```bash
# Iniciar banco de testes (mesmo do desenvolvimento)
docker-compose -f docker-compose-test.yml up -d

# Executar testes
.\gradlew.bat test

# Ou executar aplicação com profile de teste
.\gradlew.bat bootRun --args='--spring.profiles.active=test'
```

### 3. Produção
```bash
# Iniciar banco de produção
docker-compose up -d

# Executar aplicação com profile de produção
.\gradlew.bat bootRun --args='--spring.profiles.active=prod'
```

### 4. Acessar o pgAdmin (produção)
- **URL**: http://localhost:8081
- **Email**: admin@certification.com
- **Senha**: admin123

### 5. Conectar ao banco no pgAdmin
- **Host**: postgres
- **Port**: 5432
- **Database**: certification_db
- **Username**: certification_user
- **Password**: certification_pass

### 6. Parar os bancos
```bash
# Parar banco de desenvolvimento/testes
docker-compose -f docker-compose-test.yml down

# Parar banco de produção
docker-compose down

# Parar todos
docker-compose down && docker-compose -f docker-compose-test.yml down
```

### 7. Ver logs dos bancos
```bash
# Logs do banco de desenvolvimento/testes
docker-compose -f docker-compose-test.yml logs postgres-test

# Logs do banco de produção
docker-compose logs postgres

# Logs em tempo real
docker-compose -f docker-compose-test.yml logs -f postgres-test
```

## Configurações dos Bancos

### Desenvolvimento/Testes (Porta 5433)
- **Database**: `certification_test_db`
- **Username**: `certification_test_user`
- **Password**: `certification_test_pass`
- **DDL**: `validate` (dev) / `create-drop` (testes)
- **Logs**: Detalhados

### Produção (Porta 5432)
- **Database**: `certification_db`
- **Username**: `certification_user`
- **Password**: `certification_pass`
- **DDL**: `validate` (não altera estrutura)
- **Logs**: Mínimos

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
- **Password**: 123456
- **Email**: admin@example.com
- **Roles**: ADMIN

### User
- **Username**: user
- **Password**: 123456
- **Email**: user@example.com
- **Roles**: USER

## Migrações Liquibase

As migrações estão em:
- `src/main/resources/db/changelog/db.changelog-master.yaml`
- `src/main/resources/db/changelog/changes/001-create-users-table.yaml`
- `src/main/resources/db/changelog/changes/002-insert-initial-users.yaml`

## Troubleshooting

### Erro: "Connection refused"
- Verifique se o Docker está rodando
- Verifique se o banco foi iniciado: `docker ps`

### Erro: "Port 5432/5433 already in use"
```bash
# Verificar processos nas portas
netstat -ano | findstr :5432
netstat -ano | findstr :5433

# Parar containers existentes
docker-compose down
docker-compose -f docker-compose-test.yml down
```

### Erro: "Database not found"
- Para desenvolvimento/testes: verifique se o nome é `certification_test_db`
- Para produção: verifique se o nome é `certification_db`

### Container não inicia
```bash
# Ver logs detalhados
docker-compose logs postgres
docker-compose -f docker-compose-test.yml logs postgres-test

# Reiniciar containers
docker-compose restart postgres
docker-compose -f docker-compose-test.yml restart postgres-test
```

### Limpeza completa
```bash
# Parar e remover todos os containers e volumes
docker-compose down -v
docker-compose -f docker-compose-test.yml down -v

# Limpar volumes não utilizados
docker volume prune
``` 