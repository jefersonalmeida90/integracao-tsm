# Middleware Integração TSM

API intermediária em **Java / Spring Boot** para integração com as plataformas **Lincros Routing** e **Lincros Tracking**.

---

## Visão Geral

Este projeto atua como um middleware entre o ERP/sistemas internos da Dalcol e as duas APIs externas da Lincros:

| Plataforma | URL Base | Finalidade |
|---|---|---|
| **Lincros Routing** | `https://routing.lincros.com` | Importação e cancelamento de pedidos; consulta de sessões de roteirização |
| **Lincros Tracking** | `https://dalcol-tracking.lincros.com` | Importação de rotas para acompanhamento logístico |

O projeto foi desenvolvido aplicando todos os conceitos, padrões e comportamentos para o ecossistema Java / Spring Boot.

---

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.6 | Framework base |
| Spring Web MVC | (Boot) | Controllers REST |
| Spring Retry | (Boot) | Retry automático em falhas |
| Spring AOP | (Boot) | Suporte ao Spring Retry |
| Bean Validation (Jakarta) | (Boot) | Validação de entrada |
| Jackson | (Boot) | Serialização/desserialização JSON |
| Lombok | (Boot) | Redução de boilerplate |
| SpringDoc OpenAPI | 2.8.9 | Swagger UI / documentação |

---

## Arquitetura

O projeto segue uma arquitetura em camadas inspirada em **Clean Architecture / Ports & Adapters**, replicando a estrutura de projetos do modelo C# (Domain → Application → Infrastructure → API).

```
integracao-lincros-tms/
└── src/main/java/br/com/nlevel/integracaolincrostms/
    │
    ├── domain/
    │   ├── model/               # Entidades de resposta da Lincros
    │   │   ├── SessaoResponse
    │   │   └── ConsultarSessaoResponse  (com classes internas: Rota, Atividade, Pedido, Item)
    │   └── port/                # Interfaces (contratos) dos clientes HTTP
    │       ├── LincrosApiClientPort
    │       └── TrackingApiClientPort
    │
    ├── infrastructure/
    │   ├── http/                # Implementações dos clientes HTTP (adapters)
    │   │   ├── LincrosApiClient
    │   │   ├── TrackingApiClient
    │   │   ├── LincrosErrorResponse
    │   │   └── request/
    │   │       └── CancelarPedidoRequest
    │   └── exception/           # Hierarquia de exceções da Lincros
    │       ├── LincrosApiException          (base)
    │       ├── LincrosValidationException   (400)
    │       ├── LincrosUnauthorizedException (401/403)
    │       ├── LincrosNotFoundException     (404)
    │       └── LincrosUnavailableException  (5xx)
    │
    ├── application/
    │   ├── command/             # Objetos de comando (input + payload para a Lincros)
    │   │   ├── ImportarPedidoCommand        (com ClienteCommand, FornecedorCommand, etc.)
    │   │   ├── CancelarPedidoCommand
    │   │   └── ImportarRotaTrackingCommand  (com todas as classes aninhadas)
    │   └── usecase/             # Casos de uso (orquestração da lógica)
    │       ├── ImportarPedidoUseCase
    │       ├── CancelarPedidoUseCase
    │       ├── ImportarRotaTrackingUseCase
    │       ├── ConsultarSessaoUseCase
    │       └── PesquisarSessaoUseCase
    │
    ├── controller/
    │   ├── PedidosController    # POST /api/v1/integracao/pedidos/importar e /cancelar
    │   ├── SessoesController    # GET /api/v1/integracao/sessoes/{id} e POST /pesquisar
    │   ├── TrackingController   # POST /api/v1/integracao/tracking/rotas/importar
    │   └── dto/                 # DTOs de entrada dos endpoints (desacoplados dos commands)
    │       ├── ImportarPedidoDto
    │       ├── ClienteDto / FornecedorDto / ItemPedidoDto
    │       ├── PontoInteresseDto / JanelaDto
    │       └── PesquisarSessaoRequestDto
    │
    ├── config/
    │   ├── LincrosClientConfig      # Beans RestClient para Routing e Tracking
    │   ├── RoutingTokenInterceptor  # Interceptor que encaminha o Bearer token
    │   └── WebConfig                # Configuração de CORS
    │
    └── handler/
        └── GlobalExceptionHandler   # @ControllerAdvice — tratamento global de exceções
```

---

## Endpoints

### Routing — Pedidos

