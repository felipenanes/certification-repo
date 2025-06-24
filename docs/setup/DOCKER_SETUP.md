# 🐳 Configuração do Docker

## 📋 Visão Geral

Este projeto utiliza Docker para facilitar o desenvolvimento e deploy do banco de dados PostgreSQL, com opção de interface web via pgAdmin.

## 🏗️ Arquitetura Docker

### Containers Disponíveis:

1. **PostgreSQL** - Banco de dados principal
2. **pgAdmin** - Interface web para gerenciar o banco (opcional, produção)

## 📁 Arquivos Docker

### `docker-compose.yml` (Produção)
- PostgreSQL + pgAdmin
- Rede customizada
- Volumes persistentes

### `docker-compose-test.yml` (Desenvolvimento/Teste)
- Apenas PostgreSQL
- Porta diferente (5433)

### `docker-compose-simple.yml` (Simples)
- Apenas PostgreSQL
- Configuração mínima

## 🚀 Comandos Básicos

### Iniciar Serviços:
```bash
# Produção (PostgreSQL + pgAdmin)
docker-compose up -d

# Desenvolvimento/Teste (PostgreSQL)
docker-compose -f docker-compose-test.yml up -d

# Versão simples (apenas PostgreSQL)
docker-compose -f docker-compose-simple.yml up -d
```

### Parar Serviços:
```bash
# Produção
docker-compose down

# Desenvolvimento/Teste
docker-compose -f docker-compose-test.yml down

# Simples
docker-compose -f docker-compose-simple.yml down
```

### Ver Logs:
```bash
# Logs de todos os serviços
docker-compose logs

# Logs apenas do PostgreSQL
docker-compose logs postgres

# Logs em tempo real
docker-compose logs -f postgres
```

### Verificar Status:
```bash
# Ver containers rodando
docker ps

# Ver todos os containers (incluindo parados)
docker ps -a
```

## 🔧 Configurações

### PostgreSQL (produção):
- **Imagem**: `postgres:15-alpine`
- **Porta**: `5432`
- **Database**: `certification_db`
- **Usuário**: `certification_user`
- **Senha**: `certification_pass`
- **Volume**: `postgres_data`

### PostgreSQL (desenvolvimento/teste):
- **Porta**: `5433`
- **Database**: `certification_test_db`
- **Usuário**: `certification_test_user`
- **Senha**: `certification_test_pass`

### pgAdmin (produção):
- **Imagem**: `dpage/pgadmin4:latest`
- **Porta**: `8081`
- **Email**: `admin@certification.com`
- **Senha**: `admin123`

## 🗄️ Acessando o Banco

### Via Docker CLI:
```bash
# Conectar ao PostgreSQL (produção)
docker exec -it certification-postgres psql -U certification_user -d certification_db

# Conectar ao PostgreSQL (desenvolvimento/teste)
docker exec -it certification-postgres-test psql -U certification_test_user -d certification_test_db
```

### Via DBeaver:
- **Host**: `localhost`
- **Port**: `5432` (produção) ou `5433` (desenvolvimento/teste)
- **Database**: `certification_db` ou `certification_test_db`
- **Username**: `certification_user` ou `certification_test_user`
- **Password**: `certification_pass` ou `certification_test_pass`

### Via pgAdmin (produção):
- **URL**: http://localhost:8081
- **Email**: `admin@certification.com`
- **Senha**: `admin123`

## 📊 Volumes e Dados

### Volumes Criados:
```bash
# Listar volumes
docker volume ls

# Ver detalhes do volume
docker volume inspect certification_postgres_data
```

### Backup e Restore:
```bash
# Backup do banco (produção)
docker exec certification-postgres pg_dump -U certification_user certification_db > backup.sql

# Restore do banco (produção)
docker exec -i certification-postgres psql -U certification_user certification_db < backup.sql
```

## 🔍 Troubleshooting

### Container não inicia:
```bash
# Ver logs detalhados
docker-compose logs postgres
# Verificar se a porta está livre
netstat -ano | findstr :5432
# Reiniciar container
docker-compose restart postgres
```

### Erro de conexão:
```bash
# Verificar se o container está rodando
docker ps
# Verificar logs
docker-compose logs postgres
# Testar conectividade
docker exec certification-postgres pg_isready -U certification_user
```

### Volume corrompido:
```bash
# Parar serviços
docker-compose down
# Remover volume
docker volume rm certification_postgres_data
# Reiniciar (criará novo volume)
docker-compose up -d
```

## 🧹 Limpeza

### Remover containers parados:
```bash
docker container prune
```

### Remover volumes não utilizados:
```bash
docker volume prune
```

### Remover tudo (cuidado!):
```bash
# Para todos os containers
docker-compose down
# Remove volumes também
docker-compose down -v
# Limpeza completa
docker system prune -a
```

## 🔧 Configurações Avançadas

### Variáveis de Ambiente (exemplo):
```yaml
environment:
  POSTGRES_DB: certification_db
  POSTGRES_USER: certification_user
  POSTGRES_PASSWORD: certification_pass
  POSTGRES_INITDB_ARGS: "--encoding=UTF-8 --lc-collate=C --lc-ctype=C"
```

## 🚨 Boas Práticas

1. **Sempre use volumes** para persistir dados
2. **Faça backups regulares** dos dados importantes
3. **Use health checks** para monitoramento
4. **Configure logs** adequadamente
5. **Use secrets** para senhas em produção
6. **Limpe recursos** não utilizados regularmente 