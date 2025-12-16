# AllUtils

## 💻 Sobre o Projeto

**AllUtils** é uma aplicação Full Stack que combina um backend robusto em **Java/Spring Boot** com um microsserviço utilitário em **Python/FastAPI**. O objetivo principal é fornecer ferramentas utilitárias de alta qualidade para facilitar o dia a dia dos usuários, como geração de QR Codes e conversão de arquivos.

O projeto é estruturado em dois serviços principais orquestrados via Docker Compose:
1.  **`java-app`**: O backend principal, responsável pela lógica de negócios, autenticação JWT e comunicação com o frontend e o microsserviço Python.
2.  **`python-service`**: O microsserviço dedicado a processamento de tarefas pesadas, como conversão de arquivos e geração de imagens, acessível pelo `java-app` através da URL interna `http://python-service:8000`.

## ✨ Funcionalidades

O **AllUtils** oferece as seguintes ferramentas e serviços:

* **Gerador de QR Code**
    * Gera códigos QR para qualquer string de texto fornecida.
    * A funcionalidade é implementada no serviço Python (`qr_service.py`) e exposta no backend Java pelo endpoint `/api/v1/qr/`.

* **Conversor de PDF para DOCX**
    * Permite o upload de um arquivo PDF para convertê-lo em um documento DOCX.
    * Esta conversão é executada no microsserviço Python (`convert_service.py`) e gerenciada pelo controlador Java `/api/v1/converter/pdf-to-docx`.

* **Sistema de Autenticação (JWT)**
    * Permite o registro de novos usuários (`/auth/register`) com a *role* padrão `BASIC`.
    * Permite o login de usuários cadastrados (`/auth/login`), gerando um **JSON Web Token (JWT)** para acesso a rotas autenticadas.
    * A segurança é configurada no Spring Security para ser `STATELESS`.

## 🛠️ Stack de Tecnologias

### Java Backend (`java-app`)

| Categoria | Tecnologia | Detalhes |
| :--- | :--- | :--- |
| **Linguagem** | Java | 21 |
| **Framework** | Spring Boot | 4.0.0 |
| **Segurança** | Spring Security, JJWT | Autenticação Stateless com JWT |
| **Persistência** | Spring Data JPA, H2 | H2 em memória para desenvolvimento |
| **Comunicação** | Spring WebFlux (WebClient) | Integração com o microsserviço Python |

### Python Microservice (`python-service`)

| Categoria | Tecnologia | Detalhes |
| :--- | :--- | :--- |
| **Framework** | FastAPI, uvicorn | API de alta performance |
| **Utilitários** | `qrcode`, `pillow` | Geração de QR Codes |
| **Utilitários** | `pdf2docx` | Conversão de PDF para DOCX |

## 🚀 Como Rodar o Projeto

Este projeto utiliza o Docker Compose para orquestrar os serviços Java e Python.

### Pré-requisitos

* Docker
* Docker Compose

### 📦 Configuração e Inicialização

1.  **Build e Inicialização:**
    Navegue até o diretório raiz do projeto (`AllUtils`) onde o arquivo `docker-compose.yml` está localizado e execute o comando:

    ```bash
    docker-compose up --build
    ```

    Isso irá construir e iniciar os dois serviços. O `java-app` será executado na porta `8080`.

2.  **Acesso à Aplicação:**
    Após a inicialização, a aplicação web (Frontend e API) estará acessível em:

    ```
    http://localhost:8080
    ```

## 🌐 Endpoints da API (Java Backend)

Todos os endpoints da API estão expostos pelo serviço Java (porta 8080).

### Autenticação (Acesso Público)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/auth/register` | Cria um novo usuário com a role `BASIC`. |
| `POST` | `/auth/login` | Autentica um usuário e retorna um JWT. |

### Serviços Utilitários (Requer Autenticação JWT)

Para acessar estes endpoints, inclua o cabeçalho `Authorization: Bearer <token>` na sua requisição, utilizando o token obtido no login.

| Método | Endpoint | Descrição | Consumes | Produces |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/qr/` | Gera um QR Code em formato PNG. O corpo da requisição deve ser a string de texto a ser codificada. | `text/plain` | `image/png` |
| `POST` | `/api/v1/converter/pdf-to-docx` | Converte um PDF para DOCX. O arquivo PDF deve ser enviado como `MultipartFile` com o nome de parâmetro `file`. | `multipart/form-data` | `application/vnd.openxmlformats-officedocument.wordprocessingml.document` |

## ⚠️ Configuração

O backend Java utiliza as seguintes configurações do `application.properties`:

```properties
spring.application.name=AllUtils
server.port=8080

# Configuração H2 em memória
spring.datasource.url=jdbc:h2:mem:allutilsdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=nvfuejbibniubetrniuvcin47ui3ty78hgruejhy
jwt.validity=3600000

# Comunicação com o serviço Python
python.base-url=http://python-service:8000
```

- **Nota:** O `python.base-url` utiliza o nome do serviço definido no `docker-compose.yml` para comunicação interna entre containers.

## 📄 Licença

Este projeto está sob a licença **MIT**, com Copyright (c) 2025 Murilo de Andrade.