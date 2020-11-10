package advisor.playlists;

import advisor.AccessToken;
import advisor.Model;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpResponse;

public class PlaylistsModel extends Model {
    private String resource;
    private static PlaylistsModel instance;

    private PlaylistsModel(String resource, String category_id){
        this.resource = resource + "/v1/browse/categories/" + category_id + "/playlists";
    }

    public static PlaylistsModel getInstance(String resource, String category_id){
//        if(instance == null){
            instance = new PlaylistsModel(resource, category_id);
//        }
        return instance;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public JsonArray getData(AccessToken accessToken){
        JsonArray ja = new JsonArray();
        HttpResponse<String> response = super.getData(accessToken, getResource());
        JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
        if(response.statusCode() != 200) {
            ja.add(jo.get("error"));
        } else {
            if(jo.has("error")){
                JsonObject error = jo.get("error").getAsJsonObject();
                ja.add(jo.get("error"));
            } else {
                JsonObject playlists = jo.getAsJsonObject("playlists");
                ja.add(playlists.getAsJsonArray("items"));
            }
        }
        return ja;
    }
}
