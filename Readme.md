Arquitetura RESTful em Java composta por três serviços independentes (Cliente, Produto e 
Comércio), integrados a PostgreSQL, MySQL e MongoDB, com o gerenciamento de dependências 
feito no Maven através do Spring Boot; 
● CRUD completo com validações personanizadas (maioridade, DDD válido, UF válida e vínculo 
DDD–Estado); 
● Documentação com Swagger, testes unitários e de integração com JUnit 5 + Mockito; 
● Estrutura modular com ConfigServer, Services independentes, Entities, Repositories, UseCases, 
DTOs, Mappers, Exceptions e Controllers documentados; 
● Deploy com Docker Compose, integração com repositórios no Container regiestries do Azure; 
● CI/CD com GitHub Actions fazendo o build e o push para os containers do Azure; 
● Tecnologias utilizadas: Java 23, Azure, GitHub Actions, Spring Boot, Hibernate, Spring Data, 
Docker, Docker Compose, Swagger, JUnit 5 + Mockito, PostgreSQL, MySQL e MongoDB. 

Sobre os Bancos de dados:
são 3 bancos de dados: PostgreSQL para ClienteService,
MySQL para ComercioService & MongoDB para ProdutoService (Por ser NoSQL isso muda a fromatação de ID do ProdutoService, onde nos outros são IDs são uma sequencia numérica);

Então se quizer testar no Código na sua própria máquina, mude as URLs das databases em:  ConfigServer > src > main > resources > config:

cliente-service.yml

comercio-servicee.yml

produto-service.yml

Para acessar o swagger:

8081 (Comércio)

8082 (Cliente)

8083 (Produto)


[![Build and Push to Azure Container Registry](https://github.com/WinterBR/Microservico3DBs/actions/workflows/continuos-integration.yml/badge.svg)](https://github.com/WinterBR/Microservico3DBs/actions/workflows/continuos-integration.yml)
