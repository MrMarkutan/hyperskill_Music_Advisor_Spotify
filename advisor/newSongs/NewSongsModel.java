package advisor.newSongs;

import advisor.AccessToken;
import advisor.Model;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpResponse;

public class NewSongsModel extends Model {
    private String resource;
    private static NewSongsModel instance;

    public static NewSongsModel getInstance(String resource){
        if(instance == null){
            instance = new NewSongsModel(resource);
        }
        return instance;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    private NewSongsModel(String resource) {
        this.resource = resource + "/v1/browse/new-releases";
    }

    public JsonArray getData(AccessToken accessToken){
        HttpResponse<String> response = super.getData(accessToken, getResource());
        JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject albums = jo.getAsJsonObject("albums");
        return albums.getAsJsonArray("items");

    }
}
