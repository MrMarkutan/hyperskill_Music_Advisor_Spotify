package advisor;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    Client(String code, String access/*, HttpServer server*/){
        String path = "grant_type=authorization_code" +
                "&code=" + code +
                "&redirect_uri=http://localhost:8080" +
                "&client_id=47b5a38115e341179407bf5b0fb8899c" +
                "&client_secret=eaf2100235574b3aaf82c4e0cf14ca86";

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(access + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(path))
                .build();


        HttpClient client = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            Controller.setAccessToken(new Gson().fromJson(response.body(),AccessToken.class));
            System.out.println("---SUCCESS---");
            Controller.server.stop(1);

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
//        finally {
//            Controller.server.stop(1);
//        }
    }
}
