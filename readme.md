[![Build](https://github.com/theborakompanioni/docker-java-healthcheck/actions/workflows/build.yml/badge.svg)](https://github.com/theborakompanioni/docker-java-healthcheck/actions/workflows/build.yml)
[![GitHub Release](https://img.shields.io/github/release/theborakompanioni/docker-java-healthcheck.svg?maxAge=3600)](https://github.com/theborakompanioni/docker-java-healthcheck/releases/latest)
[![License](https://img.shields.io/github/license/theborakompanioni/docker-java-healthcheck.svg?maxAge=2592000)](https://github.com/theborakompanioni/docker-java-healthcheck/blob/master/LICENSE)

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

The project is licensed under the Apache License. See [LICENSE](https://github.com/theborakompanioni/docker-java-healthcheck/blob/master/LICENSE) for details.
