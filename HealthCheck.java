import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class HealthCheck {
    public static void main(String[] args) {
        int port = Integer.parseInt(args.length >= 1 ? args[0] : "8080");
        String path = args.length >= 2 ? args[1] : "/actuator/health";
        String line = args.length >= 3 ? args[2] : "\"status\":\"UP\"";
        String protocol = args.length >= 3 ? ("https".equals(args[3]) ? "https" : "http") : "http";

        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create("%s://localhost:%d%s".formatted(protocol, port, path)))
                    .header("accept", "application/json")
                    .build();

            var response = client.send(request, BodyHandlers.ofLines());

            if (response.statusCode() != 200 || response.body().noneMatch(it -> it.contains(line))) {
                throw new RuntimeException("Healthcheck failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
