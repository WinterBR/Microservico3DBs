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