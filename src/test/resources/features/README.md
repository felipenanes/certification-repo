# Cucumber Features for Provider Management

Este diretório contém as features de Cucumber para testar o ProviderController da aplicação.

## Features Disponíveis

### 1. providers.feature
Testa os cenários principais do ProviderController:
- **Busca por ID**: Testa a busca de provider por ID (sucesso e não encontrado)
- **Criação**: Testa a criação de novos providers (sucesso, dados inválidos, nome duplicado)

### 2. provider-validation.feature
Testa cenários específicos de validação:
- **Campos obrigatórios**: Testa criação com nome null, vazio ou muito longo
- **Campos opcionais**: Testa criação com website null
- **Caracteres especiais**: Testa criação com caracteres especiais e internacionais

### 3. provider-listing.feature
Prepara cenários para futuras implementações:
- **Listagem**: Testa listagem de todos os providers
- **Paginação**: Testa listagem com paginação
- **Filtros**: Testa listagem com filtros por nome

## Estrutura dos Steps

Os steps estão implementados em `ProviderSteps.java` e incluem:

### Background Steps (Given)
- Configuração do sistema
- Preparação de dados de teste
- Criação de providers para cenários específicos

### Execution Steps (When)
- Requisições HTTP para os endpoints
- Tratamento de exceções

### Assertion Steps (Then)
- Validação de respostas
- Verificação de status HTTP
- Validação de dados retornados

## Como Executar

Para executar as features, use o comando:
```bash
./gradlew test --tests "*CucumberTestSuite*"
```

## Endpoints Testados

- `GET /providers/{providerId}` - Buscar provider por ID
- `POST /providers` - Criar novo provider

## Cenários de Erro Cobertos

- Provider não encontrado (404)
- Dados inválidos (400)
- Conflito de nome duplicado (409)
- Validações de campos obrigatórios
- Validações de formato de dados 