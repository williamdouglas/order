Projeto simples para treinar conceitos de arquitetura hexagonal, integração assíncrona orientada por eventos utilizando kafka e banco de dados não relacional (NoSQL) utilizando mongodb.

Stack principal utilizada:
- Spring Boot
- Apache Kafka
- MongoDB
- Java 17
- Maven

Para a construção do Microsserviço deve ser utilizada a arquitetura Hexagonal.

Utilizando o arquivo docker-compose presente foi criado um pequeno sistema de pedidos que funciona de forma assíncrona. Foi utilizado Spring Boot, Java 17 e Maven.

O sistema possui duas partes: uma responsável pela criação e consulta de pedidos e outra que responsável pelo processamento do pedido e envio dos dados para a transportadora que enviará o pedido ao cliente.

O sistema contem endpoints REST para criação e também para consulta do STATUS de um pedido. Os possíveis status são AGUARDANDO_ENVIO e ENVIADO_TRANSPORTADORA.

Assim que um pedido é efetuado, os dados do pedido são gravados em uma collection no Mongo com o status AGUARDANDO_ENVIO e uma mensagem é postada em um tópico Kafka informando que um novo pedido foi efetuado.

Existe um consumidor para esse tópico Kafka que lê os dados, realiza a busca desse pedido no Mongo e altera o status do mesmo para ENVIADO_TRANSPORTADORA.

Ao consultar o pedido através do endpoint de consulta, o pedido é retornado com o status devidamente atualizado.

Para fins de simplificação tanto o producer quanto o consumer foram implementados em um único Microsserviço.

Exemplos de consultas:

curl --location 'http://localhost:8080/orders' \
--header 'Content-Type: application/json' \
--data '{
"products": [
        {
            "id": 1,
            "name": "Product1",
            "price": "9.99"
        }
    ]
}'

curl --location 'http://localhost:8080/orders/1'