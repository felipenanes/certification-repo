# 🔐 Configuração OAuth2 e Spring Authorization Server

## 📋 Visão Geral

Este projeto utiliza o **Spring Authorization Server** para implementar um servidor OAuth2 completo com autenticação JWT.

## 🏗️ Arquitetura

### Componentes Principais:
- **Spring Authorization Server** - Servidor de autorização OAuth2
- **Spring Security** - Framework de segurança
- **JWT** - Tokens de acesso
- **BCrypt** - Criptografia de senhas
- **Database** - Usuários persistidos

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

### 1. AuthorizationServerConfig
```java
@Configuration
public class AuthorizationServerConfig {
    // Configuração do cliente OAuth2
    // Endpoints de autorização
    // Configurações JWT
}
```

### 2. SecurityConfig
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Configuração de segurança
    // PasswordEncoder (BCrypt)
    // Filtros de segurança
}
```

### 3. DatabaseUserDetailsService
```java
@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    // Carrega usuários do banco de dados
    // Implementa UserDetails
}
```

## 🔑 Clientes OAuth2 Configurados

### Cliente Principal:
- **Client ID**: `certification-app`
- **Client Secret**: `secret` (criptografado)
- **Grant Types**: 
  - Authorization Code
  - Refresh Token
- **Redirect URI**: `http://localhost:8080/home`
- **Scopes**: `openid`, `read`, `write`

## 👥 Usuários Padrão

### Admin:
- **Username**: `admin`
- **Password**: `admin123`
- **Roles**: `ROLE_ADMIN`

### User:
- **Username**: `user`
- **Password**: `user123`
- **Roles**: `ROLE_USER`

## 🔄 Fluxo de Autenticação

### 1. Authorization Code Flow:
```
1. Cliente redireciona para /oauth2/authorize
2. Usuário faz login
3. Servidor redireciona com authorization code
4. Cliente troca code por access token
5. Cliente usa access token nas requisições
```

### 2. Endpoints Disponíveis:
- `/oauth2/authorize` - Autorização
- `/oauth2/token` - Token
- `/oauth2/introspect` - Introspect
- `/oauth2/revoke` - Revogar token
- `/.well-known/oauth-authorization-server` - Metadata

## 🛡️ Segurança

### Senhas:
- **Criptografia**: BCrypt
- **Salt**: Automático
- **Rounds**: 10 (padrão)

### Tokens:
- **Tipo**: JWT
- **Assinatura**: HMAC-SHA256
- **Expiração**: Configurável

### Headers de Segurança:
- CSRF desabilitado para APIs
- CORS configurado
- Headers de segurança automáticos

## 🧪 Testando

### 1. Obter Authorization Code:
```
GET /oauth2/authorize?
  response_type=code&
  client_id=certification-app&
  redirect_uri=http://localhost:8080/home&
  scope=openid&
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

### Customizar JWT Claims:
```java
@Bean
public JWKSource<SecurityContext> jwkSource() {
    // Configuração customizada de JWT
}
```

### Adicionar Scopes Customizados:
```java
RegisteredClient.withId(UUID.randomUUID().toString())
    .clientId("custom-client")
    .scopes("custom-scope")
    // ...
```

### Configurar Expiração:
```java
.authorizationServerSettings(settings -> settings
    .issuer("http://localhost:8080")
    .authorizationEndpoint("/oauth2/authorize")
    .tokenEndpoint("/oauth2/token")
)
```

## 🚨 Troubleshooting

### Erro: "Invalid client"
- Verifique se o client_id está correto
- Verifique se o client_secret está correto

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