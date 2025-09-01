●	Projeto de arquitetura de microserviços em Java RESTful, composto por três serviços independentes (Cliente, Produto e Comércio), integrados a PostgreSQL, MongoDB e MySQL. Implementa CRUD completo, validações customizadas (maioridade de cliente, DDD válido, UF válida e vínculo DDD–Estado) e documentação com Swagger. 
●	Tecnologias utilizadas: Spring Boot, JPA/Hibernate, Spring Data MongoDB, Swagger, JUnit 5 + Mockito, PostgreSQL, MySQL e MongoDB;
IDE utilizadas: IntelliJ IDEA e DataGrip.
●	A estrutura do projeto inicia com a configuração do Spring Boot, onde são definidas as dependências e bibliotecas necessárias. Em seguida, são criados quatro pacotes principais: ConfigServer (configurações gerais e integração com os bancos de dados), ClienteService, ComercioService e ProdutoService.
No DataGrip são criadas as bases de dados (PostgreSQL, MySQL e MongoDB). Já no IntelliJ IDEA são criadas as Entities, sendo realizada a modelagem das entidades com JPA e Spring Data MongoDB, permitindo assim a criação dos Repositories e UseCases para cada funcionalidade CRUD. Para complementar, são desenvolvidas Exceptions e Validações customizadas.
Por fim, os serviços são expostos e documentados com Swagger, o DTO e o Mapper são utilizados para o encapsulamento e transferência de dados entre as camadas, e são gerados testes unitários utilizando JUnit 5 + Mockito.

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
