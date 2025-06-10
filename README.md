# Orders Service

## Docker

Deploy service with database (will pull the latest image from the GitHub registry)

```shell
docker compose up
```

To build your own docker image you can do:

```shell
./gradlew bootBuildImage --imageName=ghcr.io/mspr4-2025/orders-service
```

## OpenAPI

The OpenAPI v3 documentation is available at [/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)