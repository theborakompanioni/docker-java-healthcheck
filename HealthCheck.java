import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class HealthCheck {
    public static void main(String[] args) {
        int port = Integer.parseInt(args.length >= 1 ? args[0] : "8080");
        String path = args.length >= 2 ? args[1] : "/actuator/health";
        String line = args.length >= 3 ? args[2] : "\"status\":\"UP\"";
        String protocol = args.length >= 4 ? ("https".equals(args[3]) ? "https" : "http") : "http";
        int statusCode = Integer.parseInt(args.length >= 5 ? args[4] : "200");

        URI uri = URI.create("%s://localhost:%d%s".formatted(protocol, port, path));
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

            System.out.printf("Healthcheck success%n");
        } catch (Exception e) {
            System.err.printf("Error: %s%n", e.getClass().getSimpleName());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
