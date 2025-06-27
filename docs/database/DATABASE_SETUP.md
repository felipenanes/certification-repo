# Configuração do Banco de Dados

## Opções de Banco de Dados

### 1. PostgreSQL para Produção
- **Configuração**: `application-prod.yaml`
- **Porta**: `5432`
- **Database**: `certification_db`
- **Usuário**: `certification_user`
- **Senha**: `certification_pass`
- **Uso**: Ambiente de produção

### 2. PostgreSQL para Desenvolvimento/Testes
- **Configuração**: `application-test.yaml`
- **Porta**: `5433`
- **Database**: `certification_test_db`
- **Usuário**: `certification_test_user`
- **Senha**: `certification_test_pass`
- **Uso**: Desenvolvimento local e execução de testes

## Como usar os diferentes ambientes

### 1. Produção
```bash
# Iniciar banco de produção
docker-compose up -d
# Executar aplicação com profile de produção
./gradlew bootRun --args='--spring.profiles.active=prod'
```

### 2. Desenvolvimento/Teste
```bash
# Iniciar banco de desenvolvimento/teste
docker-compose -f compose-test.yml up -d
# Executar aplicação (profile de teste)
./gradlew bootRun --args='--spring.profiles.active=test'
# Ou executar aplicação (profile padrão)
./gradlew bootRun
```

### 3. Acessar o pgAdmin (produção)
- **URL**: http://localhost:8081
- **Email**: admin@certification.com
- **Senha**: admin123

### 4. Conectar ao banco no pgAdmin ou DBeaver
- **Host**: localhost
- **Porta**: 5432 (produção) ou 5433 (dev/teste)
- **Database**: certification_db ou certification_test_db
- **Username**: certification_user ou certification_test_user
- **Password**: certification_pass ou certification_test_pass

### 5. Parar os bancos
```bash
# Parar banco de produção
docker-compose down
# Parar banco de desenvolvimento/teste
docker-compose -f compose-test.yml down
# Parar todos
docker-compose down && docker-compose -f compose-test.yml down
```

### 6. Ver logs dos bancos
```bash
# Logs do banco de produção
docker-compose logs postgres
# Logs do banco de desenvolvimento/teste
docker-compose -f compose-test.yml logs postgres-test
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
- `src/main/resources/db/changelog/changes/001-create-initial-db-structure.yaml`
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
docker-compose -f compose-test.yml down
```

### Erro: "Database not found"
- Para desenvolvimento/testes: verifique se o nome é `certification_test_db`
- Para produção: verifique se o nome é `certification_db`

### Container não inicia
```bash
# Ver logs detalhados
docker-compose logs postgres
docker-compose -f compose-test.yml logs postgres-test
# Reiniciar containers
docker-compose restart postgres
docker-compose -f compose-test.yml restart postgres-test
```

### Limpeza completa
```bash
# Parar e remover todos os containers e volumes
docker-compose down -v
docker-compose -f compose-test.yml down -v
# Limpar volumes não utilizados
docker volume prune
``` 