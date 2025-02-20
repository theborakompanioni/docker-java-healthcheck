import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class HealthCheck {
    public static void main(String[] args) {
        try {
            String url = args.length >= 1 ? args[0] : "http://localhost:8080/actuator/health";
            String line = args.length >= 2 ? args[1] : "\"status\":\"UP\"";
            int statusCode = Integer.parseInt(args.length >= 3 ? args[2] : "200");

            URI uri = URI.create(url);
            if (!"localhost".equals(uri.getHost())) {
                throw new IllegalArgumentException("Healthcheck only works with `localhost`.");
            }

            try (var client = HttpClient.newHttpClient()) {
                var request = HttpRequest.newBuilder()
                        .uri(uri)
                        .header("accept", "application/json")
                        .build();

                var response = client.send(request, BodyHandlers.ofString());

                if (response.statusCode() != statusCode) {
                    System.err.printf("Healthcheck error: Mismatching status code. Expected `%d`, was `%d`.%n",
                            statusCode, response.statusCode());
                    System.exit(2);
                }
                if (response.body().lines().noneMatch(it -> it.contains(line))) {
                    System.err.printf("Healthcheck error: Body does not contains line `%s`.%n", line);
                    System.exit(3);
                }
            }

            System.out.printf("Healthcheck success%n");
        } catch (Exception e) {
            System.err.printf("Error: %s%n", e.getClass().getSimpleName());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
