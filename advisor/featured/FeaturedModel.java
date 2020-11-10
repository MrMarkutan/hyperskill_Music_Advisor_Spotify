package advisor.featured;

import advisor.AccessToken;
import advisor.Model;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpResponse;

public class FeaturedModel extends Model {
    private String resource;
    private static FeaturedModel instance;

    public static FeaturedModel getInstance(String resource) {
        if(instance == null){
            instance = new FeaturedModel(resource);
        }
        return instance;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    private FeaturedModel(String resource) {
        this.resource = resource + "/v1/browse/featured-playlists";
    }

    public JsonArray getData(AccessToken accessToken){
        HttpResponse<String> response = super.getData(accessToken, getResource());
        JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject playlists = jo.getAsJsonObject("playlists");
        return playlists.getAsJsonArray("items");
    }
}
