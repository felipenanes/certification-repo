# ⚙️ Configuração do Ambiente de Desenvolvimento

## 📋 Pré-requisitos

### Software Necessário:
- **Java 17** ou superior
- **Gradle 8.x** (wrapper incluído)
- **Docker Desktop** (opcional, para PostgreSQL)
- **IDE** (IntelliJ IDEA, Eclipse, VS Code)

### Ferramentas Recomendadas:
- **DBeaver** - Cliente de banco de dados
- **Postman** - Teste de APIs
- **Git** - Controle de versão

## 🚀 Instalação

### 1. Java 17
```bash
# Verificar se Java está instalado
java -version
```

### 2. Docker Desktop (opcional, para PostgreSQL)
```bash
# Baixar e instalar Docker Desktop
# Iniciar o Docker Desktop
docker --version
```

### 3. IDE
- **IntelliJ IDEA**: Recomendado para Spring Boot
- **Eclipse**: Com plugin Spring Tools
- **VS Code**: Com extensões Java e Spring Boot

## 🔧 Configuração do Projeto

### 1. Clone do Repositório
```bash
git clone <repository-url>
cd certification
```

### 2. Configurar Variáveis de Ambiente
```bash
# Windows (PowerShell)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:PATH += ";$env:JAVA_HOME\bin"

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export PATH=$PATH:$JAVA_HOME/bin
```

### 3. Verificar Dependências
```bash
# Verificar se o Gradle está funcionando
./gradlew --version

# Baixar dependências
./gradlew build
```

## 🗄️ Configuração de Banco de Dados

### Opção 1: H2 (Padrão para desenvolvimento)
```bash
# Executar aplicação (cria banco H2 automaticamente)
./gradlew bootRun
```

### Opção 2: PostgreSQL (Recomendado para produção e integração)
```bash
# Iniciar PostgreSQL (produção)
docker-compose up -d

# Iniciar PostgreSQL (desenvolvimento/teste)
docker-compose -f docker-compose-test.yml up -d

# Executar aplicação com profile desejado
# Produção:
./gradlew bootRun --args='--spring.profiles.active=prod'
# Teste:
./gradlew bootRun --args='--spring.profiles.active=test'
# Desenvolvimento (padrão):
./gradlew bootRun
```

## 🔐 Configuração de Segurança

### Usuários Padrão:
- **admin** / 123456 (ROLE_ADMIN)
- **user** / 123456 (ROLE_USER)

### Configurações OAuth2:
- **Client ID**: certification-app
- **Client Secret**: secret
- **Redirect URI**: http://localhost:8080/home
- **Scopes**: read, write

## 🧪 Testando o Ambiente

### 1. Verificar se a aplicação inicia:
```bash
./gradlew bootRun
```

### 2. Testar endpoints:
```bash
# Health check
curl http://localhost:8080/actuator/health

# OAuth2 metadata
curl http://localhost:8080/.well-known/oauth-authorization-server
```

### 3. Testar banco de dados:
- Conectar no DBeaver ou pgAdmin
- Verificar se as tabelas foram criadas

## 📁 Estrutura do Projeto

```
certification/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/nnsgroup/certification/
│   │   │       ├── security/          # Configurações de segurança
│   │   │       ├── users/             # Módulo de usuários
│   │   │       ├── attempts/          # Módulo de tentativas
│   │   │       ├── documents/         # Módulo de documentos
│   │   │       └── providers/         # Módulo de provedores
│   │   └── resources/
│   │       ├── application.yaml       # Configuração principal
│   │       ├── application-prod.yaml  # Configuração produção
│   │       ├── application-test.yaml  # Configuração testes
│   │       └── db/changelog/          # Migrações Liquibase
│   └── test/
├── docs/                              # Documentação
├── docker-compose.yml                 # Docker PostgreSQL + pgAdmin (produção)
├── docker-compose-test.yml            # Docker PostgreSQL (desenvolvimento/teste)
├── docker-compose-simple.yml          # Docker PostgreSQL apenas
└── build.gradle                       # Dependências e configuração
```

## 🔧 Configurações de IDE

### IntelliJ IDEA:
1. **Importar projeto** como projeto Gradle
2. **Configurar SDK** para Java 17
3. **Habilitar annotation processing**
4. **Configurar run configurations**

### Eclipse:
1. **Importar projeto** como projeto Gradle
2. **Configurar JRE** para Java 17
3. **Instalar Spring Tools** plugin

### VS Code:
1. **Instalar extensões**:
   - Extension Pack for Java
   - Spring Boot Extension Pack
2. **Configurar Java Home**
3. **Habilitar Spring Boot Dashboard**

## 🚨 Troubleshooting

### Erro: "JAVA_HOME is not set"
```bash
# Configurar JAVA_HOME
export JAVA_HOME=/path/to/java17
export PATH=$PATH:$JAVA_HOME/bin
```

### Erro: "Port 8080 already in use"
```bash
# Verificar processo na porta
netstat -ano | findstr :8080
# Matar processo
taskkill /PID <process_id>
```

### Erro: "Database connection failed"
```bash
# Verificar se Docker está rodando
docker ps
# Verificar logs do PostgreSQL
docker-compose logs postgres
```

### Erro: "Gradle daemon failed"
```bash
# Limpar cache do Gradle
./gradlew clean
./gradlew --stop
```

## 📚 Recursos Adicionais

- [docs/database/DATABASE_SETUP.md](../database/DATABASE_SETUP.md) - Setup do banco
- [docs/security/OAUTH2_SETUP.md](../security/OAUTH2_SETUP.md) - Configuração OAuth2

### Links Úteis:
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Gradle User Guide](https://docs.gradle.org/current/userguide/userguide.html) 