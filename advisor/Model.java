package advisor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Model {
    public HttpResponse<String> getData(AccessToken accessToken, String resource){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken.getAccess_token())
                .uri(URI.create(resource))
                .GET().build();

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
