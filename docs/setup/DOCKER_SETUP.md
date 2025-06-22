# 🐳 Configuração do Docker

## 📋 Visão Geral

Este projeto utiliza Docker para facilitar o desenvolvimento e deploy com PostgreSQL.

## 🏗️ Arquitetura Docker

### Containers Disponíveis:

1. **PostgreSQL** - Banco de dados principal
2. **pgAdmin** - Interface web para gerenciar o banco (opcional)

## 📁 Arquivos Docker

### `docker-compose.yml` (Versão Completa)
- PostgreSQL + pgAdmin
- Rede customizada
- Volumes persistentes

### `docker-compose-simple.yml` (Versão Simplificada)
- Apenas PostgreSQL
- Configuração mínima

## 🚀 Comandos Básicos

### Iniciar Serviços:
```bash
# Versão completa (PostgreSQL + pgAdmin)
docker-compose up -d

# Versão simplificada (apenas PostgreSQL)
docker-compose -f docker-compose-simple.yml up -d
```

### Parar Serviços:
```bash
# Versão completa
docker-compose down

# Versão simplificada
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

### PostgreSQL:
- **Imagem**: `postgres:15-alpine`
- **Porta**: `5432`
- **Database**: `certification_db`
- **Usuário**: `certification_user`
- **Senha**: `certification_pass`
- **Volume**: `postgres_data`

### pgAdmin (versão completa):
- **Imagem**: `dpage/pgadmin4:latest`
- **Porta**: `8081`
- **Email**: `admin@certification.com`
- **Senha**: `admin123`

## 🗄️ Acessando o Banco

### Via Docker CLI:
```bash
# Conectar ao PostgreSQL
docker exec -it certification-postgres psql -U certification_user -d certification_db

# Executar query
docker exec -it certification-postgres psql -U certification_user -d certification_db -c "SELECT * FROM users;"
```

### Via DBeaver:
- **Host**: `localhost`
- **Port**: `5432`
- **Database**: `certification_db`
- **Username**: `certification_user`
- **Password**: `certification_pass`

### Via pgAdmin (versão completa):
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

# Backup do volume
docker run --rm -v certification_postgres_data:/data -v $(pwd):/backup alpine tar czf /backup/postgres_backup.tar.gz -C /data .
```

### Backup e Restore:
```bash
# Backup do banco
docker exec certification-postgres pg_dump -U certification_user certification_db > backup.sql

# Restore do banco
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

### Variáveis de Ambiente:
```yaml
environment:
  POSTGRES_DB: certification_db
  POSTGRES_USER: certification_user
  POSTGRES_PASSWORD: certification_pass
  POSTGRES_INITDB_ARGS: "--encoding=UTF-8 --lc-collate=C --lc-ctype=C"
```

### Configurações de Performance:
```yaml
postgres:
  environment:
    POSTGRES_SHARED_BUFFERS: 256MB
    POSTGRES_EFFECTIVE_CACHE_SIZE: 1GB
    POSTGRES_WORK_MEM: 4MB
```

### Health Checks:
```yaml
healthcheck:
  test: ["CMD-SHELL", "pg_isready -U certification_user"]
  interval: 30s
  timeout: 10s
  retries: 3
```

## 📚 Comandos Úteis

### Desenvolvimento:
```bash
# Iniciar apenas PostgreSQL
docker-compose -f docker-compose-simple.yml up -d

# Executar aplicação com PostgreSQL
./gradlew bootRun --args='--spring.profiles.active=postgres'

# Ver logs em tempo real
docker-compose logs -f postgres
```

### Produção:
```bash
# Backup automático
docker exec certification-postgres pg_dump -U certification_user certification_db | gzip > backup_$(date +%Y%m%d_%H%M%S).sql.gz

# Monitoramento
docker stats certification-postgres
```

## 🚨 Boas Práticas

1. **Sempre use volumes** para persistir dados
2. **Faça backups regulares** dos dados importantes
3. **Use health checks** para monitoramento
4. **Configure logs** adequadamente
5. **Use secrets** para senhas em produção
6. **Limpe recursos** não utilizados regularmente 