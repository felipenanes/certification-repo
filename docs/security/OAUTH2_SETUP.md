# 🔐 Configuração OAuth2 e Spring Authorization Server

## 📋 Visão Geral

Este projeto utiliza o **Spring Authorization Server** para implementar um servidor OAuth2 com autenticação JWT, integrado ao banco de dados e com clientes e usuários persistidos.

## 🏗️ Arquitetura

### Componentes Principais:
- **Spring Authorization Server** - Servidor de autorização OAuth2
- **Spring Security** - Framework de segurança
- **JWT** - Tokens de acesso
- **BCrypt** - Criptografia de senhas
- **Banco de Dados** - Usuários e clientes persistidos

## 📁 Estrutura de Arquivos

```
src/main/java/com/nnsgroup/certification/security/
├── config/
│   ├── AuthorizationServerConfig.java    # Configuração do servidor OAuth2
│   └── SecurityConfig.java               # Configuração de segurança
├── auth/
└── users/
    ├── domain/User.java                  # Entidade de usuário
    ├── repository/UserRepository.java    # Repositório JPA
    └── service/DatabaseUserDetailsService.java  # UserDetailsService
```

## ⚙️ Configurações

### AuthorizationServerConfig
- Registra o cliente OAuth2 no banco de dados, se não existir.
- Utiliza BCrypt para criptografar o client secret.
- Configura grant types: Authorization Code e Refresh Token.
- Define redirect URI e scopes.

### SecurityConfig
- Configura o PasswordEncoder (BCrypt).
- Define regras de segurança para endpoints.

## 🔑 Cliente OAuth2 Configurado

- **Client ID**: `certification-app`
- **Client Secret**: `secret` (armazenado criptografado)
- **Grant Types**: 
  - Authorization Code
  - Refresh Token
- **Redirect URI**: `http://localhost:8080/home`
- **Scopes**: `read`, `write`
- **Client Authentication**: `client_secret_basic`

## 👥 Usuários Padrão

- **admin** / 123456 (ROLE_ADMIN)
- **user** / 123456 (ROLE_USER)

## 🔄 Fluxo de Autenticação

### Authorization Code Flow:
1. Cliente redireciona para `/oauth2/authorize`
2. Usuário faz login
3. Servidor redireciona com authorization code
4. Cliente troca code por access token em `/oauth2/token`
5. Cliente usa access token nas requisições

### Endpoints Disponíveis:
- `/oauth2/authorize` - Autorização
- `/oauth2/token` - Token
- `/oauth2/introspect` - Introspect
- `/oauth2/revoke` - Revogar token
- `/.well-known/oauth-authorization-server` - Metadata

## 🛡️ Segurança

- **Senhas**: BCrypt
- **Tokens**: JWT (HMAC-SHA256)
- **CSRF**: Desabilitado para APIs
- **CORS**: Configurado

## 🧪 Testando

### 1. Obter Authorization Code:
```
GET /oauth2/authorize?
  response_type=code&
  client_id=certification-app&
  redirect_uri=http://localhost:8080/home&
  scope=read&
  state=123
```

### 2. Trocar Code por Token:
```
POST /oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&
code={authorization_code}&
redirect_uri=http://localhost:8080/home&
client_id=certification-app&
client_secret=secret
```

### 3. Usar Access Token:
```
GET /api/protected
Authorization: Bearer {access_token}
```

## 🔧 Configurações Avançadas

- É possível adicionar mais clientes OAuth2 via banco de dados.
- Scopes customizados podem ser definidos conforme a necessidade.
- Expiração dos tokens pode ser configurada no AuthorizationServerSettings.

## 🚨 Troubleshooting

### Erro: "Invalid client"
- Verifique se o client_id e client_secret estão corretos

### Erro: "Invalid grant"
- Verifique se o grant_type está suportado
- Verifique se o authorization_code não expirou

### Erro: "Invalid token"
- Verifique se o token não expirou
- Verifique se a assinatura está correta

## 📚 Referências

- [Spring Authorization Server Documentation](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/)
- [OAuth 2.0 RFC](https://tools.ietf.org/html/rfc6749)
- [JWT RFC](https://tools.ietf.org/html/rfc7519) 