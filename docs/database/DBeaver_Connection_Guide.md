# Guia de Conexão DBeaver

## 📋 Pré-requisitos

1. **DBeaver instalado** (Community Edition é gratuita)
2. **Docker rodando** com o banco PostgreSQL
3. **Driver PostgreSQL** (geralmente já vem com o DBeaver)

## 🐘 Conectando ao PostgreSQL

### Passo 1: Iniciar o banco
```bash
# Produção (porta 5432)
docker-compose up -d
# Desenvolvimento/Teste (porta 5433)
docker-compose -f docker-compose-test.yml up -d
```

### Passo 2: Criar nova conexão no DBeaver

1. **Clique em "Nova Conexão"** (ícone de tomada +)
2. **Selecione "PostgreSQL"**
3. **Clique em "Próximo"**

### Passo 3: Configurar parâmetros

**Configurações principais:**
- **Host**: `localhost`
- **Port**: `5432` (produção) ou `5433` (dev/teste)
- **Database**: `certification_db` (produção) ou `certification_test_db` (dev/teste)
- **Username**: `certification_user` (produção) ou `certification_test_user` (dev/teste)
- **Password**: `certification_pass` (produção) ou `certification_test_pass` (dev/teste)

**Configurações avançadas (aba "Driver properties"):**
- `ssl`: `false`
- `sslmode`: `disable`

### Passo 4: Testar conexão
1. **Clique em "Test Connection"**
2. **Se der erro, verifique:**
   - Docker está rodando?
   - Banco foi iniciado?
   - Porta correta?

### Passo 5: Conectar
1. **Clique em "Finish"**
2. **A conexão aparecerá na árvore de conexões**

## 🔍 Explorando as Tabelas

### Tabelas principais:
- `users` - Dados dos usuários
- `user_roles` - Roles dos usuários
- `databasechangelog` - Controle do Liquibase
- `databasechangeloglock` - Lock do Liquibase

### Queries úteis:

**Ver todos os usuários:**
```sql
SELECT id, username, email, enabled FROM users;
```

**Ver usuários com suas roles:**
```sql
SELECT u.username, u.email, ur.role 
FROM users u 
JOIN user_roles ur ON u.id = ur.user_id;
```

**Ver estrutura da tabela users:**
```sql
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'users';
```

## 🚨 Solução de Problemas

### Erro: "Connection refused"
- Verifique se o Docker está rodando
- Verifique se o banco foi iniciado: `docker ps`

### Erro: "Authentication failed"
- Verifique username/password
- Para PostgreSQL: `certification_user`/`certification_pass` (produção) ou `certification_test_user`/`certification_test_pass` (dev/teste)

### Erro: "Database not found"
- Para PostgreSQL: verifique se o nome é `certification_db` (produção) ou `certification_test_db` (dev/teste)

### Erro: "Driver not found"
- Baixe o driver PostgreSQL no DBeaver
- Vá em: Database → Driver Manager → PostgreSQL → Edit → Find Class

## 📊 Dicas

1. **Use o PostgreSQL** para desenvolvimento mais robusto
2. **Sempre teste a conexão** antes de usar
3. **Salve as configurações** para reutilizar depois 