| Método | Path | Descrição |
|---|---|---|
| `POST` | `/api/v1/integracao/pedidos/importar` | Importa um pedido para a Lincros Routing |
| `POST` | `/api/v1/integracao/pedidos/cancelar` | Cancela um ou mais pedidos na Lincros Routing |

### Routing — Sessões

| Método | Path | Descrição |
|---|---|---|
| `GET` | `/api/v1/integracao/sessoes/{idSessao}` | Consulta uma sessão de roteirização pelo ID |
| `POST` | `/api/v1/integracao/sessoes/pesquisar` | Pesquisa sessões por intervalo de data |

### Tracking — Rotas

| Método | Path | Descrição |
|---|---|---|
| `POST` | `/api/v1/integracao/tracking/rotas/importar` | Importa rota(s) para o Lincros Tracking |

---

## Configuração

Todas as configurações ficam em `src/main/resources/application.yml`:

```yaml
lincros:
  base-url: https://routing.lincros.com
  api-key: <TOKEN_BASE64>         # Usado como fallback se o client não enviar Authorization

lincros-tracking:
  base-url: https://dalcol-tracking.lincros.com
  api-key: ""                     # API Key injetada no header da requisição ao Tracking
  api-key-header-name: apiKey

cors:
  allow-all-origins: false
  allowed-origins:
    - "https://meu-erp.dalcol.com.br"
```

---

## Autenticação

### Lincros Routing

O middleware utiliza autenticação **Bearer Token**. O fluxo é:

1. O client (ERP) envia o header `Authorization: Bearer <token>` na requisição ao middleware.
2. O `RoutingTokenInterceptor` intercepta a chamada, extrai o token e o encaminha automaticamente para a Lincros Routing.
3. Se nenhum token for enviado pelo client, o middleware usa o valor configurado em `lincros.api-key` como fallback.

### Lincros Tracking

Utiliza **API Key** via header HTTP configurado em `lincros-tracking.api-key-header-name` (padrão: `apiKey`). A chave é injetada automaticamente em todas as requisições ao Tracking pelo bean `trackingRestClient`.

---

## Retry Automático

Falhas temporárias (erros 5xx, `ServiceUnavailable`, `GatewayTimeout`, erros de rede) ativam retry automático via **Spring Retry**:

- **Máximo de tentativas:** 5 (1 inicial + 4 retries)
- **Estratégia de backoff:** exponencial com multiplicador 2x, iniciando em 5 segundos, limitado a 60 segundos
- **Exceções que disparam retry:** `LincrosUnavailableException` e `ResourceAccessException`

Isso replica o comportamento do modelo C# que usava **Polly** com intervalos de 5s → 15s → 30s → 60s.

---

## Tratamento de Erros

O `GlobalExceptionHandler` (`@ControllerAdvice`) intercepta exceções da Lincros e retorna respostas no formato `application/problem+json`:

```json
{
  "status": 400,
  "title": "Requisição rejeitada pela Lincros",
  "detail": "Campo X: mensagem de erro da Lincros",
  "type": "https://httpstatuses.com/400",
  "instance": "/api/v1/integracao/pedidos/importar",
  "lincrosStatusCode": 400,
  "errors": { "CodigoPedidoNegocio": ["Campo obrigatório"] }
}
```

| Exceção | HTTP Status retornado |
|---|---|
| `LincrosValidationException` | `400 Bad Request` |
| `LincrosUnauthorizedException` | `401 Unauthorized` |
| `LincrosNotFoundException` | `404 Not Found` |
| `LincrosUnavailableException` | `502 Bad Gateway` |
| Erros de validação da API (`@Valid`) | `400 Bad Request` |

---

## Serialização JSON

Os comandos enviados para a Lincros usam **PascalCase** nos campos JSON (padrão do modelo C# de referência), implementado via `@JsonProperty` em cada campo:

```java
@JsonProperty("CodigoPedidoNegocio")
private String codigoPedidoNegocio;
```

Campos nulos são omitidos automaticamente via `@JsonInclude(JsonInclude.Include.NON_NULL)`.

---

## Swagger / Documentação

Após iniciar a aplicação, a documentação interativa está disponível em:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/api-docs`

---

## Como Executar

```bash
# Compilar e executar com Maven Wrapper
./mvnw spring-boot:run

# Ou via JAR
./mvnw package -DskipTests
java -jar target/integracao-lincros-tms-0.0.1-SNAPSHOT.jar
```

A aplicação sobe na porta `8080` por padrão.

---
