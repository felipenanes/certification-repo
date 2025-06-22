# 📚 Documentação do Projeto Certification

Esta pasta contém toda a documentação técnica e guias do projeto.

## 📁 Estrutura de Pastas

### 🗄️ `database/` - Documentação de Banco de Dados
- **DATABASE_SETUP.md** - Configuração e setup dos bancos de dados
- **DBeaver_Connection_Guide.md** - Guia para conectar no DBeaver
- **migrations/** - Documentação das migrações Liquibase

### 🔐 `security/` - Documentação de Segurança
- **OAUTH2_SETUP.md** - Configuração OAuth2 e Spring Authorization Server
- **AUTHENTICATION_FLOW.md** - Fluxo de autenticação
- **USER_MANAGEMENT.md** - Gerenciamento de usuários e roles

### ⚙️ `setup/` - Guias de Configuração
- **ENVIRONMENT_SETUP.md** - Configuração do ambiente de desenvolvimento
- **DOCKER_SETUP.md** - Configuração do Docker e containers
- **DEPLOYMENT.md** - Guia de deploy

### 🔌 `api/` - Documentação da API
- **API_ENDPOINTS.md** - Endpoints disponíveis
- **API_AUTHENTICATION.md** - Como autenticar nas APIs
- **SWAGGER_SETUP.md** - Configuração do Swagger/OpenAPI

## 🚀 Início Rápido

### Para Desenvolvedores:
1. **Configuração**: `setup/ENVIRONMENT_SETUP.md`
2. **Banco de Dados**: `database/DATABASE_SETUP.md`
3. **Segurança**: `security/OAUTH2_SETUP.md`

### Para DevOps:
1. **Docker**: `setup/DOCKER_SETUP.md`
2. **Deploy**: `setup/DEPLOYMENT.md`

### Para Testes:
1. **API**: `api/API_ENDPOINTS.md`
2. **Autenticação**: `api/API_AUTHENTICATION.md`

## 📝 Convenções

- **Markdown** para toda documentação
- **Imagens** em pasta `images/` dentro de cada subpasta
- **Exemplos de código** com syntax highlighting
- **Links relativos** para navegação entre docs

## 🔄 Manutenção

- **Atualizar docs** sempre que houver mudanças no código
- **Versionar** junto com o código
- **Revisar** periodicamente para manter consistência 