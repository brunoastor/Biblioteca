# Livraria API

Aplicação para cadastro de usuarios, livros e controle de empréstimos para uma biblioteca.

Alem de serviço de recomendação e integração com a API Google Books

## 🚀 Começando

__git clone https://github.com/brunoastor/MUDAR__

### 📋 Pré-requisitos

```
Git
Java 21
Maven
Mysql
application.properties
```

### 🔧 Instalação

No terminal, navegue até a pasta do projeto e execute o seguinte comando

```
mvn clean install
```
É necessário que haja uma instância do MySQL rodando na porta 3306

Em seguida navegue até a pasta target e execute o arquivo jar criado
```
java -jar library-0.0.1-SNAPSHOT.jar
```

## ⚙️ Executando os testes

Execute o seguinte comando na pasta do projeto

```
mvn test
```

## ✔ Documentação

Todos os endpoints estão detalhados no seguinte endereço
```
http://localhost:8088/swagger-ui/index.html#
```

## 🛠️ Construído com
* [Java](https://www.oracle.com/br/java/technologies/downloads/#java21) - SDK 21
* [Spring Boot 3](https://spring.io/) - Framework
* [Maven](https://maven.apache.org/) - Dependências
* [Swagger](https://swagger.io/) - Documentação Endpoints
* [Lombok](https://projectlombok.org/) - Menos é mais
* [Mockito](https://site.mockito.org/) - Testes
* [JUnit](https://junit.org/junit5/) - Testes



