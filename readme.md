# docker-java-healthcheck

## Getting started

Apply the `HEALTHCHECK` in your project:
```dockerfile
FROM ghcr.io/theborakompanioni/java-healthcheck:master AS healthcheck

FROM openjdk:21-jdk-slim
# [...]

COPY --from=healthcheck HealthCheck.java /HealthCheck.java

HEALTHCHECK --interval=10s --timeout=5s --retries=20 CMD ["java", "HealthCheck.java", "http://localhost:9001/actuator/health"]
```

## License

The project is licensed under the Apache License. See [LICENSE](LICENSE) for details.
