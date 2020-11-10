package advisor.playlists;

import advisor.View;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PlaylistsView extends View {
    public static void show(JsonArray ja, int maxPages, int pageIndex, int page){
        if(!checkPageIndexes(maxPages, pageIndex)) {
            JsonArray innerArray = new JsonArray();
            for (JsonElement element: ja) {
                JsonArray elemArray = element.getAsJsonArray();
                for (JsonElement el: elemArray){
                    innerArray.add(el);
                }
            }
                JsonObject itemObject;
            for(int i = pageIndex * page; i < (pageIndex*page) + page && i < innerArray.size(); i++) {
                itemObject = innerArray.get(i).getAsJsonObject();
                System.out.println(itemObject.get("name").getAsString());
                JsonObject external_urls_object = itemObject.get("external_urls").getAsJsonObject();
                System.out.println(external_urls_object.get("spotify").getAsString() + "\n");
            }
            System.out.println("---PAGE " + (pageIndex+1)  + " OF " + maxPages + "---");
        }
    }
}
