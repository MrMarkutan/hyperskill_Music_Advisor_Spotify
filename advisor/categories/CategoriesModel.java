package advisor.categories;

import advisor.AccessToken;
import advisor.Model;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpResponse;

public class CategoriesModel extends Model {
    private String resource;
    private static CategoriesModel instance;

    private CategoriesModel(String resource) {
        this.resource = resource  + "/v1/browse/categories";
    }

    public static CategoriesModel getInstance(String resource) {
        if(instance == null){
            instance = new CategoriesModel(resource);
        }
        return instance;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public JsonArray getData(AccessToken accessToken){
        HttpResponse<String> response = super.getData(accessToken, getResource());
        JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject categories = jo.getAsJsonObject("categories");
        return categories.getAsJsonArray("items");
    }

    public String getCategory(AccessToken accessToken, String genre){
        String category_id = "not found";
        HttpResponse<String> response = super.getData(accessToken, getResource());
        JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
        if (response.statusCode() != 200){
            JsonObject error = jo.get("error").getAsJsonObject();
            category_id = error.get("message").getAsString();
        } else {
            JsonObject categories = jo.getAsJsonObject("categories");
                for (JsonElement category: categories.getAsJsonArray("items")) {
                    JsonObject category_object = category.getAsJsonObject();
                    if(category_object.get("name").getAsString().equals(genre)){
                        category_id = category_object.get("id").getAsString();
                        break;
                    }
                }
        }
      return category_id;
    }
}
