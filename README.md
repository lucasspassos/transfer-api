# Transfer API

Este projeto é uma API REST desenvolvida para gerenciar transferências bancárias. A API permite o cadastro e gerenciamento de clientes, bem como a realização e consulta de transferências.

## Funcionalidades

- **Cadastro de Clientes**: Adicionar novos clientes ao sistema.
- **Listagem de Clientes**: Obter uma lista de todos os clientes.
- **Busca por Número de Conta**: Encontrar um cliente pelo número da conta.
- **Transferência entre Contas**: Realizar transferências entre contas.
- **Histórico de Transferências**: Consultar o histórico de transferências para um número de conta.

## Tecnologias

- **Java**: Versão 21
- **Spring Boot**: Framework para desenvolvimento da API
- **Lombok**: Biblioteca para reduzir boilerplate code
- **Swagger**: Documentação da API
- **JUnit**: Testes unitários e de integração
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes

## Requisitos

- Java 21
- Maven
- IDE (como IntelliJ IDEA ou Eclipse)

## Configuração

### Clonar o Repositório

Clone o repositório com o comando `git clone` e entre no diretório do projeto:

1. Execute `git clone https://github.com/seu_usuario/transfer-api.git`
2. Navegue para o diretório do projeto com `cd transfer-api`

### Configurar Dependências

As dependências do projeto estão configuradas no arquivo `pom.xml`. Verifique se o Maven está instalado e configurado corretamente em seu ambiente.

### Executar o Projeto

Para iniciar o projeto, use o comando `mvn spring-boot:run` do Maven.

### Acessar a API

Após iniciar o projeto, a API estará disponível no URL `http://localhost:8080`.

A documentação interativa da API pode ser acessada através do Swagger em `http://localhost:8080/swagger-ui.html`.

### Documentação Swagger

A documentação da API também pode ser visualizada diretamente no arquivo YAML do Swagger localizado em `src/main/resources/v1/swagger.yaml`.

## Endpoints

### Clientes

- **GET** `/api/v1/customers`: Lista todos os clientes.
- **GET** `/api/v1/customers/{accountNumber}`: Obtém um cliente pelo número da conta.
- **POST** `/api/v1/customers`: Cria um novo cliente.

### Transferências

- **GET** `/api/v1/transfers/history/{accountNumber}`: Obtém o histórico de transferências para um número de conta.
- **POST** `/api/v1/transfers`: Realiza uma nova transferência entre contas.

## Respostas da API

### Clientes

- **GET** `/api/v1/customers/{accountNumber}`
  - **200 OK**: Cliente encontrado.
  - **404 Not Found**: Cliente não encontrado.

### Transferências

- **GET** `/api/v1/transfers/history/{accountNumber}`
  - **200 OK**: Lista de transferências encontrada.
  - **404 Not Found**: Nenhuma transferência encontrada.

## Testes

Para rodar os testes unitários e de integração, use o comando `mvn test`.

## Contribuição

1. Faça um fork do repositório.
2. Crie uma branch para suas alterações com `git checkout -b feature/MinhaFeature`.
3. Faça suas alterações e commit com `git commit -am 'Adiciona nova feature'`.
4. Faça um push para a branch com `git push origin feature/MinhaFeature`.
5. Abra um Pull Request.

## Contato

Para mais informações, entre em contato com lucas.spassos@outlook.com.
